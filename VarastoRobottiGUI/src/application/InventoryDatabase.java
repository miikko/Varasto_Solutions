package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InventoryDatabase {

	private final String PATH = "db/database.txt";
	private InventoryItem[][] inventory;

	public InventoryDatabase() {
		inventory = new InventoryItem[6][4];
		// Read inventory from file
		try (FileReader fileReader = new FileReader(PATH); BufferedReader buffReader = new BufferedReader(fileReader)) {

			String thisLine = buffReader.readLine();
			int lineCounter = 0;

			// assuming that shelf count is 6
			while (lineCounter < 2) {

				String[] thisLineSplit = thisLine.split("\t");
				System.out.println("LineCounter: " + lineCounter + ", length: " + thisLineSplit.length);
				for (int i = 1; i < thisLineSplit.length; i++) {
					inventory[lineCounter][i] = new InventoryItem(thisLineSplit[i], lineCounter);
				}

				thisLine = buffReader.readLine();
				lineCounter++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Database file was not found.");
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void addItem(InventoryItem item, int shelfNum) {
		for (int i = 0; i < inventory[shelfNum].length; i++) {
			if (inventory[shelfNum][i] == null) {
				inventory[shelfNum][i] = item;
				break;
			}
		}
		updateDB();
	}

	public void removeItem(InventoryItem item, int shelfNum) {
		for (int i = 0; i < inventory[shelfNum].length; i++) {
			if (inventory[shelfNum][i].getName() == item.getName()) {
				inventory[shelfNum][i] = null;
				break;
			}
		}
		updateDB();
	}

	public InventoryItem[][] getInventory() {
		return inventory;
	}

	private void updateDB() {
		
		try (FileWriter fileWriter = new FileWriter(PATH); BufferedWriter buffWriter = new BufferedWriter(fileWriter)) {
			
			for (int i = 0; i < inventory.length; i++) {
				
				String thisShelf = i + ".Shelf";
				
				for (int j = 0; j < inventory[i].length; j++) {
					
					if (inventory[i][j] != null) {
						thisShelf += "\t" + inventory[i][j].getName();
					}
				}
				
				buffWriter.write(thisShelf);
				buffWriter.newLine();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Database file was not found.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
