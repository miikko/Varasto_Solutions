package navigation;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.*;

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
	
	public Navigation() {
		
	}
	
	public void executePath(Waypoint targetWaypoint) {
		//TODO implement method
	}
	
	public Pose getPose() {
		//TODO implement method
		return null;
	}
	
	public void setLineMap(LineMap map) {
		this.map = map;
	}
}
