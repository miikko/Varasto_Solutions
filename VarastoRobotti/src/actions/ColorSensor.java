package actions;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorSensor {
	private EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S1);
	private SampleProvider väriTunnistin = ((EV3ColorSensor)sensor).getRGBMode();
	float[] sample = new float[väriTunnistin.sampleSize()];
	
	public static int PUNAINEN = 0, VIHREÄ = 1, SININEN = 2, KELTAINEN = 3, VALKOINEN = 4;
	private int red[] = new int[3];
	private int green[] = new int[3];
	private int blue[] = new int[3];
	private int yellow[] = new int[3];
	private int white[] = new int[3];
	
	public ColorSensor() {
		
	}
	
	public void kalibroi() {
		
		LCD.drawString("Punaisen arvot", 0, 1);
		Button.waitForAnyPress();
		
		//Punainen
		väriTunnistin.fetchSample(sample, 0);	
		for(int i=0;i<3;i++) {
			red[i] = Math.round(sample[i] * 765);
		}

		LCD.drawString("r=" + red[0] + " g=" + red[1] + " b=" + red[2], 0, 2);
		Delay.msDelay(1000);
		LCD.clear(1);
		LCD.drawString("Vihrean arvot", 0, 1);
		Button.waitForAnyPress();
		
		
		//Vihreä
		väriTunnistin.fetchSample(sample, 0);
		for(int i=0;i<3;i++) {
			green[i] = Math.round(sample[i] * 765);
		}
		LCD.drawString("r=" + green[0] + " g=" + green[1] + " b=" + green[2], 0, 3);
		Delay.msDelay(1000);
		LCD.clear(1);
		LCD.drawString("Sinisen arvot", 0, 1);
		Button.waitForAnyPress();
		
		//Sininen
		väriTunnistin.fetchSample(sample, 0);
		for(int i=0;i<3;i++) {
			blue[i] = Math.round(sample[i] * 765);
		}
		LCD.drawString("r=" + blue[0] + " g=" + blue[1] + " b=" + blue[2], 0, 4);
		Delay.msDelay(1000);
		LCD.clear(1);
		LCD.drawString("Keltaisen arvot", 0, 1);
		Button.waitForAnyPress();
		
		//Keltainen
		väriTunnistin.fetchSample(sample, 0);
		for(int i=0;i<3;i++) {
			yellow[i] = Math.round(sample[i] * 765);
		}
		LCD.drawString("r=" + yellow[0] + " g=" + yellow[1] + " b=" + yellow[2], 0, 5);
		Delay.msDelay(1000);
		LCD.clear(1);
		LCD.drawString("Valkoisen arvot", 0, 1);
		Button.waitForAnyPress();
		
		//Valkoinen
		väriTunnistin.fetchSample(sample, 0);
		for(int i=0;i<3;i++) {
			white[i] = Math.round(sample[i] * 765);
		}
		LCD.drawString("r=" + white[0] + " g=" + white[1] + " b=" + white[2], 0, 5);
		Delay.msDelay(1000);
		LCD.clear(1);
	}

	public int getVäri() {
		int rgb[] = new int[3];
		double väri[] = new double [5];
		
		väriTunnistin.fetchSample(sample, 0);
		
		for(int i=0;i<3;i++) {
			rgb[i] = Math.round(sample[i] * 765);
		}
		
		väri[PUNAINEN] = laskeVäri(red,rgb);
		väri[VIHREÄ] = laskeVäri(green,rgb);
		väri[SININEN] = laskeVäri(blue,rgb);
		väri[KELTAINEN] = laskeVäri(yellow, rgb);
		väri[VALKOINEN] = laskeVäri(white, rgb);
		
		int lähinVäri;
		/*
		if (väri[PUNAINEN] < väri[VIHREÄ]) {
			if(väri[PUNAINEN] < väri[SININEN]) {
				lähinVäri = PUNAINEN;
			}
			else {
				lähinVäri = SININEN;
			}
			
		}else if(väri[VIHREÄ] < väri[SININEN]){
			lähinVäri = VIHREÄ;
		}else {
			lähinVäri = SININEN;
		}
		*/
		
		 if(väri[PUNAINEN] < väri[VIHREÄ] && väri[PUNAINEN] < väri[SININEN] && väri[PUNAINEN] < väri[KELTAINEN] && väri[PUNAINEN] < väri[VALKOINEN]){
		 	lähinVäri = PUNAINEN;
		 	
		 }else if(väri[VIHREÄ] < väri[PUNAINEN] && väri[VIHREÄ] < väri[SININEN] && väri[VIHREÄ] < väri[KELTAINEN] && väri[VIHREÄ] < väri[VALKOINEN]){
		 	lähinVäri = VIHREÄ;
		 	
		 }else if(väri[SININEN] < väri[PUNAINEN] && väri[SININEN] < väri[VIHREÄ] && väri[SININEN] < väri[KELTAINEN] && väri[SININEN] < väri[VALKOINEN]){
		 	lähinVäri = SININEN;
		 	
		 }else if(väri[KELTAINEN] < väri[PUNAINEN] && väri[KELTAINEN] < väri[VIHREÄ] && väri[KELTAINEN] < väri[SININEN] && väri[KELTAINEN] < väri[VALKOINEN]){
		 	lähinVäri = KELTAINEN;
		 	
		 }else {
		 	lähinVäri = VALKOINEN;

		 } 
		
		return lähinVäri;
	}

	public double laskeVäri(int referenssiVäri[], int väri[]) {
		double tulos = 0;
		int calc[] = new int[3];
		for(int i=0;i<3;i++){
			calc[i] = referenssiVäri[i] - väri[i];
		}
		
		for(int i=0;i<3;i++){
			calc[i] *= calc[i];
		}
		
		for(int i=0;i<3;i++) {
			tulos += calc[i];
		}
		
		tulos = Math.sqrt(tulos);
		LCD.clear();
		LCD.drawString("" + tulos, 0, 6);
		
		return tulos;
	}

}

