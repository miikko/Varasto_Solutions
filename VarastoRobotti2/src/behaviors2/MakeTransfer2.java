package behaviors2;

import actions2.ColorSensor2;
import actions2.Lift2;
import connection2.Connection2;
import connection2.Orders;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import navigation2.Navigation2;

public class MakeTransfer2 implements Behavior{

	private volatile boolean suppressed = false;
	private Navigation2 navigation2;
	private Waypoint homeWP = new Waypoint(0,0);
	private Lift2 lift2;
	private Connection2 con2;
	private ColorSensor2 liftSensor;
	
	public MakeTransfer2(Navigation2 navigation2, Connection2 con2) {
		this.con2 = con2;
		this.navigation2 = navigation2;
		lift2 = new Lift2();	
		lift2.liftUp(3,false);
		liftSensor = new ColorSensor2(new EV3ColorSensor(SensorPort.S2));
		lift2.liftUp(0,false);
		
	}
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (!Connection2.noOrders());
	}

	@Override
	public void action() {
		
		suppressed = false;
		while(!Connection2.noOrders()) {
			
			// saa värillisen paketin kauhaansa, checkkaa värin ja toteuttaa saadun waypointin
			Waypoint temp = Connection2.getNextOrder();
			//Delay.msDelay(1000);
			con2.sendColor(liftSensor.getVäri());
			lift2.liftDown();
			navigation2.followLine();
			System.out.println(Connection2.orders.get(temp).get(Connection2.orders.get(temp).size() - 1));
			lift2.liftUp(Connection2.orders.get(temp).get(Connection2.orders.get(temp).size() - 1),false);
			Delay.msDelay(1000);
			navigation2.driveStraight(true);
			lift2.liftUp(1,true);
			navigation2.driveStraight(false);
			
			
			con2.sendUpdate("Packet transferred.");
			if(!Connection2.orders.get(temp).isEmpty()) {
				Connection2.orders.get(temp).remove(Connection2.orders.get(temp).size() - 1);
			}else {
				Connection2.orders.remove(temp);
			}
			
			lift2.liftDown();
		}
		//con2.sendUpdate("Returning home.");
		navigation2.turnAround();
		navigation2.followLine();
		navigation2.turnAround();
		lift2.liftUp(3, false);
		con2.sendUpdate("Finished");
		Thread.yield();
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
