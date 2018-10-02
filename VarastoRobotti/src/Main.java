import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.Pose;
import lejos.utility.Delay;
import navigation.Navigation;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Navigation navi = new Navigation(new Pose(0,0,0));
		navi.rotate90();
//		navi.driveStraight();
		navi.faceShelf(true);
		navi.faceShelf(false);

	}

}
