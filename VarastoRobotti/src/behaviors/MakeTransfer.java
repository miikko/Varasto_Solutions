package behaviors;

import connection.Orders;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;
import navigation.Navigation;

public class MakeTransfer implements Behavior {
	private volatile boolean suppressed = false;
	private Navigation navigation;
	private Orders orders; 
	private Waypoint defaultWP = new Waypoint(0,0);
	
	public MakeTransfer(Navigation navigation, Orders orders) {
		this.navigation = navigation;
		this.orders = orders;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return orders.getNextOrder() != null;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		suppressed = false;
		navigation.executePath(orders.getNextOrder(), false);
		// TODO pickup rutiini tähän väliin
		navigation.executePath(defaultWP, true);

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;

	}

}
