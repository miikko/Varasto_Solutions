package model;

import java.net.Socket;

public class DataHandler extends Thread {
	public static final String ADD_TO_STORAGE = "ADD_STORAGE";
	private Socket socket;
	private ConnectionHandler connectionHandler;
	private Buffer_IF buffer;
	
	public DataHandler(ConnectionHandler connectionHandler, Buffer_IF buffer) {
		//this.socket = socket;
		this.connectionHandler = connectionHandler;
		this.buffer = buffer;
	}
	
	public void run() {

			//DataInputStream dis = new DataInputStream(socket.getInputStream());
			//DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			//dos.writeUTF(ADD_TO_STORAGE); //COMMAND
			//dos.flush();
			//dis.readBoolean();
			
			System.out.println("hakee...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buffer.remove();
			System.out.println("haki");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//int containerNum = dis.readInt();
			//int shelfNum = dis.readInt();
			//String color = dis.readUTF();
			
			//InventoryItem item = new InventoryItem(color, containerNum, shelfNum);
			connectionHandler.transferReady(null);
	}
}
