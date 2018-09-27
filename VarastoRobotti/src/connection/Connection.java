package connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.robotics.navigation.Waypoint;
import navigation.Navigation;

public class Connection extends Thread{

	private ServerSocket serv;
	private Socket socket;
	private Navigation navigation;
	private Orders orders;
	private boolean terminate = false;
	
	public Connection(Navigation navigation) {
		this.navigation = navigation;
	}
	
	public void run() {
		try {
			serv = new ServerSocket(1111);
			while(!terminate) {
				socket = serv.accept();
				Handler handler = new Handler(this, socket);
				handler.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void terminate() {
		terminate = true;
	}
	
	public void makeNewOrder(Waypoint waypoint) {
		orders.addOrder(waypoint);
	}
}
