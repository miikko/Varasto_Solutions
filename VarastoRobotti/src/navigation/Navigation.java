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
	private EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	private EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	//TODO Diameters, Offsets
	private Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 3.10).offset(-8.40);	
	private Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 3.10).offset(8.40);
	
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
			wp = new Waypoint(pose.getY(), (float)targetWaypoint.getX());
		} else {
			wp = new Waypoint(pose.getX(), (float)targetWaypoint.getY());
		}
		
		path.add(wp);
		path.add(targetWaypoint);
		return path;
	}
}
