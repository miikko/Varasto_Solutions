package connection2;

import java.util.ArrayList;
import lejos.robotics.navigation.Waypoint;

public class Orders {

private ArrayList<Waypoint> orders2;
	
	public Orders() {
		orders2 = new ArrayList<>();
	}
	
	public void addOrder(Waypoint order) {
		orders2.add(order);
	}
	
	public Waypoint getNextOrder() {
		if(!orders2.isEmpty()) {
			Waypoint temp = orders2.get(0);
			orders2.remove(0);
			return temp;
		}

		return null;
		
	}
	
	public boolean checkIfEmpty() {
		return orders2.isEmpty();
	}
}
