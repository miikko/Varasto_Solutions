package connection2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import lejos.robotics.navigation.Waypoint;
import navigation2.Navigation2;

public class Connection2 extends Thread {

	private ServerSocket server;
	private Socket socket;
	private Navigation2 navi2;
	public static Map<Waypoint, ArrayList<Integer>> orders = new HashMap<>();
	private boolean terminate = false;
	DataOutputStream out;
	private static Object lock = new Object();

	public Connection2(Navigation2 navi2) {
		this.navi2 = navi2;
	}

	public void run() {
		try {
			server = new ServerSocket(1111);
			while (!terminate) {
				socket = server.accept();
				DataInputStream in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				Waypoint wp = new Waypoint(0, 0);
				wp.loadObject(in);
				int shelfNumber;
				shelfNumber = in.readInt();
				makeNewOrder(wp, shelfNumber);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void terminate() {
		terminate = true;
	}

	public static boolean noOrders() {
		Set<Waypoint> set = orders.keySet();
		synchronized(lock) {
			for (Waypoint waypoint : set) {
				if (!orders.get(waypoint).isEmpty()) {
					return false;
				}
			}
		}
		
		return true;
	}

	public static Waypoint getNextOrder() {
		Set<Waypoint> set = orders.keySet();
		synchronized(lock) {
			for (Waypoint waypoint : set) {
				if (!orders.get(waypoint).isEmpty()) {
					return waypoint;
				}
			}
		}
		return null;
	}

	public void makeNewOrder(Waypoint waypoint, int shelfNumber) {
		
		synchronized(lock) {
			if (orders.get(waypoint) == null) {
				orders.put(waypoint, new ArrayList<Integer>());
			}
			orders.get(waypoint).add(shelfNumber);
		}
	}

	public void sendUpdate(String message) {

		try {
			out.writeUTF(message);
			if(message.equals("Finished")) {
				out.flush();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void sendColor(int color) {
		String colorName = null;
		
		switch(color) {
			case 0:
				colorName = "Green";
				break;
				
			case 1:
				colorName = "Red";
				break;
				
			case 2:
				colorName = "Blue";
				break;
				
			case 3:
				colorName = "Yellow";
				break;
				
			case 4:
				colorName = "White";
				break;
		}
		System.out.println(colorName);	
		try {
			out.writeUTF(colorName);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
