package main;

import java.io.IOException;

import GUI.StartInterface;
import priv.chunyu.moto.xnl.XNLsocket;

public class main {
	public static void main(String args[]) throws IOException, InterruptedException {
		XNLsocket Xnl_Connection = new XNLsocket();
		Xnl_Connection.run();
		new StartInterface ();
	}
}
