package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class InventoryDAO {

	private SessionFactory istuntotehdas = null;
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	
	public InventoryDAO() {
		try {
			
		}catch(Exception e){
			System.out.println("Istuntotehtaan luonti epäonnistui");
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}
	}
	
	public boolean addItem(Inventory item) {
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(item);
			transaktio.commit();
		}catch(Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
		
		return true;
	}
	
	public Inventory[] readInventory() {
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		List<Inventory> inventory;
		
		try {
			transaktio = istunto.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Inventory> result = istunto.createQuery("from Inventory").getResultList();
			
			inventory = result;
			
		}catch(Exception e) {
			if(transaktio != null) {
				transaktio.rollback();
			}
			throw e;
		}finally {
			transaktio.commit();
			istunto.close();
		}
		
		if(inventory.isEmpty()) {
			return null;
		}
		
		return inventory.toArray(new Inventory[inventory.size()]);
	}
	
	public Inventory readInventory(int containerNum, int shelfNum) {
		Inventory[] items = readInventory();
		for(Inventory item : items) {
			if(item.getContainerNum() == containerNum && item.getShelfNum() == shelfNum) {
				return item;
			}
		}
		return null;
	}
	
	public boolean removeItem(int containerNum, int shelfNum) {
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		boolean onnistui = false;
		Inventory[] inventory = readInventory();
		
		try {
			transaktio = istunto.beginTransaction();
			
			for(Inventory item : inventory) {
				if(item.getContainerNum() == containerNum && item.getShelfNum() == shelfNum) {
					istunto.delete(item);
					onnistui = true;
				}
			}
			
			if(!onnistui) {
				System.out.println("Esinettä ei löytynyt");
			}
			
			transaktio.commit();
		}catch(Exception e) {
			if(transaktio != null) {
				transaktio.rollback();
				throw e;
			}
		}finally {
			istunto.close();
		}
		
		return onnistui;
	}
	
	public void terminateSessionFactory() {
		istuntotehdas.close();
	}
}
