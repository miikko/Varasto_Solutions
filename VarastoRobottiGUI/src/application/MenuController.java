package application;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import lejos.robotics.navigation.Waypoint;

public class MenuController {
	
	private InventoryItemDAO dao;
	private MenuView view;
	private MenuCommunicationModel commModel;
	private final Waypoint[] SHELFCOORDINATES = new Waypoint[] {new Waypoint(36,44)};
	
	public MenuController(MenuView view) {
		this.view = view;
		dao = new InventoryItemDAO();
		commModel = new MenuCommunicationModel();
		commModel.setDaemon(true);
		commModel.start();
	}
	
	public void startDelivery(String itemName) {
		InventoryItem[] inventory = dao.readInventory();
		
		for (int i = 0; i < inventory.length; i++) {
			
			if (inventory[i] != null && inventory[i].getName().equals(itemName)) {
				commModel.makeTransfer(SHELFCOORDINATES[inventory[i].getContainerNum()], inventory[i].getShelfNum());
			}
			
		}
		view.deliveryStatusLbl.textProperty().bind(commModel.statusMessageProperty());
	}
	
	public ObservableList<String> initializeCatalog() {
		ObservableList<String> catalog;
		InventoryItem[] inventory = dao.readInventory();
		List<String> items = new ArrayList<String>();
		
		for (int i = 0; i < inventory.length; i++) {
			
			if (inventory[i] != null && !items.contains(inventory[i].getName())) {
				items.add(inventory[i].getName());
			}
		}
		catalog = FXCollections.observableArrayList(items);
		return catalog;
	}
	
	public void updateQuantity(String itemName) {
		InventoryItem[] inventory = dao.readInventory();
		int quantity = 0;
		
		for (int i = 0; i < inventory.length; i++) {
			
			if (inventory[i] != null && inventory[i].getName().contains(itemName)) {
				quantity++;
			}
		}
		view.displayItemQuantity(quantity);
	}
	
	public void connectRobot() {
		try {
			commModel.connect();
			view.setConnected();
		} catch (Exception e) {
			view.popExceptionAlert("Connection Failed", "Make sure that the robot is running", e);
		}
	}
	
	public void terminateSessionFactory() {
		dao.terminateSessionFactory();
	}
}
