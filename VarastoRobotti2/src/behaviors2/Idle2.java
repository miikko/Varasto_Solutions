package behaviors2;

import connection2.Connection2;
import lejos.robotics.subsumption.Behavior;
import navigation2.Navigation2;

/**
 * Behavior class which keeps the robot idle.
 * @author JP
 *
 */
public class Idle2 implements Behavior{

	private volatile boolean suppressed;
	
	public Idle2() {
	}
	
	
	@Override
	/**
	 * When robot has no orders it goes to dormant state.
	 */
	public boolean takeControl() {
		return Connection2.noOrders();
	}

	@Override
	/**
	 * keeps the robot at dormant state when no orders are given.
	 */
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
