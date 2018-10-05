package controller;

import application.GUI;
import model.Buffer;
import model.Buffer_IF;
import model.ConnectionHandler;

public class MainController implements Controller {
	
	private GUI gui;
	private ConnectionHandler conHandler;
	private Buffer_IF buffer;
	
	public MainController(GUI gui) {
		this.gui = gui;
		buffer = new Buffer(this);
		conHandler = new ConnectionHandler(buffer);
		conHandler.start();
		
	}
	
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

	
	@Override
	public void addPacket() {
			buffer.addBuffer();
	}
	

	@Override
	public void updateLabel(int inte) {
		// TODO Auto-generated method stub
		gui.setBufferValue(inte);
	}

	
	

}
