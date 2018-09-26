import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Lift {
	private EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private boolean state;
	
	public void liftUp() {
		
	}
	
	public void liftDown() {
		
	}
	
	public boolean getState() {
		return state;
	}
	
}
