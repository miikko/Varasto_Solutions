package behaviors2;


import actions2.IRSensor;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class CollisionAvoidance implements Behavior{

	private Navigator navi;
	private IRSensor ir;
	private volatile boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return ir.getDistance() < 30 && ir.getDistance() > 10; // ir.getDistance() otetaan 30cm - 10cm väliltä, koska sensorin käynnistyessä lukema näyttää 0:llaa.
	}

	@Override
	public void action() {
		suppressed = false;
		
		while(ir.getDistance() < 30) {
			navi.stop();
		}
		Thread.yield();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}

