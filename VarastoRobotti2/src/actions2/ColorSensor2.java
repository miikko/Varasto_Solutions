package actions2;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Class which is used to calibrate colorsensors and check the colors.
 * @author JP & Miikka Oksanen
 *
 */

public class ColorSensor2 {
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
	 * Calibrates the colorsensor when created
	 * each sensor is calibrated
	 * @param navSensor
	 */
	public ColorSensor2(EV3ColorSensor navSensor) {
		this.navSensor = navSensor;
		sampleProvider = navSensor.getRGBMode();
		sample = new float[sampleProvider.sampleSize()];
		if (objectCounter < 3) {
			kalibroi();
		}
		objectCounter++;
	}

	/**
	 * Calibrates the colorsensor and 2 linefollowing colorsensors.
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
	 * Used to check the current color in front of the colorsensors
	 * @return
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
	 * Calculates the colors values using rgb values.
	 * @param referenssiVäri
	 * @param väri
	 * @return
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
	
	public void setColorID() {
		colorID = navSensor.getColorID();
	}
	
	public boolean itemInCrane() {
		if (colorID == navSensor.getColorID()) {
			return true;
		}
		return false;
	}
	
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

