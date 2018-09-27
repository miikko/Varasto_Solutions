package behaviors;

import connection.Orders;
import lejos.robotics.subsumption.Behavior;

public class Idle implements Behavior {

	private volatile boolean suppressed = false;
	
	private Orders orders;
	
	public Idle(Orders orders) {
		this.orders = orders;
	}
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return orders.checkIfEmpty();
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		suppressed = false;
		Thread.yield();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
		
	}

}
