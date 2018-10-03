package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionHandler extends Thread{
	private Socket socket;
	
	public void connect() throws UnknownHostException, IOException {	
		socket = new Socket("10.0.1.1", 1111);
	}
	
	public boolean sendData(String command) {
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(command);
			dos.flush();
			dos.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
