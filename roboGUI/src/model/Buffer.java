package model;


import controller.Controller;
import javafx.application.Platform;

public class Buffer implements Buffer_IF{

	private int buf;
	private Controller controller;
	
	public Buffer(Controller controller) {
		buf= 0;
		this.controller = controller;
	}

	public int getBuffer() {
		return buf;
	}

	public synchronized void  addBuffer() {
		buf++;
		updateLabel();
	}
	
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
