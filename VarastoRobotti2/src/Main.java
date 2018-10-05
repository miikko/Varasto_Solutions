

import behaviors2.Idle2;
import behaviors2.MakeTransfer2;
import connection2.Connection2;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import navigation2.Navigation2;

public class Main {

	public static void main(String[] args) {
		
		Navigation2 navi = new Navigation2(new Pose(0,0,0));
		Connection2 connection = new Connection2(navi);
		connection.start();
		Behavior idle = new Idle2();
		Behavior makeTransfer = new MakeTransfer2(navi, connection);
		Behavior[] bArray = {idle, makeTransfer };
		Arbitrator arby = new Arbitrator(bArray);
		arby.go();
	}

}
