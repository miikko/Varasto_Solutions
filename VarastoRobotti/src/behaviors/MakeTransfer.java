package behaviors;

import actions.ColorSensor;
import actions.Lift;
import connection.Connection;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;
import navigation.Navigation;
/**
 * This class contains the behavior for retrieving an order and taking it to the customer.
 * @author 
 *
 */
public class MakeTransfer implements Behavior {
	private volatile boolean suppressed = false;
	private Navigation navigation;
	private Lift lift = new Lift();
	private Connection connection;
	private final Waypoint customerWP = new Waypoint(10,-50);
	private final Waypoint defaultWP = new Waypoint(0,0,0);
	private ColorSensor liftSensor;
	
	/**
	 * A constructor with Navigation and Connection parameters.
	 */
	public MakeTransfer(Navigation navigation, Connection connection, ColorSensor liftSensor) {
		this.navigation = navigation;
		this.connection = connection;
		this.liftSensor = liftSensor;
	}
	
	/**
	 * Starts the behavior when there are orders.
	 */
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return !Connection.noOrders();
		
	}

	/**
	 * Executes the order retrieval actions
	 */
	@Override
	public void action() {
		// TODO Auto-generated method stub
		suppressed = false;
		while(!Connection.noOrders()) {
			Waypoint temp = Connection.getNextOrder();
			
			connection.sendUpdate("Moving to pick up the box.");
			navigation.followLine();
		
			lift.liftUp(Connection.orders.get(temp).get(Connection.orders.get(temp).size() - 1), false);
			
			// move forward
			navigation.driveStraight(true);
			
			// lift order up
			lift.liftUp(0, true);
			
			// back out of shelf
			navigation.driveStraight(false);
			connection.sendUpdate("Box picked, returning to delivery counter.");
			
			// remove current order from orders
			if(!Connection.orders.get(temp).isEmpty()) {
				Connection.orders.get(temp).remove(Connection.orders.get(temp).size() - 1);
			}else {
				Connection.orders.remove(temp);
			}
			
			// take order to removal point
			navigation.turnAround();
			liftSensor.setColorID();
			navigation.followLine();
			while (liftSensor.itemInCrane()) {
				
			}
			navigation.turnAround();
			
			// reset lift-height
			lift.liftDown();
		}
		
		connection.sendUpdate("Finished");
		Thread.yield();
			

	}
	/**
	 * Suppresses the behavior.
	 */
	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;

	}

}
