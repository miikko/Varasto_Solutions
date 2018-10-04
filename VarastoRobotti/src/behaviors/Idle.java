package behaviors;

import java.util.Set;

import connection.Connection;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;

public class Idle implements Behavior {

	private volatile boolean suppressed = false;
	
	
	public Idle() {
		
	}
	
	@Override
	public boolean takeControl() {
		
		if (Connection.orders == null) {
			return true;
		}
		Set<Waypoint> keys = Connection.orders.keySet();
		
		for (Waypoint key : keys) {
			if (!Connection.orders.get(key).isEmpty()) {
				return false;
			}
		}
		return true;
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
