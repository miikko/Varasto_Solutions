package controller;

import java.io.IOException;

import model.*;
import application.GUI;
/**
 * This class purpose is control model and view.
 * @author Eero
 *
 */
public class MainController implements Controller {
	
	private GUI gui;
	private ConnectionHandler conHandler;
	private Buffer_IF buffer;
	
	/**
	 * @param gui Application graphical user interface.
	 */
	public MainController(GUI gui) {
		
		this.gui = gui;
		buffer = new Buffer(this);
		conHandler = new ConnectionHandler(buffer);
		conHandler.start();
		
	}
	
	/**
	 *Checks robot connection and sets GUI view to connected.
	 */
	@Override
	public void connectRobot() {
		// TODO Auto-generated method stub
		try {
			//conHandler.connect();
			gui.setConnected();
		} catch (Exception e){
			gui.popExceptionAlert("Connection Failed.", "Make sure robot is running.",e);
		}
	}

	/**
	 *Adds packet into buffer.
	 */
	@Override
	public void addPacket() {
		buffer.addBuffer();
	}
	
	/**
	 *Updates buffer value label.
	 */
	@Override
	public void updateLabel(int inte) {
		// TODO Auto-generated method stub
		gui.setBufferValue(inte);
	}

	
	

}
