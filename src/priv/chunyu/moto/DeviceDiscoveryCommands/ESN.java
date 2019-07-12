package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;
import java.util.logging.Logger;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

public class ESN extends XNL implements RadioStatusCommand {
	static byte[] RadioAddress = DataProcess.get_RadioAddress();
	static byte[] MasterAddress = DataProcess.get_MasterAddress();
	static byte id[] = {(byte)0x00,(byte)0x03};
	static byte flag = 0x01;
	static String strClassName = RadioStatus.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	public static void request() throws IOException {
		checkflag();
		byte RadioStatus[] = { (byte) 0x00, (byte) 0x0F, (byte) 0X00, (byte) 0x0B, (byte) 0X01, // Protocol ID
				(byte) flag, // FLag
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1], 
				(byte) id[0], (byte)id[1], // Transaction ID
				(byte) 0X00, (byte) 0x03, // payloadLength
				(byte) 0X00, (byte) 0X0E, (byte) 0X09// payloadLeng
		};
		output.write(RadioStatus);
		logger.info("Sending Raido Status Request");
		request_ack(flag);
	}
	public static void request_ack(byte flag) throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		logger.info("Radio Status ack "+HexicmalData);
		reply();
	}
	public static void reply() throws IOException {
		byte data[] = new byte[26];
		StringBuilder HexicmalData = null;
		input.read(data, 0, data.length);
		HexicmalData = DataProcess.ReadingData(data);
		logger.info("ModelNUmber reqquest "+HexicmalData);
		reply_ack(data[5]);
	}
	public static void reply_ack(byte flag) throws IOException {
		byte ack[] = { (byte) 0x00, (byte) 0x0C, (byte) 0X00, (byte) 0x0C, (byte) 0X01, // Protocol ID
				(byte) flag, // FLag
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1], // id?
				(byte) id[0], (byte)id[1], // Transaction ID
				(byte) 0X00, (byte) 0X00// payloadLeng
		};
		output.write(ack);
		logger.info("Sending back ACK");
	}
	public static void checkflag() {
		if (flag == (byte) 0x07) {
			flag = (byte) 0x00;
		} else {
			flag = (byte) (flag + 0x01);
		}
	}
}
