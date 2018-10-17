package interfaces;

/**
 * Interface for the MenuView-class.
 * @author Miikka Oksanen & Eero Tuure
 *
 */
public interface MenuView_IF {

	/**
	 * Puts the parameter as the current update feed message. 
	 * @param message
	 */
	public void updateFeed(String message);
	
	/**
	 * Updates the quantity-ListView by giving numbers from one to 'quantity' to the ObservableListView that is linked to the ListView.
	 * @param quantity
	 */
	public void displayItemQuantity(int quantity);
	
	/**
	 * Puts the parameter as the current delivery status message.
	 * @param message
	 */
	public void updateDeliveryStatus(String message);
	
	/**
	 * Creates an alert pop-up box with the given parameters. The box contains information about the exception.
	 * @param headerText
	 * @param contentText
	 * @param e
	 */
	public abstract void popExceptionAlert(String headerText, String contentText, Exception e);
	
	/**
	 * Updates the GUI by displaying the main screen.
	 */
	public abstract void setConnected();
}
