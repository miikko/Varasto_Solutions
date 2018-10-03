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

public class Connection extends Thread{

	private ServerSocket serv;
	private Socket socket;
	private Navigation navigation;
	public static Map<Waypoint, ArrayList<Integer>> orders = new HashMap<Waypoint, ArrayList<Integer>>(); 
	private boolean terminate = false;
	
	public Connection(Navigation navigation) {
		this.navigation = navigation;
	}
	
	public void run() {
//		Waypoint wp = new Waypoint(30, 10);
//		makeNewOrder(wp, 2);
//		while(!terminate) {
//			
//		}
		try {
			serv = new ServerSocket(1111);
			while(!terminate) {
				socket = serv.accept();
				DataInputStream in = new DataInputStream(socket.getInputStream());
				Waypoint waypoint = new Waypoint(0,0);
				waypoint.loadObject(in);
				int shelfNumber;
				shelfNumber = in.readInt();
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
		for(Waypoint waypoint : set) {
			if(!orders.get(waypoint).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public static Waypoint getNextOrder() {
		Set<Waypoint> set = orders.keySet();
		for(Waypoint waypoint : set) {
			if(!orders.get(waypoint).isEmpty()) {
				return waypoint;
			}
		}
		return null;
	}
	
	public void makeNewOrder(Waypoint waypoint, int shelfNumber) {
		if (orders.get(waypoint) == null) {
			orders.put(waypoint, new ArrayList<Integer>());
		}
		orders.get(waypoint).add(shelfNumber);
	}
}
