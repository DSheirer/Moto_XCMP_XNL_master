package priv.chunyu.moto.xnl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
			(byte) 0xE2, (byte) 0x44, (byte) 0x9A, (byte) 0x9A, };

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
		TEA key = new TEA(HexicmalData.toString());
		sb.delete(0, sb.length());
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
	/*
	 * public static byte [] hexToByteArray(String inHex) { int hexlen =
	 * inHex.length(); byte [] result; if (hexlen % 2 == 1) { // 奇数 hexlen++; result
	 * = new byte[(hexlen / 2)]; inHex = "0" + inHex; } else { // 偶数 result = new
	 * byte[(hexlen / 2)]; } int j = 0; for (int i = 0; i < hexlen; i += 2) {
	 * result[j] = hexToByte(inHex.substring(i, i + 2)); j++; } return result; }
	 * public static byte hexToByte(String inHex) { return (byte)
	 * Integer.parseInt(inHex, 16); }
	 */

}

// 001600050000000000060000000afffd3a8f45f504dc006f
// 001600050000000000060000000afffc3a8f45f504dc006f
// 001600050000000000060000000afffd09df7e9a60513cbb
