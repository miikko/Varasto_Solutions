package actions;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Lift {
	private EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private boolean state;
	
	// 160 astetta = 11cm
	
	// pystytään keskeyttämään kesken noston
	public void liftUp() {
		liftMotor.rotate(130, false);
	}
	
	// pystytään keskeyttämään kesken noston
	public void liftDown() {
		liftMotor.rotate(-130, false);
	}
	
	public boolean getState() {
		return state;
	}
	
	public boolean isMoving() {
		return liftMotor.isMoving();
	}
}
