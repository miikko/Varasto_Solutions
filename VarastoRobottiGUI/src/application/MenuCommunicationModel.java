package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Waypoint;

public class MenuCommunicationModel extends Thread {

	private Robot[] robots;
	private boolean quit = false;
	private final String HOST = "10.0.1.1";
	private final int PORT = 1111;
	private boolean transferInProgress = false;
	private Socket s;
	private final StringProperty transferStatusMessage = new SimpleStringProperty("No transfers in progress.");

	public MenuCommunicationModel() {

		/*
		try {
			s = new Socket(HOST, PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void connect() throws UnknownHostException, IOException{
			s = new Socket(HOST, PORT);	
	}
	
	public StringProperty statusMessageProperty() {
		return transferStatusMessage;
	}
	
	public final String getStatusMessage() {
		return transferStatusMessage.get();
	}
	
	public final void setStatusMessage(String message) {
		transferStatusMessage.set(message);
	}

	public void run() {

		try (DataInputStream in = new DataInputStream(s.getInputStream())) {

			while (!quit) {

				if (transferInProgress) {
					
					transferStatusMessage.set(in.readUTF());

					if (transferStatusMessage.get().equals("Finished")) {
						transferInProgress = false;
					}
				}
			}
			
			s.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			
		}

	}

	private void connect(Robot robot) {

	}

	public void makeTransfer(Waypoint waypoint, int lockerNumber) {
		try (DataOutputStream out = new DataOutputStream(s.getOutputStream())) {

			waypoint.dumpObject(out);
			out.writeInt(lockerNumber);
			out.flush();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transferInProgress = true;
	}

	public void setLineMap(LineMap lineMap) {

	}

	public void terminate() {
		quit = true;
	}
}
