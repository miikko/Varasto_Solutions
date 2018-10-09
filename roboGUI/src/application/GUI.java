package application;
/**
 * Interface for Graphical User Interface
 * @author Eero
 *
 */
public interface GUI {

	/**
	 * Sets view to connected.
	 */
	public abstract void setConnected();
	/**
	 * Creates new Alert window for exception.
	 * @param headerText Header text.
	 * @param contentText Content text.
	 * @param e Exception.
	 */
	public abstract void popExceptionAlert(String headerText, String contentText, Exception e);
	/**
	 * Sets new value for buffer label.
	 * @param value Buffer size.
	 */
	public abstract void setBufferValue(int value);
}
