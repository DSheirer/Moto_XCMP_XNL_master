package priv.chunyu.moto.xnl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class XNLsocket {
	DataOutputStream output;// data output
	Socket master;// Pc
	byte[]  XNL_DEVICE_AUTH_KEY_REQUEST  = { (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0xFF };
	public XNLsocket() throws IOException, InterruptedException {
		master = new Socket("192.168.10.1", 8002);// Pc set as master ,and setup connection
		Thread.sleep(700);//waiting for  XNL_MASTER_STATUS_BROADCAST
		output = new DataOutputStream(master.getOutputStream());
		sendXNLREQUEST();
		
	}
	private void sendXNLREQUEST() throws IOException {
		output.write(XNL_DEVICE_AUTH_KEY_REQUEST);//sending XNL_DEVICE_AUTH_KEY_REQUEST to radio		
	}
}
