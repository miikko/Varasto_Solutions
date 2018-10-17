package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Waypoint;

/**
 * This thread receives and sends information to the robot.
 * 
 * @author Miikka Oksanen & Eero Tuure
 *
 */
public class MenuCommunicationModel extends Thread {

	private boolean quit = false;
	private final String HOST = "10.0.1.1";
	private final int PORT = 1111;
	public static boolean transferInProgress = false;
	private Socket s;
	private final StringProperty transferStatusMessage = new SimpleStringProperty("No transfers in progress.");
	DataOutputStream out;

	/**
	 * Constructor
	 */
	public MenuCommunicationModel() {

		try {
			connect();
			out = new DataOutputStream(s.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Creates a new socket that is connected to the robot. 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException {
		s = new Socket(HOST, PORT);
	}

	/**
	 * Getter
	 * @return the Property value that wraps the transferStatusMessage-String.
	 */
	public StringProperty statusMessageProperty() {
		return transferStatusMessage;
	}

	/**
	 * Getter
	 * @return the String value of transferStatusMessage.
	 */
	public final String getStatusMessage() {
		return transferStatusMessage.get();
	}

	/**
	 * Sets the parameter as the String value of transferStatusMessage.
	 * @param message
	 */
	public final void setStatusMessage(String message) {
		transferStatusMessage.set(message);
	}

	@Override
	public void run() {

		try (DataInputStream in = new DataInputStream(s.getInputStream())) {

			while (!quit) {

				if (transferInProgress) {

					String statusMessage = in.readUTF();

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							setStatusMessage(statusMessage);
						}
					});

					if (statusMessage.equals("Finished")) {
						transferInProgress = false;
					}
				}
				Thread.sleep(50);
			}

			out.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}

	}

	/**
	 * Sends instructions to the robot based on the given parameters.
	 * Waypoint determines which shelf to go to.
	 * LockerNumber determines the height in that shelf.
	 * @param waypoint
	 * @param lockerNumber
	 */
	public void makeTransfer(Waypoint waypoint, int lockerNumber) {

		transferInProgress = true;
		try {

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

	}

	/**
	 * Terminates this thread.
	 */
	public void terminate() {
		quit = true;
	}
}
