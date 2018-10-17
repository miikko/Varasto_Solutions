package actions;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
/**
 * This class is used to handle color sensors.
 * @author Eero
 *
 */
public class ColorSensor {
	private EV3ColorSensor navSensor;
	private SampleProvider sampleProvider;
	float[] sample;

	public final static int PUNAINEN = 0, MUSTA = 1, LATTIA = 2;
	private static int objectCounter = 0;
	private int black[] = new int[3];
	private int red[] = new int[3];
	private int floor[] = new int[3];
	private int colorID;

	/**
	 * Constructor that calibrates colors when called.
	 * @param navSensor EV3Sensor
	 */
	public ColorSensor(EV3ColorSensor navSensor) {
		this.navSensor = navSensor;
		sampleProvider = navSensor.getRGBMode();
		sample = new float[sampleProvider.sampleSize()];
		if (objectCounter < 2) {
			kalibroi();
		}
		objectCounter++;
	}

	/**
	 * Calibrates the sensor by reading reference values for each color.
	 */
	private void kalibroi() {

		String tunniste;
		if (objectCounter == 0) {
			tunniste = "Vasemman";
		} else {
			tunniste = "Oikean";
		}
		
		System.out.println(tunniste + " moottorin kalibrointi");
		
		System.out.println("Mustan arvo");
		Button.waitForAnyPress();

		// Musta
		sampleProvider.fetchSample(sample, 0);
		for (int i = 0; i < 3; i++) {
			black[i] = Math.round(sample[i] * 765);
		}

		System.out.println("r=" + black[0] + " g=" + black[1] + " b=" + black[2]);
		Delay.msDelay(1000);
		LCD.clear();

		System.out.println("Punaisen arvo");
		Button.waitForAnyPress();

		// Punainen
		sampleProvider.fetchSample(sample, 0);
		for (int i = 0; i < 3; i++) {
			red[i] = Math.round(sample[i] * 765);
		}

		System.out.println("r=" + red[0] + " g=" + red[1] + " b=" + red[2]);
		Delay.msDelay(1000);
		LCD.clear();

		System.out.println("Lattian arvo");
		Button.waitForAnyPress();
		
		// Lattia
		sampleProvider.fetchSample(sample, 0);
		for (int i = 0; i < 3; i++) {
			floor[i] = Math.round(sample[i] * 765);
		}

		System.out.println("r=" + floor[0] + " g=" + floor[1] + " b=" + floor[2]);
		Delay.msDelay(1000);
		LCD.clear();
	}

	/**
	 * Compares detected color with reference colors and finds the closest color.
	 * 
	 * @return Detected color as integer. RED = 0, BLACK = 1, FLOOR = 2.
	 */
	public int getVäri() {
		
		int rgb[] = getRGB();
		double väri[] = new double[5];

		väri[PUNAINEN] = laskeVäri(red, rgb);
		väri[MUSTA] = laskeVäri(black, rgb);
		väri[LATTIA] = laskeVäri(floor, rgb);

		int lähinVäri;

		if (väri[PUNAINEN] < väri[MUSTA] && väri[PUNAINEN] < väri[LATTIA]) {
			lähinVäri = PUNAINEN;
		} else if (väri[MUSTA] < väri[PUNAINEN] && väri[MUSTA] < väri[LATTIA]) {
			lähinVäri = MUSTA;
		} else {
			lähinVäri = LATTIA;
		}

		return lähinVäri;
	}

	/**
	 * Calculates channel differences for specific color.
	 * @param referenssiVäri Reference color that is stored in calibration.
	 * @param väri Detected color.
	 * @return Result of calculated channel differences. The lower the calculated value the closer it is to the reference color.
	 */
	public double laskeVäri(int referenssiVäri[], int väri[]) {

		double tulos = 0;
		int calc[] = new int[3];
		for (int i = 0; i < 3; i++) {
			calc[i] = referenssiVäri[i] - väri[i];
		}

		for (int i = 0; i < 3; i++) {
			calc[i] *= calc[i];
		}

		for (int i = 0; i < 3; i++) {
			tulos += calc[i];
		}

		tulos = Math.sqrt(tulos);
		LCD.clear();
		LCD.drawString("" + tulos, 0, 6);

		return tulos;
	}
	/**
	 * Sets colorID
	 */
	public void setColorID() {
		colorID = navSensor.getColorID();
	}
	/**
	 * Checks if the set color box is still in the crane.
	 * @return
	 */
	public boolean itemInCrane() {
		if (colorID == navSensor.getColorID()) {
			return true;
		}
		return false;
	}
	/**
	 * Fetches RBG values.
	 * @return List of RBG values.
	 */
	public int[] getRGB () {
		
		int rgb[] = new int[3];

		sampleProvider.fetchSample(sample, 0);

		for (int i = 0; i < 3; i++) {
			rgb[i] = Math.round(sample[i] * 765);
		}
		
		return rgb;
	}

	public int[] getBlack() {
		return black;
	}

	public void setBlack(int[] black) {
		this.black = black;
	}

	public int[] getRed() {
		return red;
	}

	public void setRed(int[] red) {
		this.red = red;
	}

	public int[] getFloor() {
		return floor;
	}

	public void setFloor(int[] floor) {
		this.floor = floor;
	}

}
