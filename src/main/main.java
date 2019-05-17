package main;

import java.io.IOException;

import priv.chunyu.moto.xnl.XNL;

public class main {
	public static void main(String args[]) throws IOException, InterruptedException {
		XNL Xnl_Connection = new XNL();
		Xnl_Connection.start();
	}
}
