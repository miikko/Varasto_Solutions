package controller;

import java.io.IOException;

import model.*;
import application.GUI;
/**
 * 
 * @author Eero
 *
 */
public class MainController implements Controller {
	
	private GUI gui;
	private ConnectionHandler conHandler;
	private Buffer_IF buffer;
	
	/**
	 * MainController constructor
	 * @param gui
	 */
	public MainController(GUI gui) {
		
		this.gui = gui;
		buffer = new Buffer(this);
		conHandler = new ConnectionHandler(buffer);
		conHandler.start();
		
	}
	
	/**
	 *Method for checking robot connection. 
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
	 *Method for adding packet into buffer. 
	 */
	@Override
	public void addPacket() {
		buffer.addBuffer();
	}
	
	/**
	 *Method for updating buffer size.
	 */
	@Override
	public void updateLabel(int inte) {
		// TODO Auto-generated method stub
		gui.setBufferValue(inte);
	}

	
	

}
