package navigation;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.MirrorMotor;
import lejos.robotics.chassis.*;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.*;
import lejos.robotics.pathfinding.*;
import lejos.utility.Delay;

public class Navigation {

	//TODO assign correct MotorPorts
	private EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	
	
	//TODO Diameters, Offsets
	private Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 1.72).offset(6.25).invert(true);	
	private Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 1.72).offset(-6.25).invert(true);
	
	//TODO check correct wheels
	private Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	private PoseProvider poseProvider = chassis.getPoseProvider();
	private Navigator navi = new Navigator(pilot, poseProvider);
	private LineMap map = null;
	
	public Navigation(Pose startPose) {
		poseProvider.setPose(startPose);

	}
	
	public void executePath(Waypoint targetWaypoint) {
		Pose pose = poseProvider.getPose();
		navi.followPath(findPath(pose, targetWaypoint));
		navi.waitForStop();
		navi.clearPath();
	}
	
	public Pose getPose() {
		return poseProvider.getPose();
	}
	
	public void setLineMap(LineMap map) {
		this.map = map;
	}
	
	//TODO requires testing
	private Path findPath(Pose pose, Waypoint targetWaypoint) {
		Waypoint wp = null;
		Path path = new Path();
		
		wp = new Waypoint(pose.getX(), pose.getY()); 
		
		path.add(wp);
		path.add(targetWaypoint);
		return path;
	}
	
	public void rotate90() {
		pilot.rotate(45);
	}
	
	public void driveStraight(boolean forward) {
		if(forward) {
			pilot.travel(9.5);
		}else {
			pilot.travel(-9.5);
		}
		pilot.stop();
	}
	
	public void faceShelf(boolean leftShelf) {
		Pose currentPose = chassis.getPoseProvider().getPose();
		System.out.println(currentPose.getHeading());
		int amount;
		if(!leftShelf) {
			amount = 90;
		}else {
			amount = -90;
		}
		pilot.rotate(amount - currentPose.getHeading(), false);
	}
	
}
