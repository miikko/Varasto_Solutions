import actions.ColorSensor;
import behaviors.Idle;
import behaviors.MakeTransfer;
import connection.Connection;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import navigation.Navigation;

/**
 * This class creates various objects and behaviors.
 * Finally it starts the program execution.
 * @author 
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		ColorSensor leftColorSensor = new ColorSensor(new EV3ColorSensor(SensorPort.S3));
		ColorSensor rightColorSensor = new ColorSensor(new EV3ColorSensor(SensorPort.S4));
		ColorSensor liftSensor = new ColorSensor(new EV3ColorSensor(SensorPort.S2));
		Navigation navi = new Navigation(leftColorSensor, rightColorSensor);
		Connection connection = new Connection();
		connection.start();
		Behavior idle = new Idle();
		Behavior makeTransfer = new MakeTransfer(navi, connection, liftSensor);
		Behavior[] bArray = {idle, makeTransfer };
		Arbitrator arby = new Arbitrator(bArray);
		arby.go();

	}

}
