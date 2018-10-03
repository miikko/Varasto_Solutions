package connection2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Handler2 {
	
	private Socket socket;
	private Connection2 connection2;
	
	public Handler2(Connection2 connection2, Socket socket) {
		this.connection2 = connection2;
		this.socket = socket;
	}
	
	public void run() {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void terminate() {
		
	}
}
