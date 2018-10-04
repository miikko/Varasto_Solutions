package actions;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Lift {
	private EV3MediumRegulatedMotor liftMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private boolean state;
	// 160 astetta = 11cm
	
	// pystytään keskeyttämään kesken noston
	public void liftUp(int shelfHeight, boolean shortLift) {
		liftMotor.setSpeed(80);
		if(!shortLift) {
			switch(shelfHeight) { // muuta korkeudet hyllykorkeuksia vastaaviksi
			case 1:
				liftMotor.rotate(60, false);
				break;
			case 2:
				liftMotor.rotate(100, false);
				break;
			case 3:
				liftMotor.rotate(140, false);
				break;
			
			}
		}else {
			liftMotor.rotate(30, false); // muuta hyllyväliin sopivaksi
		}
		
		//liftMotor.rotate(130, false);
	}
	
	// pystytään keskeyttämään kesken noston
	public void liftDown() {
		liftMotor.setSpeed(80);
		liftMotor.rotate(-liftMotor.getTachoCount(), false); // palauttaa nostomoottorin alkuasemaansa
		//liftMotor.rotate(-130, false);
	}
	
	public boolean getState() {
		return state;
	}
	
	public boolean isMoving() {
		return liftMotor.isMoving();
	}
}
