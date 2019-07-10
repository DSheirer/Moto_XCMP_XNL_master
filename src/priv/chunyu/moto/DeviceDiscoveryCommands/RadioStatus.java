package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;
import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

public class RadioStatus extends XNL {
	static byte[] RadioAddress = DataProcess.get_RadioAddress();
	static byte[] MasterAddress = DataProcess.get_MasterAddress();
	static byte id[] = new byte[2];
	static byte flag = 0x01;

	public RadioStatus() throws IOException, InterruptedException {
		id[0] = 0x00; // id unknown
		id[1] = 0x01;
	}

	public static void Radio_Status_Request() throws IOException, InterruptedException {
		if (flag == (byte) 0x07) {
			flag = (byte) 0x00;
		} else {
			flag = (byte) (flag + 0x01);
		}
		byte RadioStatus[] = { (byte) 0x00, (byte) 0x0F, (byte) 0X00, (byte) 0x0B, (byte) 0X01, // Protocol ID
				(byte) flag, // FLag
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1], // id?
				(byte) id[0], (byte) id[1], // Transaction ID
				(byte) 0X00, (byte) 0x03, // payloadLength
				(byte) 0X00, (byte) 0X0E, (byte) 0X09// payloadLeng
		};
		output.write(RadioStatus);
		System.out.println("Sending Raido Status Request");
		System.out.println(flag);
		receive_Radio_Status_ACK(flag, id[0], id[1]);
	}

	public static void receive_Radio_Status_ACK(byte flag, byte id1, byte id2) throws IOException {
		byte data[] = new byte[14];
		int f = 0;
		while (f == 0) {
			input.read(data, 0, data.length);
			if (data[5] == flag && data[10] == id1 && data[11] == id2) {
				f = 1;
				StringBuilder HexicmalData = DataProcess.ReadingData(data);
				System.out.println("Radio Status ack");
				System.out.println(HexicmalData);
				receive_ModelNumber();
			}

		}
	}

	public static void send_ModelNUmber_Ack(byte flag, byte id1, byte id2) throws IOException {
		byte ack[] = { (byte) 0x00, (byte) 0x0C, (byte) 0X00, (byte) 0x0C, (byte) 0X01, // Protocol ID
				(byte) flag, // FLag
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1], // id?
				(byte) id1, (byte) id2, // Transaction ID
				(byte) 0X00, (byte) 0X00// payloadLeng
		};
		output.write(ack);
		System.out.println("Sending back ACK");
		flag = (byte) 0x02;
	}

	private static void receive_ModelNumber() throws IOException {
		byte data[] = new byte[31];
		int f = 0;
		while (f == 0) {
			input.read(data, 0, data.length);
			if (data[14] == (byte) 0x80 && data[15] == (byte) 0x0E) {
				f = 1;
				StringBuilder HexicmalData = DataProcess.ReadingData(data);
				System.out.println("ModelNUmber reqquest");
				System.out.println(HexicmalData);
				send_ModelNUmber_Ack(data[5], data[10], data[11]);
			}
		}
	}
}
