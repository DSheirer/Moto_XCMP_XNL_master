package priv.chunyu.moto.xnl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class XNL extends Thread {
	public static Socket master;
	public static DataOutputStream output;
	public static DataInputStream input;
	public static void setConnection() throws UnknownHostException, IOException {
		master = new Socket("192.168.10.1", 8002);// Pc set as master ,and setup connection;// Pc
		output = new DataOutputStream(master.getOutputStream());
		input = new DataInputStream(master.getInputStream());// data input
	}
}
