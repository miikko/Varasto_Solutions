package actions2;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class IRSensor {

	private EV3IRSensor ir = new EV3IRSensor(SensorPort.S2);
	private SampleProvider distance = ((EV3IRSensor)ir).getSeekMode();
	float[] sample = new float[distance.sampleSize()];
	
	
	public float getDistance() {
		distance.fetchSample(sample, 0);
		
		float etäisyys = sample[1];
		return etäisyys;
	}
}