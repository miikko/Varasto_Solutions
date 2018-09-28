import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean workMode, decision = false;
		// workMode: true = lastaaja, false = hakija

		LCD.drawString("Lastaaja vai hakija?", 0, 1);
		LCD.drawString("(Vasen)      (oikea)", 0, 2);
		while(!decision){
			if(Button.LEFT.isDown()) {
				workMode = true;
				decision = true;
			}
			else if(Button.RIGHT.isDown()) {
				workMode = false;
				decision = true;
			}
		}
		LCD.clear();
		LCD.drawString("Aloitetaan...", 0, 3);
		Delay.msDelay(2000);
		LCD.clear();

	}

}
