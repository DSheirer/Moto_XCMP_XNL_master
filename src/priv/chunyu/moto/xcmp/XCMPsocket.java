package priv.chunyu.moto.xcmp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

public class XCMPsocket extends XCMP {
	static Socket master;
	static DataOutputStream output;
	static DataInputStream input;
	byte[] srcAddress;
	byte[] dstAddress;
	public XCMPsocket() throws IOException, InterruptedException {
		System.out.println("XCMP Connection... start");
		master = XNL.master;
		output = XNL.output;
		input = XNL.input;
		srcAddress=DataProcess.get_SrcAddress();
		dstAddress=DataProcess.get_DstAddress(); //raido address
		receive_DEVICE_SYSMAP_BROADCAST();
		receive_XCMP_DEVICE_INIT_STATUS();
		send_DATA_MSG_ACK();
		//sleep(1000);
		send_XCMP_DEVICE_INIT_STATUS();
		receive_DATA_MSG_ACK();
		receive_XCMP_DEVICE_INIT_STATUS_COMPLETE();
		send_DATA_MSG_ACK_COMPLETE();
		receive_RRCTRLBRDCST();
		send_DATA_RRCTRLBRDCST_ACK_COMPLETE();
		RadioStatus_Request();
		receive_RadioStatus_Reply();
		receive_RadioStatus_Replyx();
	}

	private void send_DATA_RRCTRLBRDCST_ACK_COMPLETE() throws IOException {
		byte data[]= {
				(byte)0x00,(byte)0x0C,
				(byte)0X00,(byte)0x0C,
				(byte)0X01,//Protocol ID
				(byte)0x02,//FLag
				(byte)dstAddress[0],(byte)dstAddress[1],(byte)srcAddress[0],(byte)0X01//id?
				,(byte)0X01,(byte)0xBE,//Transaction ID
				(byte)0X00,(byte)0x00,//payloadLength
		};
		output.write(data);
		System.out.println("ACK for Complete");
		System.out.println("XCMP Establish Complete\n");
	}

	private void receive_RadioStatus_Replyx() throws IOException {
		byte data[] = new byte[18];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("UNKOWN");
		System.out.println(HexicmalData);
		DataProcess.MessageStructure(data);
	}

	private void receive_RRCTRLBRDCST() throws IOException {
		/* Remote Radio Control Broadcast*/
		byte data[] = new byte[25];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive Remote Radio Control Broadcast");
		System.out.println(HexicmalData);
		DataProcess.MessageStructure(data);
	}

	

	private void receive_RadioStatus_Reply() throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Brightness");
		System.out.println(HexicmalData);
		
	}

	private void RadioStatus_Request() throws IOException, InterruptedException {
		sleep(100);
		byte RadioStatus[]= {
				(byte)0x00,(byte)0x10,
				(byte)0X00,(byte)0x0B,
				(byte)0X01,//Protocol ID
				(byte)0x01,//FLag
				(byte)dstAddress[0],(byte)dstAddress[1],(byte)srcAddress[0],(byte)0X01//id?
				,(byte)0X02,(byte)0x01,//Transaction ID
				(byte)0X00,(byte)0x04,//payloadLength
				(byte)0X04,(byte)0x11,(byte)0X00,(byte)0X0A//payloadLeng
		};
		output.write(RadioStatus);
		System.out.println("Sending Raido Status Request");
		
	}

	private void receivetest() throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("TEST");
		System.out.println(HexicmalData);
		
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
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive DATA_MSG_ACK");
		System.out.println(HexicmalData);

	}

	private void receive_XCMP_DEVICE_INIT_STATUS_COMPLETE() throws IOException {
		byte data[] = new byte[21];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive XCMP_DEVICE_INIT_STATUS_COMPLETE");
		System.out.println(HexicmalData);

	}

	private void send_XCMP_DEVICE_INIT_STATUS() throws IOException {
		byte[] XCMP_DEVICE_INIT_STATUS = { (byte) 0x00, (byte) 0x1B, (byte) 0x00, (byte) 0x0B, (byte) 0x01, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0F,
				(byte) 0xB4, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x0A,
				(byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0xFF };
		output.write(XCMP_DEVICE_INIT_STATUS);
		System.out.println("Send XCMP_DEVICE_INIT_STATUS");
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
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive XCMP_DEVICE_INIT_STATUS");
		System.out.println(HexicmalData);
	}

	private void receive_DEVICE_SYSMAP_BROADCAST() throws IOException {
		byte data[] = new byte[31];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive DEVICE_SYSMAP_BROADCAST");
		System.out.println(HexicmalData);
	}
}

//000C000C01010000000602010000
//000C000C01010001000602010000

