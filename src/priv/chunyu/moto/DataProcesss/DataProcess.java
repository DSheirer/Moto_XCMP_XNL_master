package priv.chunyu.moto.DataProcesss;

import java.io.IOException;

public class DataProcess {
	static StringBuilder sb = new StringBuilder();
	static byte[] srcAddress = new byte[2]; // Master address
	static byte[] dstAddress = new byte[2]; // raido address

	public static StringBuilder ReadingData(byte[] data) throws IOException {
		sb.delete(0, sb.length());
		for (byte b : data) {
			sb.append(String.format("%02X", b));
		}
		return sb;
	}

	/* testing for hex Value */
	public static String hexValue(byte x) {
		String hexValue = String.format("%02X", x);
		return hexValue;
	}

	public static void set_SrcAddress(byte[] data) {
		dstAddress[0] = data[8];
		dstAddress[1] = data[9];
		// System.out.println("地址"+hexValue(dstAddress[0])+" "+hexValue(dstAddress[1]));
	}

	public static void set_DstAddress(byte[] data) {
		srcAddress[0] = data[10];
		srcAddress[1] = data[11];
		// System.out.println("地址"+hexValue(srcAddress[0])+" "+hexValue(srcAddress[1]));
	}

	public static byte[] get_DstAddress() {
		return dstAddress;
	}

	public static byte[] get_SrcAddress() {
		return srcAddress;
	}

	public static void MessageStructure(byte[] data) {
		System.out.print("Length " + hexValue(data[0]) + hexValue(data[1]));// Size of Message
		System.out.print(" OPCODE " + hexValue(data[2]) + hexValue(data[3]));// OPCODE
		System.out.print(" Protocol ID " + hexValue(data[4]));// 01 will be XCMP 00 will be xnl
		System.out.print(" FLAG " + hexValue(data[5]));
		System.out.print(" Destination Address " + hexValue(data[6]) + hexValue(data[7])); // Destination Address
		System.out.print(" Source Address " + hexValue(data[8]) + hexValue(data[9])); // Source Address
		System.out.print(" Transaction ID " + hexValue(data[10]) + hexValue(data[11]));
		System.out.print(" Payload Length " + hexValue(data[12]) + hexValue(data[13]));
		System.out.print(" Payload "); // 14 to rest of the byte will be its Data
		for (int i = 14; i < data.length; i++) {
			System.out.print(hexValue(data[i])+" ");
		}
		System.out.println(" ");

	}
}
