package priv.chunyu.moto.xnl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class XNL extends Thread {
	/* setup connection */
	Socket master = new Socket("192.168.10.1", 8002);// Pc set as master ,and setup connection;// Pc
	DataOutputStream output = new DataOutputStream(master.getOutputStream());
	DataInputStream input = new DataInputStream(master.getInputStream());;// data input
	public XNL() throws IOException, InterruptedException {

	}
	public void run() {
		try {
			XNLsocket XNLsocket = new XNLsocket();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void getConnection() throws UnknownHostException, IOException {
	
		
	}

}
