package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;
import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

public class RadioStaus extends XNL {
	static byte[] RadioAddress = DataProcess.get_RadioAddress();
	static byte[] MasterAddress = DataProcess.get_MasterAddress();
	public RadioStaus() throws IOException, InterruptedException {
	}

	private static void receive_ModelNumber() throws IOException {
		byte data[] = new byte[31];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("ModelNUmber reqquest");
		System.out.println(HexicmalData);
		send_ModelNUmber_Ack();
	}

	public static void send_ModelNUmber_Ack() throws IOException {
		byte ack[] = { (byte) 0x00, (byte) 0x0C, (byte) 0X00, (byte) 0x0C, (byte) 0X01, // Protocol ID
				(byte) 0x03, // FLag
				(byte) RadioAddress[0], RadioAddress[1],(byte) MasterAddress[0], (byte) MasterAddress[1], // id?
				(byte) 0X02, (byte) 0X03, // Transaction ID
				(byte) 0X00, (byte) 0X00// payloadLeng
		};
		byte test[]= {
				(byte) 0x00
		};
		output.write(ack);
		output.write(test);
		System.out.println("Sending back ACK");
	}

	public static void receive_Radio_Staus_ACK() throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Radio Status ack");
		System.out.println(HexicmalData);
		receive_ModelNumber();
	}

	public static void Radio_Staus_Request() throws IOException, InterruptedException {
		byte RadioStatus[] = { (byte) 0x00, (byte) 0x0F, (byte) 0X00, (byte) 0x0B, (byte) 0X01, // Protocol ID
				(byte) 0x03, // FLag
				(byte) RadioAddress[0], RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1], // id?
				(byte) 0X02, (byte) 0X03, // Transaction ID
				(byte) 0X00, (byte) 0x03, // payloadLength
				(byte) 0X00, (byte) 0X0E, (byte) 0X07// payloadLeng
		};
		output.write(RadioStatus);
		System.out.println("Sending Raido Status Request");
		receive_Radio_Staus_ACK();

	}
}
