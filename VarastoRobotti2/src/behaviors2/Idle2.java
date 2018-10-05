package behaviors2;

import connection2.Orders;
import lejos.robotics.subsumption.Behavior;
import navigation2.Navigation2;

public class Idle2 implements Behavior{

	private MakeTransfer2 mt2;
	private Orders orders;
	private Navigation2 navigation;
	private volatile boolean suppressed;
	
	public Idle2(Orders orders) {
		this.orders = orders;
	}
	
	@Override
	public boolean takeControl() {
		return orders.checkIfEmpty();
	}

	@Override
	public void action() {
		suppressed = false;

		
		while(!suppressed) {
			Thread.yield();
		}
			
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
