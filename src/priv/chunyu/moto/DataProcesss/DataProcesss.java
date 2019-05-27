package priv.chunyu.moto.DataProcesss;

import java.io.IOException;

public class DataProcesss {
	static StringBuilder sb = new StringBuilder();

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
}
