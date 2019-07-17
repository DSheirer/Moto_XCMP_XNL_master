package priv.chunyu.moto.xcmp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.DeviceDiscoveryCommands.ESN;
import priv.chunyu.moto.DeviceDiscoveryCommands.SN;
import priv.chunyu.moto.xnl.XNL;

public class XCMPMsg extends XCMP implements Runnable {
	static Socket master;
	protected static DataOutputStream output;
	protected static DataInputStream input;
	protected static byte[] MasterAddress;
	protected static byte[] RadioAddress;

	public XCMPMsg() throws IOException, InterruptedException {
		master = XNL.master;
		output = XNL.output;
		input = XNL.input;
		MasterAddress = DataProcess.get_MasterAddress(); // Pc address
		RadioAddress = DataProcess.get_RadioAddress(); // radio address
	}

	public void run() {
		try {
			receive();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * check every message and define the message type
	 */
	void receive() throws IOException, InterruptedException {
		boolean x = true;
		while (x) {
			int msgLen=input.readUnsignedShort()-2;//XCMP msg length  LEN(2)+OPCODE(2)
			int 	OPCODE = input.readUnsignedShort(); //determine msg or ack
			/* if it's ACK*/
			if(OPCODE==(byte) 0x0C) {
				System.out.print("此msg為 ACK");
				byte data[] = new byte[msgLen];
				input.read(data, 0, data.length);
				StringBuilder HexicmalData = DataProcess.ReadingData(data);
				System.out.println("	"+HexicmalData);
			}
			/* if it's reply*/
			if(OPCODE==(byte) 0x0B) {
				System.out.println("此msg為 Reply");
				byte data[] = new byte[msgLen];
				input.read(data, 0, data.length);
				checkMsg(data);
				Ack((byte) data[1], data[6], data[7]);
			}
		}
	}

	private void checkMsg(byte data[]) throws IOException, InterruptedException {
		switch (data[10]) {
		case (byte) 0xF4:
			System.out.println("Battery Level");
		case (byte) 0x04:
			System.out.println("Battery Level");
		case (byte) 0x54:
			System.out.println("Battery Level");
		case (byte) 0x74:
			System.out.println("Battery Level");
		case (byte) 0x24:
			System.out.println("Battery Level");
		case (byte) 0x22:
			System.out.println("Battery Level");
		case (byte) 0xD3:
			System.out.println("Battery Level");
		case (byte) 0xE4:
			System.out.println("Battery Level");
		case (byte) 0xC3:
			System.out.println("Battery Level");
		case (byte) 0xA4:
			System.out.println("Battery Level");
		case (byte) 0xB5:
			System.out.println("Battery Level");

		case (byte) 0xB4:
			if (data[11] == (byte) 0x10) {
				System.out.println("	Battery Level");
			}
			if (data[11] == (byte) 0x02) {
				System.out.println("	GPS");
			}
			if (data[11] == (byte) 0x1E) {
				System.out.println("	還不知道");
			}
		/* Radio Status Reply */
		case (byte) 0x80:
			if (data[11] == (byte) 0x0E && data[13] == (byte) 0x08) {
				SN.reply(data);
			}
			if (data[11] == (byte) 0x0E && data[13] == (byte) 0x09) {
				ESN.reply(data);
			}
		}
	}

	static void Ack(byte flag, byte id_1, byte id_2) throws IOException {
		byte ack[] = { (byte) 0x00, (byte) 0x0C, (byte) 0X00, (byte) 0X00C, (byte) 0X01, // Protocol ID
				(byte) flag, // FLag
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1], // id?
				(byte) id_1, (byte) id_2, // Transaction ID
				(byte) 0X00, (byte) 0X00// payloadLeng
		};
		output.write(ack);
	}
}
