package interfaces;

public interface MenuView_IF {

	public void updateFeed(String message);
	
	public void displayItemQuantity(int quantity);
	
	public void updateDeliveryStatus(String message);
	public abstract void popExceptionAlert(String headerText, String contentText, Exception e);
	public abstract void setConnected();
}
