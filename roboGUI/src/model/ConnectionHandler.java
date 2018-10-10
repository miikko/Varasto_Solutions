package model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ConnectionHandler class is for handling new connections by creating DataHandler objects when new package needs to be picked.
 * @author Eero
 *
 */
public class ConnectionHandler extends Thread{
	/**
	 * Static Host name.
	 */
	public static final String HOST = "10.0.1.1";
	/**
	 * Static port number.
	 */
	public static final int PORT = 1111;
	
	private boolean terminated = false;
	private Buffer_IF buffer;
	private boolean transferInProgress;
	private InventoryItemDAO dao;
	
	/**
	 * 
	 * @param buffer Buffer for packets.
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
	 * Checks robot connection
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException {	
		Socket socket = new Socket(HOST, PORT);
		socket.close();
	}
	
	
	/**
	 * Inserts item into database and sets transfer progress to false.
	 * @param item InventoryItem.
	 */
	public void transferReady(InventoryItem item) {
		System.out.println("valmis");
		dao.addItem(item);	
	}
	
	public void setTransferInProgress(boolean state) {
		transferInProgress = state;
	}

}
