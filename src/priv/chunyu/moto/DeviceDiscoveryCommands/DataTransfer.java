package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

public class DataTransfer extends XNL{
	static byte[] RadioAddress = DataProcess.get_RadioAddress();
	static byte[] MasterAddress = DataProcess.get_MasterAddress();
	public static void Data_Req() throws IOException {
		byte DATAREQ[] = { 
				(byte) 0x00, (byte) 0x18, (byte) 0X00, (byte) 0x0B, (byte) 0X01, // Protocol ID
				(byte) 0x03, // FLag
				(byte) RadioAddress[0],(byte) RadioAddress[1],(byte)MasterAddress[0], (byte)MasterAddress[1], // id?
				(byte) 0x00, (byte) 0x09,// transcation ID
				(byte) 0x00,(byte) 0x0C,
				(byte) 0x04, (byte) 0x1D,
				(byte) 0x10,//Data sesssion start
				(byte) 0x20,//DMR message data
				(byte) 0x02,//Motorola IP
				(byte) 0x04, //size
				(byte) 0x0c,//12
				(byte) 0x00,//0
				(byte) 0x00,//0
				(byte) 0x26,//38
				(byte) 0x0f,(byte) 0xA7//4006
		};
		output.write(DATAREQ);
		System.out.println("Sending Data_Req");
		receive_Data_Req_ACK();
	}
	private static void receive_Data_Req_ACK() throws IOException {
		byte data[] = new byte[5];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Data_Req ack");
		System.out.println(HexicmalData);
		
	}

}
