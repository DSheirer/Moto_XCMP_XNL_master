package priv.chunyu.moto.xcmp;

import java.io.IOException;

import priv.chunyu.moto.xnl.XNL;
import priv.chunyu.moto.xnl.XNLsocket;

public class XCMP extends XNL {
	public XCMP() throws IOException, InterruptedException {

	}

	public void run() {
		try {
			XCMPsocket XCMPsocket=new XCMPsocket();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}