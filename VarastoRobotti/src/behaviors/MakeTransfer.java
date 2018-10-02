package behaviors;

import java.util.Set;

import connection.Connection;
import connection.Orders;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;
import navigation.Navigation;

public class MakeTransfer implements Behavior {
	private volatile boolean suppressed = false;
	private Navigation navigation;
	private final Waypoint customerWP = new Waypoint(80,0);
	private final Waypoint defaultWP = new Waypoint(40,0);
	
	public MakeTransfer(Navigation navigation, Orders orders) {
		this.navigation = navigation;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		//return (!orders.checkIfEmpty());
		return !Connection.noOrders();
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		suppressed = false;
		while(Connection.noOrders()) {
			navigation.executePath(Connection.getNextOrder());
			
			// TODO pickup rutiini tähän väliin
			
			navigation.executePath(defaultWP);
		}
		Thread.yield();
			

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;

	}

}
