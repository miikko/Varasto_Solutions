package actions;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
/**
 * This class is used for initialising and controlling the lift motor.
 * @author 
 *
 */
public class Lift {
	private EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private boolean state;
	
	/**
	 * Lifts the forks to the right shelf height or picks up a package from the shelf
	 */
	public void liftUp(int shelfHeight, boolean shortLift) {
		liftMotor.setSpeed(80);
		if(!shortLift) {
			switch(shelfHeight) { 
			case 1:
				liftMotor.rotate(0, false);
				break;
			case 2:
				liftMotor.rotate(120, false);
				break;
			case 3:
				liftMotor.rotate(200, false);
				break;
			
			}
		}else {
			liftMotor.rotate(60, false); 
		}
	}
	/**
	 * Rotates the lift motor back to its start position
	 */
	public void liftDown() {
		liftMotor.setSpeed(80);
		liftMotor.rotate(-liftMotor.getTachoCount(), false); 
	}
	
	public boolean getState() {
		return state;
	}
	
	public boolean isMoving() {
		return liftMotor.isMoving();
	}
}
