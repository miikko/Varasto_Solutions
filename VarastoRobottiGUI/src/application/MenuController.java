package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lejos.robotics.navigation.Waypoint;

/**
 * This class connects the MenuView- and the MenuCommunicationModel-class.
 * It calls the Model's methods based on the information it gets from the View.
 * @author Miikka Oksanen
 *
 */
public class MenuController {

	private InventoryItemDAO dao;
	private MenuView view;
	private MenuCommunicationModel commModel;
	private final Waypoint[] SHELFCOORDINATES = new Waypoint[] { new Waypoint(32, -28,0), new Waypoint(63, -70,0) };
	private ObservableList<String> catalog;

	/**
	 * Constructor
	 * @param view
	 */
	public MenuController(MenuView view) {
		this.view = view;
		dao = new InventoryItemDAO();
		commModel = new MenuCommunicationModel();
		commModel.setDaemon(true);
		commModel.start();
	}

	/**
	 * Gets the chosen item's location from the database and sends it forward to the commModel.
	 * Finally removes the item from the database.
	 * @param itemName
	 * @param itemCount
	 */
	public void startDelivery(String itemName, int itemCount) {
		int temp = itemCount;
		while (temp > 0) {

			InventoryItem[] inventory = dao.readInventory();
			InventoryItem thisItem = null;
			for (int i = 0; i < inventory.length; i++) {

				if (inventory[i] != null && inventory[i].getName().equals(itemName)) {
					commModel.makeTransfer(SHELFCOORDINATES[inventory[i].getContainerNum()],
							inventory[i].getShelfNum());
					thisItem = inventory[i];
					break;
				}

			}

			if (thisItem != null) {
				dao.removeItem(thisItem.getContainerNum(), thisItem.getShelfNum());
			}
			view.deliveryStatusLbl.textProperty().bind(commModel.statusMessageProperty());
			temp--;
		}

		catalog = initializeCatalog();
		updateQuantity(itemName);
	}

	/**
	 * Creates the catalog-ObservableList based on database information.
	 * @return the catalog-ObservableList that is filled based on the database information.
	 */
	public ObservableList<String> initializeCatalog() {
		InventoryItem[] inventory = dao.readInventory();
		List<String> items = new ArrayList<String>();

		if (inventory != null) {

			for (int i = 0; i < inventory.length; i++) {

				if (inventory[i] != null && !items.contains(inventory[i].getName())) {
					items.add(inventory[i].getName());
				}
			}
		}
		catalog = FXCollections.observableArrayList(items);
		return catalog;
	}

	/**
	 * Updates the quantity-ListView based on the given parameter.
	 * This is done by checking the quantity from the database.
	 * @param itemName
	 */
	public void updateQuantity(String itemName) {
		InventoryItem[] inventory = dao.readInventory();
		int quantity = 0;

		if (inventory != null) {

			for (int i = 0; i < inventory.length; i++) {

				if (inventory[i] != null && inventory[i].getName().contains(itemName)) {
					quantity++;
				}
			}
			if (quantity > 0) {
				view.displayItemQuantity(quantity);
			} else {
				view.removeItemFromCatalog(itemName);
			}

		} else {
			view.flushQuantityList();
		}
	}

	/**
	 * Connects to the robot and updates the GUI to the main screen.
	 * Prints a dialog upon failure.
	 */
	public void connectRobot() {
		try {
			commModel.connect();
			view.setConnected();
		} catch (Exception e) {
			view.popExceptionAlert("Connection Failed", "Make sure that the robot is running", e);
		}
	}

	/**
	 * Closes the connection with the database.
	 */
	public void terminateSessionFactory() {
		dao.terminateSessionFactory();
	}
}
