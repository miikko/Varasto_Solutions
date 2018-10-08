package navigation;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.*;
import lejos.robotics.pathfinding.*;

public class Navigation {

	//TODO assign correct MotorPorts
	private EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	
	
	//TODO Diameters, Offsets
	private Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 1.89).offset(6.575).invert(true);	
	private Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 1.91).offset(-6.575).invert(true);
	
	//TODO check correct wheels
	private Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	private PoseProvider poseProvider = chassis.getPoseProvider();
	private Navigator navi = new Navigator(pilot, poseProvider);
	
	private Pose startPose;
	
	public Navigation(Pose startPose) {
		this.startPose = startPose;
		poseProvider.setPose(new Pose(0,0,0));
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
	
	//TODO requires testing
	private Path findPath(Pose pose, Waypoint targetWaypoint) {
		Waypoint wp = null;
		Path path = new Path();
		
		wp = new Waypoint(pose.getX(), pose.getY()); 
		
		path.add(wp);
		path.add(targetWaypoint);
		return path;
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
		int amount;
		if(!leftShelf) {
			amount = 0;
		}else {
			amount = 0;
		}
		pilot.rotate(amount - currentPose.getHeading(), false);
	}
	
	public void rotateToStartingHeading() {
		Pose currentPose = chassis.getPoseProvider().getPose();
		float currentHeading = currentPose.getHeading();
		pilot.rotate(currentHeading - currentHeading);
	}
	
	public void setPose(Pose pose) {
		poseProvider.setPose(pose);
	}
}
