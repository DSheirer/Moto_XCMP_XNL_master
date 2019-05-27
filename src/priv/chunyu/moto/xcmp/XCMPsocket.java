package priv.chunyu.moto.xcmp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import priv.chunyu.moto.DataProcesss.DataProcesss;
import priv.chunyu.moto.xnl.XNL;

public class XCMPsocket extends XCMP {
	static Socket master;
	static DataOutputStream output;
	static DataInputStream input;

	public XCMPsocket() throws IOException, InterruptedException {
		System.out.println("XCMP Connection  ");
		master = XNL.master;
		output = XNL.output;
		input = XNL.input;
		receive_DEVICE_SYSMAP_BROADCAST();
		receive_XCMP_DEVICE_INIT_STATUS();
		send_DATA_MSG_ACK();
		send_XCMP_DEVICE_INIT_STATUS();
		receive_DATA_MSG_ACK();
		receive_XCMP_DEVICE_INIT_STATUS_COMPLETE();
		send_DATA_MSG_ACK_COMPLETE();
	}

	private void send_DATA_MSG_ACK_COMPLETE() throws IOException {
		byte DATA_MSG_ACK_COMPLETE[] = { (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x0C, (byte) 0x01, (byte) 0x01,
				(byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x04, (byte) 0x00,
				(byte) 0x00 };
		output.write(DATA_MSG_ACK_COMPLETE);
		System.out.println("DATA_MSG_ACK_COMPLETE");
		System.out.println("XCMP initialization complete");
	}

	private void receive_DATA_MSG_ACK() throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcesss.ReadingData(data);
		System.out.println("Receive DATA_MSG_ACK");
		System.out.println(HexicmalData);

	}

	private void receive_XCMP_DEVICE_INIT_STATUS_COMPLETE() throws IOException {
		byte data[] = new byte[21];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcesss.ReadingData(data);
		System.out.println("Receive XCMP_DEVICE_INIT_STATUS_COMPLETE");
		System.out.println(HexicmalData);

	}

	private void send_XCMP_DEVICE_INIT_STATUS() throws IOException {
		byte[] XCMP_DEVICE_INIT_STATUS = { (byte) 0x00, (byte) 0x1B, (byte) 0x00, (byte) 0x0B, (byte) 0x01, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0F,
				(byte) 0xB4, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x0A,
				(byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0xFF };
		output.write(XCMP_DEVICE_INIT_STATUS);
		System.out.println("XCMP_DEVICE_INIT_STATUS");
	}

	private void send_DATA_MSG_ACK() throws IOException {
		byte[] DATA_MSG_ACK = { (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x0C, (byte) 0x01, (byte) 0x00,
				(byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x03, (byte) 0x00,
				(byte) 0x00 };
		System.out.println("Send DATA_MSG_ACK");
		output.write(DATA_MSG_ACK);
	}

	private void receive_XCMP_DEVICE_INIT_STATUS() throws IOException {
		byte data[] = new byte[41];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcesss.ReadingData(data);
		System.out.println("Receive XCMP_DEVICE_INIT_STATUS");
		System.out.println(HexicmalData);
	}

	private void receive_DEVICE_SYSMAP_BROADCAST() throws IOException {
		byte data[] = new byte[31];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcesss.ReadingData(data);
		System.out.println("Receive DEVICE_SYSMAP_BROADCAST");
		System.out.println(HexicmalData);
	}
}
