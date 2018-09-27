package navigation;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.*;
import lejos.robotics.pathfinding.*;

public class Navigation {

	//TODO assign correct MotorPorts
	private EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	
	//TODO Diameters, Offsets
	private Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 3.10).offset(-5.5);	
	private Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 3.10).offset(5.5);
	
	//TODO check correct wheels
	private Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	private PoseProvider poseProvider = chassis.getPoseProvider();
	private Navigator navi = new Navigator(pilot, poseProvider);
	private LineMap map = null;
	
	public Navigation(Pose startPose) {
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
	
	//TODO requires testing
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
	
	public void rotate90() {
		pilot.rotate(90);
	}
}
