package model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Eero
 *
 */
public class ConnectionHandler extends Thread{
	public static final String HOST = "10.0.1.1";
	public static final int PORT = 1111;
	
	private boolean terminated = false;
	private Buffer_IF buffer;
	private boolean transferInProgress;
	private InventoryItemDAO dao;
	
	/**
	 * ConnectionHandler constructor.
	 * Creates new DataAccessObject for InventoryItems.
	 * @param buffer
	 */
	public ConnectionHandler(Buffer_IF buffer) {
		this.buffer = buffer;
		dao = new InventoryItemDAO();
	}
	
	public void run() {
		while(!terminated) {
			if(buffer.getBuffer() > 0 && !transferInProgress) {
				transferInProgress = true;
				System.out.println("aloittaa");
				int[] nextEmptySlot = dao.getNextEmptySpot();
				DataHandler dataHandler = new DataHandler(this, buffer, nextEmptySlot);
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
	
	/**
	 * Method for checking connection.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException {	
		Socket socket = new Socket(HOST, PORT);
		socket.close();
	}
	
	
	/**
	 * Method for telling ConnectionHandler that transfer is ready.
	 * Adds item in parameter into database.
	 * @param item
	 */
	public void transferReady(InventoryItem item) {
		System.out.println("valmis");
		dao.addItem(item);
		transferInProgress = false;
	}

}
