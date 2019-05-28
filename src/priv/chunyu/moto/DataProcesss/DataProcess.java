package priv.chunyu.moto.DataProcesss;

import java.io.IOException;

public class DataProcess {
	static StringBuilder sb = new StringBuilder();
	static byte[] srcAddress= new byte[2]; //Master address
	static byte[] dstAddress= new byte[2]; //raido address

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
	public static void set_SrcAddress(byte []data) {
		dstAddress[0]=data[8];
		dstAddress[1]=data[9];
		//System.out.println("地址"+hexValue(dstAddress[0])+" "+hexValue(dstAddress[1]));
	}
	public static void set_DstAddress(byte []data){
		srcAddress[0]=data[10];
		srcAddress[1]=data[11];
	//	System.out.println("地址"+hexValue(srcAddress[0])+" "+hexValue(srcAddress[1]));

	}
	public static byte []get_DstAddress() {
		return dstAddress;
	}
	public static byte []get_SrcAddress() {
		return srcAddress;
	}
}
