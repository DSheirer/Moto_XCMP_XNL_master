package priv.chunyu.moto.TEA;

public class TEA {
		int[] data = new int[2];
		String x;
	public TEA(String[] keydata) {
		int[] key = new int[] {Enter by urself };
		data[0] = (int) Long.parseLong(keydata[0], 16);
		data[1] = (int) Long.parseLong(keydata[1], 16);
		int[] encrypt= encrypt(data, key);
		x=intArrToHex(encrypt);
	//	System.out.println("Encrypted      : " + intArrToHex(encrypt));
		int[] decrypt= decrypt(data, key);
	//	System.out.println("Decrypted      : " + intArrToHex(decrypt));
	}
	private static int[] encrypt(int[] block, int[] key) {
		int i = block[0];
		int j = block[1];
		int sum = 0;
		int delta = Enter by urself ;
		for (int k = 0; k < 32; ++k) {
			sum += delta;
			i += (j << 4 & 0xfffffff0) + key[0] ^ j + sum ^ (j >> 5 & 0x7ffffff) + key[1];
			j += (i << 4 & 0xfffffff0) + key[2] ^ i + sum ^ (i >> 5 & 0x7ffffff) + key[3];
		}
		block[0] = i;
		block[1] = j;
		return block;
	}

	private static String intArrToHex(int[] arr) {
		StringBuilder builder = new StringBuilder(arr.length * 8 + arr.length - 1);
		for (int b : arr) {
			builder.append(byteToUnsignedHex(b));
		//	builder.append(" ");
		}
		return builder.toString();
	}

	private static String byteToUnsignedHex(int i) {
		String hex = Integer.toHexString(i).toUpperCase();
		while (hex.length() < 8) {
			hex = "0" + hex;
		}
		return hex;
	}

	private static int[] decrypt(int[] block, int[] key) {
		int i = block[0];
		int j = block[1];
		int delta = Enter by urself ;
		int sum = delta * 32; // sum=468ACF00
		for (int k = 0; k < 32; ++k) {
			j -= (i << 4 & 0xfffffff0) + key[2] ^ i + sum ^ (i >> 5 & 0x7ffffff) + key[3];
			i -= (j << 4 & 0xfffffff0) + key[0] ^ j + sum ^ (j >> 5 & 0x7ffffff) + key[1];
			sum -= delta;
		}
		block[0] = i;
		block[1] = j;
		return block;
	}
	public String getValue() {
		
		return x;
	}

}
