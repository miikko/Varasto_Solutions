package controller;

import application.GUI;
import javafx.scene.control.Alert.AlertType;
import model.ConnectionHandler;

public class MainController implements Controller {
	
	private GUI gui;
	private ConnectionHandler conHandler;
	
	public MainController(GUI gui) {
		this.gui = gui;
		conHandler = new ConnectionHandler();
		conHandler.start();
	}
	
	@Override
	public void connectRobot() {
		// TODO Auto-generated method stub
		try {
			conHandler.connect();
			gui.setConnected();
		} catch (Exception e){
			gui.popExceptionAlert("Connection Failed.", "Make sure robot is running.",e);
		}
	}

	@Override
	public void sendData(String command) {
		// TODO Auto-generated method stub
		conHandler.sendData(command);
	}

	@Override
	public void addPacket() {
		// TODO Auto-generated method stub
		
	}
	

}
