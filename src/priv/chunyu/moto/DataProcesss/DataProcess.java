package priv.chunyu.moto.DataProcesss;

import java.io.IOException;

public class DataProcess {
	static StringBuilder sb = new StringBuilder();
	static byte[] RadioAddress = new byte[2]; // radio
	static byte[] MasterAddress = new byte[2]; // Master address

	public static StringBuilder ReadingData(byte[] data) throws IOException {
		sb.delete(0, sb.length());
		for (byte b : data) {
			sb.append(String.format("%02X", b));
		}
		return sb;
	}

	public static String hexToAscii(int start, int end, String hexStr) {
		StringBuilder output = new StringBuilder("");
		for (int i = start; i < end; i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	/* testing for hex Value */
	public static String hexValue(byte x) {
		String hexValue = String.format("%02X", x);
		return hexValue;
	}

	public static void set_RADIOAddress(byte[] data) {
		RadioAddress[0] = data[8];
		RadioAddress[1] = data[9];
		System.out.println("src地址" + hexValue(RadioAddress[0]) + " " + hexValue(RadioAddress[1]));
	}

	public static void set_XNL_MASTER_Address(byte[] data) {
		MasterAddress[0] = data[16];
		MasterAddress[1] = data[17];
		System.out.println("DST地址" + hexValue(MasterAddress[0]) + " " + hexValue(MasterAddress[1]));
	}

	public static byte[] get_MasterAddress() {
		return MasterAddress;
	}

	public static byte[] get_RadioAddress() {
		return RadioAddress;
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
			System.out.print(hexValue(data[i]) + " ");
		}
		System.out.println(" ");

	}
	// Hex to Decimal
	public static String ReadingData(int start, int end, byte[] data) {
		StringBuilder value = new StringBuilder();
		for (int i = start; i < end; i++) {
			int x = data[i] & 0xff;
			// System.out.println(temp);
			value.append(x);
		}
		return value.toString();
	}

	public static int ReadingIntData(int value, byte[] data) {
		int IntData= data[value] & 0xff;
		return IntData;
	}

}
