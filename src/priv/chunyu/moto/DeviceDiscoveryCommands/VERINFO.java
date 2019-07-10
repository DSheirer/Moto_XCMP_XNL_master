package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

/* Version Information Request
 */
public class VERINFO extends XNL {
	static byte[] RadioAddress = DataProcess.get_RadioAddress();
	static byte[] MasterAddress = DataProcess.get_MasterAddress();
	public static void VERINFO_Request() throws IOException, InterruptedException {
		byte VERINFO[] = { (byte) 0x00, (byte) 0x0F, (byte) 0X00, (byte) 0x0B, (byte) 0X01, // Protocol ID
				(byte) 0x03, // FLag
				(byte)0x00, (byte)0x11,(byte)MasterAddress[0], (byte)MasterAddress[1], // id?
				(byte) 0X01, (byte) 0X03, // Transaction ID
				(byte) 0X00, (byte) 0x03, // payloadLength
				(byte) 0X00, (byte) 0X0F, (byte) 0X63
		};
		output.write(VERINFO);
		System.out.println("Sending Raido Status Request");
		receive_VERINFO_ACK();

	}
	private static void receive_VERINFO_ACK() throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Version Information ack");
		System.out.println(HexicmalData);
		receive_VERINFO();
		
	}
	private static void receive_VERINFO() throws IOException {
		byte data[] = new byte[31];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Version Information reqquest");
		System.out.println(HexicmalData);
		
	}

}
