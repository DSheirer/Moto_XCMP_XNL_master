package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;
import java.util.logging.Logger;
import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.XCMP.XCMPsocket;

/* Version Information Request
 */
public class SN extends XCMPsocket {
	public SN() throws IOException, InterruptedException {
		super();
	}

	static byte id[] = { (byte) 0x00, (byte) 0x02};
	static String strClassName = SN.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	static String sn;

	public static String request() throws IOException, InterruptedException {
		synchronized(lock) {
			checkflag();
			byte data[] = { (byte) 0x00, (byte) 0x0F, (byte) 0X00, (byte) 0x0B, (byte) 0X01, (byte) flag,
					(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
					(byte) id[0], (byte) id[1], (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x0E, (byte) 0x08 };
			output.write(data);
			logger.info("Sending SN Request");
			lock.wait(500);
		}
		return sn;
	}

	public static void reply(byte[] data) throws IOException {
		synchronized(lock) {
		HexicmalData = DataProcess.ReadingData(data);
		System.out.println("	" + HexicmalData);
		sn = DataProcess.hexToAscii(28,HexicmalData.length(),HexicmalData.toString());
		logger.info("SerialNUmber:" + sn);
		lock.notify();
		}
	}

	public static String get() {
		return sn;
	}
}
