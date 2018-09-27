package connection;

import java.net.Socket;

public class Handler extends Thread {

	private Socket socket;
	private Connection connection;
	
	public Handler(Connection connection, Socket socket) {
		this.connection = connection;
		this.socket = socket;
	}
	
	public void run() {
		//TODO 
	}
}
