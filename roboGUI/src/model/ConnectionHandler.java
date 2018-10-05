package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import Inventory.InventoryItem;
import Inventory.InventoryItemDAO;


public class ConnectionHandler extends Thread{
	private static Object lock = new Object();
	private static final String HOST = "10.0.1.1";
	private static final int PORT = 1111;
	private boolean terminated = false;
	private Buffer_IF buffer;
	private boolean transferInProgress;
	private InventoryItemDAO dao;
	
	public ConnectionHandler(Buffer_IF buffer) {
		this.buffer = buffer;
	}
	
	public void run() {
		System.out.println("started thread");
		while(!terminated) {
			if(buffer.getBuffer() > 0 && !transferInProgress) {
				transferInProgress = true;
				System.out.println("aloittaa");
				DataHandler dataHandler = new DataHandler(this, buffer);
				dataHandler.start();
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//For checking if robot is running
	public void connect() throws UnknownHostException, IOException {	
		Socket socket = new Socket(HOST, PORT);
		socket.close();
	}
	
	
	
	public void transferReady(InventoryItem item) {
		System.out.println("valmis");
		//dao.addItem(item);
		transferInProgress = false;
	}

}
