package navigation2;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.*;
import lejos.robotics.pathfinding.*;

public class Navigation2 {

	private EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

	private Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 1.93).offset(69);	
	private Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 1.93).offset(-69);
	
	private Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	private PoseProvider poseProvider = chassis.getPoseProvider();
	private Navigator navi = new Navigator(pilot, poseProvider);
	private LineMap map = null;
	
	public Navigation2(Pose startPose) {
		poseProvider.setPose(startPose);

	}
	
	public void executePath(Waypoint targetWaypoint, boolean store) {
		Pose pose = poseProvider.getPose();
		navi.followPath(findPath(pose, targetWaypoint, store));
		navi.waitForStop();
		navi.clearPath();
	}
	
	public Pose getPose() {
		return poseProvider.getPose();
	}
	
	public void setLineMap(LineMap map) {
		this.map = map;
	}
	
	private Path findPath(Pose pose, Waypoint targetWaypoint, boolean store) {
		Waypoint wp = null;
		Path path = new Path();
		
		if(store) {
			wp = new Waypoint(pose.getX(), targetWaypoint.getY());
		} else {
			wp = new Waypoint(targetWaypoint.getX(), pose.getY());
		}
		
		path.add(wp);
		path.add(targetWaypoint);
		return path;
	}
	
	public void rotateAngle(int angle) {
		pilot.rotate(angle);
		pilot.stop();
	}
	
	public void driveForward(int distance) {
		pilot.travel(distance);
		pilot.stop();
	}
	
	public void driveBackward(int distance) {
		pilot.travel(-distance);
		pilot.stop();
	}
}
