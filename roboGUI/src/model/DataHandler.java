package model;

import java.io.*;
import java.net.Socket;

import lejos.robotics.navigation.Waypoint;

public class DataHandler extends Thread {
	public static final String ADD_TO_STORAGE = "ADD_STORAGE";
	private Socket socket;
	private ConnectionHandler connectionHandler;
	private Buffer_IF buffer;
	private int[] list;
	
	public DataHandler(Socket socket, ConnectionHandler connectionHandler, Buffer_IF buffer, int[] list) {
		this.socket = socket;
		this.connectionHandler = connectionHandler;
		this.buffer = buffer;
		this.list = list;
	}
	
	public void run() {

			try {
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				Waypoint wp = new Waypoint(10,50);
				wp.dumpObject(dos);
				dos.writeInt(list[1]);
				dos.flush();
				String color = dis.readUTF();
				System.out.println(dis.readUTF());
				System.out.println(dis.readUTF());
				buffer.remove();
				InventoryItem item = new InventoryItem(color, list[0], list[1]);
				connectionHandler.transferReady(item);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			//dos.writeUTF(ADD_TO_STORAGE); //COMMAND
			//dos.flush();
			//dis.readBoolean();
			
			
	}
}
