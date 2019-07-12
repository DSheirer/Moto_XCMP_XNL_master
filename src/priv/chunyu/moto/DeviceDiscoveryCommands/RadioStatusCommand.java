package priv.chunyu.moto.DeviceDiscoveryCommands;

import java.io.IOException;

public abstract interface RadioStatusCommand {
	static byte flag = 0x01;
	static void request() throws IOException {
	} // pc to raido
	static void request_ack (byte flag) throws IOException {
	} //radio to pc
	static void reply() throws IOException {
	} //radio to pc
	static void reply_ack(byte data) throws IOException {
	} //pc to radio
	static void checkflag() {} // if flag >0x07 then reset it to 0
}
