package behaviors2;


import actions2.IRSensor;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

/**
 * Behavior to avoid collision between 2 robots
 * 
 * @author JP
 *
 */

public class CollisionAvoidance implements Behavior{

	private Navigator navi;
	private IRSensor ir;
	private volatile boolean suppressed = false;
	
	/**
	 * Takes control of the robot when distance between robots is between 30 - 10 cm.
	 */
	@Override
	public boolean takeControl() {
		return ir.getDistance() < 30 && ir.getDistance() > 10; // ir.getDistance() otetaan 30cm - 10cm väliltä, koska sensorin käynnistyessä lukema näyttää 0:llaa.
	}

	/**
	 * Keeps the robot stopped while distance to other robot is below 30 cm. When distance is larger robot continues it's path.
	 */
	@Override
	public void action() {
		suppressed = false;
		
		while(ir.getDistance() < 30) {
			navi.stop();
		}
		navi.followPath();
		Thread.yield();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}

