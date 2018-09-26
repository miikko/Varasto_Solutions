import java.util.ArrayList;

import lejos.robotics.navigation.Waypoint;

public class Orders {
	ArrayList<Waypoint> orders;
	
	public void addOrder(Waypoint order) {
		orders.add(order);
	}
	
	public Waypoint getNextOrder() {
		Waypoint temp = orders.get(orders.size());
		orders.remove(orders.size());
		return temp;
	}
}
