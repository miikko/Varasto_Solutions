package navigation2;

import actions2.ColorSensor2;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.*;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.*;
import lejos.robotics.pathfinding.*;
import lejos.utility.Delay;

/**
 * This class is creates the Motor-objects and uses them in its methods.
 * It provides methods for the robot to move 
 * The wheel values need to be modified if the robot is not built using our instructions.
 * @author Miikka Oksanen
 *
 */
public class Navigation2 {

	private EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

	private Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 1.89).offset(6.575).invert(true);
	private Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 1.91).offset(-6.575).invert(true);

	private Chassis chassis = new WheeledChassis(new Wheel[] { leftWheel, rightWheel },
			WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	private PoseProvider poseProvider = chassis.getPoseProvider();
	private Navigator navi = new Navigator(pilot, poseProvider);

	private ColorSensor2 leftColorSensor;
	private ColorSensor2 rightColorSensor;

	private boolean lastTurnLeft = false;

	public Navigation2(ColorSensor2 leftColorSensor, ColorSensor2 rightColorSensor) {
		this.leftColorSensor = leftColorSensor;
		this.rightColorSensor = rightColorSensor;
	}

	/**
	 * This method tells the robot to keep its 2 navigation color sensors on black while moving forward.
	 * It stops the motors when the navigation sensors detect red.
	 */
	public void followLine() {
		
		leftMotor.setAcceleration(3000);
		rightMotor.setAcceleration(3000);
		leftMotor.setSpeed(250);
		rightMotor.setSpeed(250);
		leftMotor.synchronizeWith(new RegulatedMotor[] {rightMotor});
		
		while (leftColorSensor.getVäri() != ColorSensor2.PUNAINEN
				&& rightColorSensor.getVäri() != ColorSensor2.PUNAINEN) {

			while (leftColorSensor.getVäri() == ColorSensor2.MUSTA && rightColorSensor.getVäri() == ColorSensor2.MUSTA) {
				leftMotor.startSynchronization();
				leftMotor.backward();
				rightMotor.backward();
				leftMotor.endSynchronization();
			}
			
			leftMotor.startSynchronization();
			leftMotor.stop();
			rightMotor.stop();
			leftMotor.endSynchronization();
			
			
			if (leftColorSensor.getVäri() == ColorSensor2.PUNAINEN || rightColorSensor.getVäri() == ColorSensor2.PUNAINEN) {
				System.out.println("Näki punaista");
				break;
			}
			
			double leftComparable = leftColorSensor.laskeVäri(leftColorSensor.getBlack(), leftColorSensor.getRGB());
			double rightComparable = rightColorSensor.laskeVäri(rightColorSensor.getBlack(), rightColorSensor.getRGB());
			if (Math.abs(leftComparable - rightComparable) > 15) {
				if (leftComparable > rightComparable) {
					leftMotor.backward();
					lastTurnLeft = false;
				} else {
					rightMotor.backward();
					lastTurnLeft = true;
				}
				Delay.msDelay(500);
				leftMotor.startSynchronization();
				leftMotor.stop();
				rightMotor.stop();
				leftMotor.endSynchronization();
			}
			
			if (leftColorSensor.getVäri() == ColorSensor2.LATTIA && rightColorSensor.getVäri() == ColorSensor2.LATTIA) {
				
				if (lastTurnLeft) {
					leftMotor.backward();
				} else {
					rightMotor.backward();
				}
				Delay.msDelay(500);
			}
		}
		System.out.println("End of the line.");
	}
	
	/**
	 * Rotates the robot 180 degrees.
	 */
	public void turnAround() {
		pilot.setAngularAcceleration(30);
		pilot.setAngularSpeed(90);
		pilot.rotate(180);
	}

	/**
	 * Getter
	 * @return the robots current Pose.
	 */
	public Pose getPose() {
		return poseProvider.getPose();
	}

	/**
	 * Finds and returns a path between the given targetWaypoint and the robot's current Pose.
	 * @param pose
	 * @param targetWaypoint
	 * @return a path between the given targetWaypoint and the robot's current Pose.
	 */
	private Path findPath(Pose pose, Waypoint targetWaypoint) {
		Waypoint wp = null;
		Path path = new Path();

		wp = new Waypoint(pose.getX(), pose.getY());

		path.add(wp);
		path.add(targetWaypoint);
		return path;
	}

	/**
	 * Tells the robot to move forward/backward for a certain distance. The direction depends on the parameter.
	 * @param forward
	 */
	public void driveStraight(boolean forward) {
		if (forward) {
			pilot.travel(9.5);
		} else {
			pilot.travel(-9.5);
		}
		pilot.stop();
	}

	/**
	 * Rotates the robot so that its lifting crane is facing a shelf.
	 * This method presumes that the shelfs are facing the same direction as the robot's starting direction.
	 * @param leftShelf
	 */
	public void faceShelf(boolean leftShelf) {
		Pose currentPose = chassis.getPoseProvider().getPose();
		int amount;
		if (!leftShelf) {
			amount = 0;
		} else {
			amount = 0;
		}
		pilot.rotate(amount - currentPose.getHeading(), false);
	}

	/**
	 * Rotates the robot so that it is facing the same direction that it faced when the program started.
	 */
	public void rotateToStartingHeading() {
		Pose currentPose = chassis.getPoseProvider().getPose();
		float currentHeading = currentPose.getHeading();
		pilot.rotate(currentHeading - currentHeading);
	}

	/**
	 * Setter
	 * @param pose
	 */
	public void setPose(Pose pose) {
		poseProvider.setPose(pose);
	}
}

