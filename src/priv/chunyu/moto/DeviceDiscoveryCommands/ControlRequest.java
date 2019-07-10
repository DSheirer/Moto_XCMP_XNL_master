package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;
import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

public class ControlRequest extends XNL {
	static byte[] RadioAddress = DataProcess.get_RadioAddress();
	static byte[] MasterAddress = DataProcess.get_MasterAddress();

	public ControlRequest() throws IOException, InterruptedException {

	}

	public static void Control_Request() throws IOException, InterruptedException {
		byte VERINFO[] = { (byte) 0x00, (byte) 0x17, (byte) 0X00, (byte) 0x0B, (byte) 0X01, // Protocol ID
				(byte) 0x03, // FLag
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1], // id?
				(byte) 0X01, (byte) 0X03, // Transaction ID
				(byte) 0X00, (byte) 0x0b, // payloadLength
				(byte) 0X04, (byte) 0X1C, (byte) 0X02, (byte) 0X01, (byte) 0X01, (byte) 0X03, (byte) 0X00, (byte) 0X00,
				(byte) 0X26, (byte) 0X00, (byte) 0X00, };
		output.write(VERINFO);
		System.out.println("Turn off Request");
		receive_Control_Request_ACK();
	}

	private static void receive_Control_Request_ACK() throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Turn off ack");
		System.out.println(HexicmalData);

	}
}
