package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class InventoryItemDAO {

	private SessionFactory istuntotehdas = null;
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

	public InventoryItemDAO() {

		try {

			istuntotehdas = new MetadataSources(registry).buildMetadata().buildSessionFactory();

		} catch (Exception e) {
			System.out.println("Istuntotehtaan luonti epäonnistui.");
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}

	}

	public boolean addItem(InventoryItem item) {

		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;

		try {

			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(item);
			transaktio.commit();

		} catch (Exception e) {

			if (transaktio != null)
				transaktio.rollback();
			throw e;

		} finally {
			istunto.close();
		}

		return true;
	}

	public InventoryItem readInventoryItem(int containerNum, int shelfNum) {
		InventoryItem[] items = readInventory();
		for (InventoryItem item : items) {
			if (item.getContainerNum() == containerNum && item.getShelfNum() == shelfNum) {
				return item;
			}
		}
		return null;
	}

	public InventoryItem[] readInventory() {

		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		List<InventoryItem> inventory;

		try {
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<InventoryItem> result = istunto.createQuery("from InventoryItem").getResultList();

			inventory = result;

		} catch (Exception e) {

			if (transaktio != null) {
				transaktio.rollback();
			}
			throw e;

		} finally {
			transaktio.commit();
			istunto.close();
		}

		if (inventory.isEmpty()) {
			return null;
		}

		return inventory.toArray(new InventoryItem[inventory.size()]);
	}

	public boolean removeItem(int containerNum, int shelfNum) {

		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		boolean onnistui = false;
		InventoryItem[] inventory = readInventory();

		try {

			transaktio = istunto.beginTransaction();

			for (InventoryItem item : inventory) {
				if (item.getContainerNum() == containerNum && item.getShelfNum() == shelfNum) {
					istunto.delete(item);
					onnistui = true;
				}
			}

			if (!onnistui) {
				System.out.println("Esinettä ei löytynyt");
			}

			transaktio.commit();

		} catch (Exception e) {

			if (transaktio != null)
				transaktio.rollback();
			throw e;

		} finally {
			istunto.close();
		}

		return onnistui;
	}

	public int[] getNextEmptySpot() {
		int[] containerShelfNums = new int[2];
		InventoryItem[] inventory = readInventory();
		
		if (inventory == null) {
			
			containerShelfNums[0] = 0;
			containerShelfNums[1] = 1;
			
		} else {
			
			String[][] containerShelfModel = new String[2][3];
			
			for (int i = 0; i < inventory.length; i++) {
				containerShelfModel[inventory[i].getContainerNum()][inventory[i].getShelfNum()] = inventory[i].getName();
			}
			
			outerloop:
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					if (containerShelfModel[i][j] == null) {
						containerShelfNums[0] = i;
						containerShelfNums[1] = j;
						break outerloop;
					}
				}
			}
		}
		return containerShelfNums;
	}

	public void terminateSessionFactory() {
		istuntotehdas.close();
	}
}
