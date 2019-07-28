package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;
import java.util.logging.Logger;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.XCMP.XCMPsocket;

public class RSSI extends XCMPsocket {
	public RSSI() throws IOException, InterruptedException {
		super();
	}

	public static byte id[] = { (byte) 0x00, (byte) 0x00 };
	static String strClassName = RSSI.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	static double RSSI;

	public static String request() throws IOException, InterruptedException {
		synchronized (lock) {
			checkflag();
			byte data[] = { (byte) 0x00, (byte) 0x0F, (byte) 0X00, (byte) 0x0B, (byte) 0X01, (byte) flag,
					(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
					(byte) id[0], (byte) id[1], (byte) 0X00, (byte) 0x03, (byte) 0X00, (byte) 0X0E, (byte) 0X02 };
			output.write(data);
			logger.info("Sending ESN Request");
			lock.wait(500);
		}
		return String.valueOf(RSSI);
	}
	public static void reply(byte[] data) throws IOException, InterruptedException {
		synchronized (lock) {
			System.out.println((DataProcess.ReadingIntData(15,data))*0.00390625);
			RSSI =DataProcess.ReadingIntData(14,data)+  ((DataProcess.ReadingIntData(15,data)*0.00390625));
			logger.info("RSSI: " + RSSI);
			lock.notify();
		}
	}
}
