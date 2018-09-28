package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuController {
	
	private InventoryDatabase invDB;
	private MenuView view;
	private MenuCommunicationModel commModel;
	
	public MenuController(MenuView view) {
		this.view = view;
		invDB = new InventoryDatabase();
		commModel = new MenuCommunicationModel();
	}
	
	public void startDelivery() {
		
	}
	
	public ObservableList<String> initializeCatalog() {
		ObservableList<String> catalog;
		InventoryItem[][] inventory = invDB.getInventory();
		List<String> items = new ArrayList<String>();
		
		for (int i = 0; i < inventory.length; i++) {
			
			for (int j = 0; j < inventory[i].length; j++) {
				
				if (inventory[i][j] != null && !items.contains(inventory[i][j].getName())) {
					items.add(inventory[i][j].getName());
				}
			}
		}
		catalog = FXCollections.observableArrayList(items);
		return catalog;
	}
	
	public void updateQuantity(String itemName) {
		InventoryItem[][] inventory = invDB.getInventory();
		int quantity = 0;
		
		for (int i = 0; i < inventory.length; i++) {
			for (int j = 0; j < inventory[i].length; j++) {
				
				if (inventory[i][j] != null && inventory[i][j].getName().contains(itemName)) {
					quantity++;
				}
			}
		}
		view.displayItemQuantity(quantity);
	}
}
