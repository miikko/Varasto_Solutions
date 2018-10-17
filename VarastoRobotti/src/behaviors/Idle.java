package behaviors;

import java.util.Set;

import connection.Connection;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;

/**
 * Behavior class that keeps robot idle when there are no orders.
 * @author Eero
 *
 */
public class Idle implements Behavior {

	private volatile boolean suppressed = false;
	
	
	public Idle() {
		
	}
	
	@Override
	public boolean takeControl() {
		
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
		while (!suppressed) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
		
	}

}
