package priv.chunyu.moto.xnl;

import java.io.IOException;

public class XNL extends Thread{
	public XNL() throws IOException, InterruptedException{
		/*setup connection*/
		// waiting for the XNL sending  XNL_MASTER_STATUS_BROADCAST 
		XNLsocket XNLsocket= new XNLsocket();
		
	}
}
