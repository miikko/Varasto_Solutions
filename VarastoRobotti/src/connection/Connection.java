package connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lejos.robotics.navigation.Waypoint;
import navigation.Navigation;
/**
 * This class handles the connection between EV3 and PC in a thread and saves the received orders into a HashMap.
 * @author Arttu Halonen
 *
 */
public class Connection extends Thread{

	private ServerSocket serv;
	private Socket socket;
	public static Map<Waypoint, ArrayList<Integer>> orders = new HashMap<Waypoint, ArrayList<Integer>>(); 
	private boolean terminate = false;
	private DataOutputStream out;
	
	public Connection() {
		
	}
	/**
	 * Opens the server, socket and input stream and keeps them open until terminated.
	 */
	public void run() {
		
		try {
			serv = new ServerSocket(1111);
			socket = serv.accept();
			DataInputStream in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			while(!terminate) {
				Waypoint waypoint = new Waypoint(0,0);
				waypoint.loadObject(in);
				int shelfNumber;
				shelfNumber = in.readInt();
				makeNewOrder(waypoint, shelfNumber);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Terminates the thread
	 */
	public void terminate() {
		terminate = true;
	}
	/**
	 * Checks whether there's orders or not
	 */
	public static boolean noOrders() {
		Set<Waypoint> set = orders.keySet();
		for(Waypoint waypoint : set) {
			if(!orders.get(waypoint).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Finds the next undone order and returns its waypoint
	 */
	public static Waypoint getNextOrder() {
		Set<Waypoint> set = orders.keySet();
		for(Waypoint waypoint : set) {
			if(!orders.get(waypoint).isEmpty()) {
				return waypoint;
			}
		}
		return null;
	}
	/**
	 * Adds a new order to the HashMap with Waypoint and shelf number parameters.
	 */
	public void makeNewOrder(Waypoint waypoint, int shelfNumber) {
		if (orders.get(waypoint) == null) {
			orders.put(waypoint, new ArrayList<Integer>());
		}
		orders.get(waypoint).add(shelfNumber);
	}
	/**
	 * Sends a message to the PC when a transfer is finished
	 */
	public void sendUpdate(String message) {
		
		try {
			
			out.writeUTF(message);
			if (message.equals("Finished")) {
				out.flush();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
