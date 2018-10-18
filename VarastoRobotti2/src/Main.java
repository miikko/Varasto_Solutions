import actions2.ColorSensor2;
import behaviors2.Idle2;
import behaviors2.MakeTransfer2;
import connection2.Connection2;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import navigation2.Navigation2;

/**
 * This class creates various objects and behaviors.
 * Finally it starts the program execution.
 * @author 
 *
 */
public class Main {

	public static void main(String[] args) {
		
		ColorSensor2 leftColorSensor = new ColorSensor2(new EV3ColorSensor(SensorPort.S3));
		ColorSensor2 rightColorSensor = new ColorSensor2(new EV3ColorSensor(SensorPort.S4));
		Navigation2 navi = new Navigation2(leftColorSensor, rightColorSensor);
		Connection2 connection = new Connection2(navi);
		connection.start();
		Behavior idle = new Idle2();
		Behavior makeTransfer = new MakeTransfer2(navi, connection);
		Behavior[] bArray = {idle, makeTransfer };
		Arbitrator arby = new Arbitrator(bArray);
		arby.go();
	}

}
