package behaviors;

import connection.Orders;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;
import navigation.Navigation;

public class MakeTransfer implements Behavior {
	private volatile boolean suppressed = false;
	private Navigation navigation;
	private Orders orders; 
	private Waypoint defaultWP = new Waypoint(40,0);
	
	public MakeTransfer(Navigation navigation, Orders orders) {
		this.navigation = navigation;
		this.orders = orders;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (!orders.checkIfEmpty());
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		suppressed = false;
		while(!orders.checkIfEmpty()) {
			navigation.executePath(orders.getNextOrder(), true);
			// TODO pickup rutiini tähän väliin
			navigation.executePath(defaultWP, false);
		}
		Thread.yield();
			

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;

	}

}
