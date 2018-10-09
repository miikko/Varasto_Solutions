package model;


import controller.Controller;
import javafx.application.Platform;

/**
 * This class is for handling packet buffer.
 * @author Eero
 */
public class Buffer implements Buffer_IF{

	private int buf;
	private Controller controller;
	
	/**
	 * Creates new buffer with buffer value of 0.
	 * @param controller
	 */
	public Buffer(Controller controller) {
		buf= 0;
		this.controller = controller;
	}

	/**
	 * Gets buffer size.
	 */
	public int getBuffer() {
		return buf;
	}

	/**
	 * Increases buffer by one and updates buffer label.
	 */
	public synchronized void  addBuffer() {
		buf++;
		updateLabel();
	}
	/**
	 * Decreases buffer by one and updates buffer label.
	 */
	public synchronized void remove() {
		buf--;
		updateLabel();
	}
	
	private void updateLabel() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				controller.updateLabel(buf);
			}
		});
	}
	
}
