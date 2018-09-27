package connection;
import java.util.ArrayList;

import lejos.robotics.navigation.Waypoint;

public class Orders {
	private ArrayList<Waypoint> orders;
	
	public Orders() {
		orders = new ArrayList<>();
	}
	
	public void addOrder(Waypoint order) {
		orders.add(order);
	}
	
	public Waypoint getNextOrder() {
		if(!orders.isEmpty()) {
			Waypoint temp = orders.get(0);
			orders.remove(0);
			return temp;
		}

		return null;
		
	}
	
	public boolean checkIfEmpty() {
		return orders.isEmpty();
	}
}
