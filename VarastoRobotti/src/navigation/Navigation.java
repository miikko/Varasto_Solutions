package navigation;

import actions.ColorSensor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.*;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.*;
import lejos.robotics.pathfinding.*;
import lejos.utility.Delay;

public class Navigation {

	// TODO assign correct MotorPorts
	private EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

	// TODO Diameters, Offsets
	private Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 1.89).offset(6.575).invert(true);
	private Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 1.91).offset(-6.575).invert(true);

	// TODO check correct wheels
	private Chassis chassis = new WheeledChassis(new Wheel[] { leftWheel, rightWheel },
			WheeledChassis.TYPE_DIFFERENTIAL);
	private MovePilot pilot = new MovePilot(chassis);
	private PoseProvider poseProvider = chassis.getPoseProvider();

	private ColorSensor leftColorSensor;
	private ColorSensor rightColorSensor;

	private boolean lastTurnLeft = false;

	public Navigation(ColorSensor leftColorSensor, ColorSensor rightColorSensor) {
		this.leftColorSensor = leftColorSensor;
		this.rightColorSensor = rightColorSensor;
	}

	public void followLine() {
		
		leftMotor.setAcceleration(3000);
		rightMotor.setAcceleration(3000);
		leftMotor.setSpeed(250);
		rightMotor.setSpeed(250);
		leftMotor.synchronizeWith(new RegulatedMotor[] {rightMotor});
		
		while (leftColorSensor.getVäri() != ColorSensor.PUNAINEN
				&& rightColorSensor.getVäri() != ColorSensor.PUNAINEN) {

			while (leftColorSensor.getVäri() == ColorSensor.MUSTA && rightColorSensor.getVäri() == ColorSensor.MUSTA) {
				leftMotor.startSynchronization();
				leftMotor.backward();
				rightMotor.backward();
				leftMotor.endSynchronization();
			}
			
			leftMotor.startSynchronization();
			leftMotor.stop();
			rightMotor.stop();
			leftMotor.endSynchronization();
			
			
			if (leftColorSensor.getVäri() == ColorSensor.PUNAINEN || rightColorSensor.getVäri() == ColorSensor.PUNAINEN) {
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
			
			if (leftColorSensor.getVäri() == ColorSensor.LATTIA && rightColorSensor.getVäri() == ColorSensor.LATTIA) {
				
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
	
	public void turnAround() {
		pilot.setAngularAcceleration(30);
		pilot.setAngularSpeed(90);
		pilot.rotate(180);
	}


	public void driveStraight(boolean forward) {
		if (forward) {
			pilot.travel(9.5);
		} else {
			pilot.travel(-9.5);
		}
		pilot.stop();
	}


	public void rotateToStartingHeading() {
		Pose currentPose = chassis.getPoseProvider().getPose();
		float currentHeading = currentPose.getHeading();
		pilot.rotate(currentHeading - currentHeading);
	}

}
