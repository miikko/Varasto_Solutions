package actions2;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Lift2 {
	private EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private boolean state;
	
	// pystytään keskeyttämään kesken noston
	public void liftUp(int shelfNumber) {
		
		switch(shelfNumber) {
		case 1:
			liftMotor.rotate(40);
			break;
		case 2:
			liftMotor.rotate(140);
			break;
		}
	}
	
	// pystytään keskeyttämään kesken noston
	public void liftDown() {
		liftMotor.rotate(-140, false);
	}
	
	public void liftUpShort(int angle) {
		liftMotor.rotate(angle);
	}
	
	public boolean getState() {
		return state;
	}
	
	public boolean isMoving() {
		return liftMotor.isMoving();
	}
}