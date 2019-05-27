package priv.chunyu.moto.xnl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.xml.bind.DatatypeConverter;

import priv.chunyu.moto.DataProcesss.DataProcesss;
import priv.chunyu.moto.TEA.TEA;
import priv.chunyu.moto.xcmp.XCMP;

public class XNLsocket {
	public Socket master;
	public DataOutputStream output;
	public DataInputStream input;// data input
	byte[] XNL_DEVICE_AUTH_KEY_REQUEST = { (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	byte[] XNL_DEVICE_CONN_REQUEST = { (byte) 0x00, (byte) 0x18, (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x06, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0C,
			(byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x01, (byte) 0x44, (byte) 0xF9, (byte) 0x27, (byte) 0x5D,
			(byte) 0xE2, (byte) 0x44, (byte) 0x9A, (byte) 0x9A };
	public void run() throws IOException, InterruptedException {
		XNL.getConnection();
		master=XNL.master;
		output=XNL.output;
		input=XNL.input;
		System.out.println("XNL Connection");
		receive_XNL_MASTER_STATUS_BROADCAST();
		Thread.sleep(700);// waiting for XNL_MASTER_STATUS_BROADCAST
		send_XNL_REQUEST();
		receive_XNL_DEVICE_AUTH_KEY();
		send_DEVICE_CONN_REQUEST();
		receive_DEVICE_CONN_REPLY();
		XCMP Xcmp_Connection = new XCMP();
		Xcmp_Connection.start();
	}

	private void send_DEVICE_CONN_REQUEST() throws IOException {
		output.write(XNL_DEVICE_CONN_REQUEST);
	}

	private void receive_DEVICE_CONN_REPLY() throws IOException {
		byte data[] = new byte[28];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcesss.ReadingData(data);
		System.out.println("Receive DEVICE_CONN_REPLY");
		System.out.println(HexicmalData);
		System.out.println("XNL connection is established");
	}

	private void receive_XNL_MASTER_STATUS_BROADCAST() throws IOException {
		System.out.println("Receving XNL Master Status BROADCAST");
		byte[] data = new byte[21];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcesss.ReadingData(data);
		System.out.println(HexicmalData);
	}

	private void receive_XNL_DEVICE_AUTH_KEY() throws IOException {
		System.out.println("Receving Device Auth Key");
		byte[] data = new byte[24];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcesss.ReadingData(data);
		System.out.println(HexicmalData);// here sb is hexadecimal string
		byte temp_key[] = new byte[2];// storing temp key
		temp_key = KeyData(HexicmalData, temp_key.length);
		// System.out.println("test"+DataProcesss.hexValue(temp_key[0]) + " " +
		// DataProcesss.hexValue(temp_key[1]) + " "+ DataProcesss.hexValue(temp_key[2])
		// + " " + DataProcesss.hexValue(temp_key[3]));
		setXNL_DEVICE_CONN_REQUEST(temp_key);
	}

	private void setXNL_DEVICE_CONN_REQUEST(byte[] temp_key) {
		for (int i = 18; i < XNL_DEVICE_CONN_REQUEST.length; i++) {
			XNL_DEVICE_CONN_REQUEST[i] = temp_key[i - 18];
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
		String Encrypt_key;//// 要更改
		Encrypt_key = key.getValue();
		// System.out.println("Encrypted Key(from main):"+Encrypt_key);
		return toByteArray(Encrypt_key);
	}

	private void send_XNL_REQUEST() throws IOException {
		System.out.println("sedning XNL Request");
		output.write(XNL_DEVICE_AUTH_KEY_REQUEST);// sending XNL_DEVICE_AUTH_KEY_REQUEST to radio
	}

	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
}
