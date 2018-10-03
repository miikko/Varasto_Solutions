package behaviors2;

import actions2.Lift2;
import connection2.Orders;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import navigation2.Navigation2;

public class MakeTransfer2 implements Behavior{

	private volatile boolean suppressed = false;
	private Navigation2 navigation2;
	private Orders orders; 
	private Waypoint homeWP = new Waypoint(200,200);
	private Lift2 lift2;
	
	public MakeTransfer2(Navigation2 navigation2, Orders orders) {
		this.navigation2 = navigation2;
	}
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (!orders.checkIfEmpty());
	}

	@Override
	public void action() {
		
		suppressed = false;
		while(!orders.checkIfEmpty()) {
			navigation2.executePath(orders.getNextOrder(), true);
			
			navigation2.driveForward(10);
			lift2.liftUp();
			Delay.msDelay(1000);
			navigation2.driveBackward(10);
			lift2.liftDown();
			
			navigation2.executePath(homeWP, false);
		}
		Thread.yield();
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
