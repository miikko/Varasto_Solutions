package model;

import java.io.*;
import java.net.Socket;
import java.util.*;

import lejos.robotics.navigation.Waypoint;
/**
 * This class purpose is to handle data in new sockets.
 * @author Eero
 *
 */
public class DataHandler extends Thread {
	private Socket socket;
	private ConnectionHandler connectionHandler;
	private Buffer_IF buffer;
	private int[] nextEmptySlot;
	private static Map<Integer, Waypoint> STORAGE_MAP;
	static{
		STORAGE_MAP = new HashMap<>();
		STORAGE_MAP.put(0, new Waypoint(32, -28,0));
		STORAGE_MAP.put(1, new Waypoint(63, -70,0));
		STORAGE_MAP = Collections.unmodifiableMap(STORAGE_MAP);	
	}
	
	/**
	 * 
	 * @param connectionHandler ConnectionHandler.
	 * @param buffer Packet Buffer.
	 * @param nextEmptySlot Next Empty Slot in database.
	 */
	public DataHandler(ConnectionHandler connectionHandler, Buffer_IF buffer, int[] nextEmptySlot) {
		this.connectionHandler = connectionHandler;
		this.buffer = buffer;
		this.nextEmptySlot = nextEmptySlot;
	}
	
	public void run() {

			try {
				socket = new Socket(ConnectionHandler.HOST,ConnectionHandler.PORT);
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				Waypoint wp = STORAGE_MAP.get(nextEmptySlot[0]);
				wp.dumpObject(dos);
				dos.writeInt(nextEmptySlot[1]);
				dos.flush();
				String color = dis.readUTF();
				System.out.println(dis.readUTF());
				System.out.println(dis.readUTF());
				buffer.remove();
				InventoryItem item = new InventoryItem(color, nextEmptySlot[0], nextEmptySlot[1]);
				connectionHandler.transferReady(item);
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			
	}
	
}
