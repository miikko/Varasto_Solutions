package application;

import javafx.scene.control.Alert;

public interface GUI {

	public abstract void setConnected();
	public abstract void popExceptionAlert(String headerText, String contentText, Exception e);
}
