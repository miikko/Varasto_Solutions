import actions.Lift;
import behaviors.Idle;
import behaviors.MakeTransfer;
import connection.Connection;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import navigation.Navigation;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Navigation navi = new Navigation(new Pose(0,0,0));
		Connection connection = new Connection(navi);
		connection.start();
		Behavior idle = new Idle();
		Behavior makeTransfer = new MakeTransfer(navi);
		Behavior[] bArray = {idle, makeTransfer };
		Arbitrator arby = new Arbitrator(bArray);
		arby.go();

	}

}
