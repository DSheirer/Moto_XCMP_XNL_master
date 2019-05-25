package priv.chunyu.moto.xnl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

import priv.chunyu.moto.TEA.TEA;

public class XNLsocket {
	DataOutputStream output;// data output
	DataInputStream input;// data input
	StringBuilder sb = new StringBuilder();
	Socket master;// Pc
	byte[] XNL_DEVICE_AUTH_KEY_REQUEST = { (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	byte[] XNL_DEVICE_CONN_REQUEST = { (byte) 0x00, (byte) 0x18, (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x06, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0C,
			(byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x01, (byte) 0x44, (byte) 0xF9, (byte) 0x27, (byte) 0x5D,
			(byte) 0xE2, (byte) 0x44, (byte) 0x9A, (byte) 0x9A };

	public XNLsocket() throws IOException, InterruptedException {
		master = new Socket("192.168.10.1", 8002);// Pc set as master ,and setup connection
		input = new DataInputStream(master.getInputStream());
		output = new DataOutputStream(master.getOutputStream());
		receive_XNL_MASTER_STATUS_BROADCAST();
		Thread.sleep(700);// waiting for XNL_MASTER_STATUS_BROADCAST
		send_XNL_REQUEST();
		receive_XNL_DEVICE_AUTH_KEY();
		send_DEVICE_CONN_REQUEST();

	}

	private void send_DEVICE_CONN_REQUEST() throws IOException {
		output.write(XNL_DEVICE_CONN_REQUEST);
	}

	private void receive_XNL_MASTER_STATUS_BROADCAST() throws IOException {
		System.out.println("Receving XNL Master Status BROADCAST");
		byte[] data = new byte[21];
		StringBuilder HexicmalData = ReadingData(data);
		System.out.println(HexicmalData);
		sb.delete(0, sb.length());
	}

	private void receive_XNL_DEVICE_AUTH_KEY() throws IOException {
		System.out.println("Receving Device Auth Key");
		byte[] data = new byte[24];
		StringBuilder HexicmalData = ReadingData(data);
		System.out.println(HexicmalData);// here sb is hexadecimal string
		byte temp_key[] = new byte[2];// storing temp key
		temp_key = KeyData(HexicmalData, temp_key.length);
		//System.out.print(temp_key[0] +" "+ hexValue(temp_key[1])+" "+ hexValue(temp_key[2])+" "+hexValue(temp_key[3]));
		setXNL_DEVICE_CONN_REQUEST(temp_key);
		sb.delete(0, sb.length());
	}

	private void setXNL_DEVICE_CONN_REQUEST(byte[] temp_key) {
		for(int i=18;i<XNL_DEVICE_CONN_REQUEST.length;i++) {
			XNL_DEVICE_CONN_REQUEST[i]=temp_key[i-18];
		}
		
	}

	private byte[] KeyData(StringBuilder HexicmalData, int length) {
		String temp_key[] = new String[2];
		// String x=HexicmalData.subSequence(0, 32).toString();
		String key0 = HexicmalData.subSequence(32, 40).toString();
		String key1 = HexicmalData.subSequence(40, 48).toString();
		temp_key[0] = key0;
		temp_key[1] = key1;
		TEA key = new TEA(temp_key);
		String Encrypt_key;////要更改
		Encrypt_key = key.getValue();
		System.out.println("Encrypted Key(from main):"+Encrypt_key);
		return toByteArray(Encrypt_key);
	}

	private void send_XNL_REQUEST() throws IOException {
		System.out.println("sedning XNL Request");
		output.write(XNL_DEVICE_AUTH_KEY_REQUEST);// sending XNL_DEVICE_AUTH_KEY_REQUEST to radio
	}

	private StringBuilder ReadingData(byte[] data) throws IOException {
		input.read(data, 0, data.length);
		for (byte b : data) {
			sb.append(String.format("%02X", b));
		}
		return sb;
	}
	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
	/* testing for hex Value*/
	public String hexValue(byte x) {
		String hexValue = String.format("%02X",x);
		return hexValue;
	}

}

// 001600050000000000060000000afffd 3a 8f 45 f5 04 dc 00 6f
// 001600050000000000060000000afffc3a8f45f504dc006f
// 001600050000000000060000000afffd09df7e9a60513cbb
