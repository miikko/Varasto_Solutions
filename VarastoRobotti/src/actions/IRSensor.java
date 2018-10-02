package actions;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class IRSensor {

	private EV3IRSensor irsensor = new EV3IRSensor(SensorPort.S2);
	private SampleProvider etäisyys = ((EV3IRSensor)irsensor).getSeekMode();
	float[] sample = new float[etäisyys.sampleSize()];
	
	
	public float getDistance() {
		etäisyys.fetchSample(sample, 0);
		
		float etäisyys = sample[1];
		return etäisyys;
	}
}
