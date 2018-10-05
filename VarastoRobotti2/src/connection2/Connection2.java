package connection2;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lejos.robotics.navigation.Waypoint;
import navigation2.Navigation2;

public class Connection2 extends Thread{

	private ServerSocket server;
	private Socket socket;
	private Navigation2 navi2;
	public static Map<Waypoint, ArrayList<Integer>> orders = new HashMap<>();
	private boolean terminate = false;
	
	public Connection2(Navigation2 navi2) {
		this.navi2 = navi2;
	}
	
	public void run() {
		try {
			server = new ServerSocket(1111);
			while(!terminate) {
				socket = server.accept();
				DataInputStream in = new DataInputStream(socket.getInputStream());
				Waypoint wp = new Waypoint(0,0);
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
		for(Waypoint waypoint : set) {
			if(!orders.get(waypoint).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public static Waypoint getNextOrder(){
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
