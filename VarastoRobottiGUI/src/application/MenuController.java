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
					System.out.println(inventory[i][j].getName() + " added");
				}
			}
		}
		catalog = FXCollections.observableArrayList(items);
		return catalog;
	}
}
