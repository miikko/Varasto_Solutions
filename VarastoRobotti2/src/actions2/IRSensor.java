package actions2;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

/**
 * Class which keeps track of distance between robots
 * 
 * @author JP
 *
 */

public class IRSensor {

	private EV3IRSensor ir = new EV3IRSensor(SensorPort.S2);
	private SampleProvider distance = ((EV3IRSensor)ir).getSeekMode();
	float[] sample = new float[distance.sampleSize()];
	
	/**
	 * returns distance to retriever robots ir-beacon.
	 * @return Distance as float
	 */
	public float getDistance() {
		distance.fetchSample(sample, 0);
		
		float etäisyys = sample[1];
		return etäisyys;
	}
	
	/**
	 * returns the heading towards beacon
	 * @return
	 */
	public float getHeading() {
		distance.fetchSample(sample, 0);
		
		float heading = sample[0];
		return heading;
	}
}