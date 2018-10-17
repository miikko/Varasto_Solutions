package application;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Data Access Object for InventoryItem-objects.
 * 
 * @author Miikka Oksanen
 *
 */
public class InventoryItemDAO {

	private SessionFactory istuntotehdas = null;
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

	/**
	 * Constructor. 
	 * Creates a SessionFactory for Database access.
	 */
	public InventoryItemDAO() {

		try {

			istuntotehdas = new MetadataSources(registry).buildMetadata().buildSessionFactory();

		} catch (Exception e) {
			System.out.println("Istuntotehtaan luonti epäonnistui.");
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}

	}

	/**
	 * Adds the specified item to the database.
	 * @param item 
	 */
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

	/**
	 * Gets a single InventoryItem from the database based on the given parameters.
	 * @param containerNum
	 * @param shelfNum
	 * @return the specified InventoryItem if such an item exists in the database. Else returns null.
	 */
	public InventoryItem readInventoryItem(int containerNum, int shelfNum) {
		InventoryItem[] items = readInventory();
		for (InventoryItem item : items) {
			if (item.getContainerNum() == containerNum && item.getShelfNum() == shelfNum) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Gets all the InventoryItems in the database.
	 * @return an array of InventoryItems if the database has any. Otherwise returns null.
	 */
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

	/**
	 * Removes an item from the database based on the given parameters.
	 * @param containerNum
	 * @param shelfNum
	 * @return true, if the specified InventoryItem was found in the database. Else returns false.
	 */
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

	/**
	 * Closes the database connection.
	 */
	public void terminateSessionFactory() {
		istuntotehdas.close();
	}
}
