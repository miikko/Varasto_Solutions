package application;

public interface GUI {

	public abstract void setConnected();
	public abstract void popExceptionAlert(String headerText, String contentText, Exception e);
	public abstract void setBufferValue(int value);
}
