package behaviors2;

import actions2.ColorSensor2;
import actions2.Lift2;
import connection2.Connection2;
import connection2.Orders;
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
	private ColorSensor2 cs;
	
	public MakeTransfer2(Navigation2 navigation2, Connection2 con2) {
		this.con2 = con2;
		this.navigation2 = navigation2;
		lift2 = new Lift2();
		lift2.liftUpShort(140);
		cs = new ColorSensor2();
		cs.kalibroi();
		
		
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
			//lift2.liftUpShort(140);
			Delay.msDelay(1000);
			con2.sendColor(cs.getVäri());
			//con2.sendUpdate("Color: " + cs.getVäri() + "detected.");
			lift2.liftDown();
			Delay.msDelay(3000);
			//con2.sendUpdate("Transferring packet to storage.");
			navigation2.executePath(temp, true);
			
			/*
			boolean leftShelf;
			if(temp.x < 100) {
				leftShelf = true;
			}else {
				leftShelf = false;
			}
			
			navigation2.faceShelf(leftShelf);
			*/
			
			lift2.liftUp(Connection2.orders.get(temp).get(Connection2.orders.get(temp).size() - 1));
			Delay.msDelay(1000);
			navigation2.driveForward(5);
			lift2.liftUpShort(30);
			navigation2.driveBackward(5);
			
			
			con2.sendUpdate("Packet transferred.");
			if(!Connection2.orders.get(temp).isEmpty()) {
				Connection2.orders.get(temp).remove(Connection2.orders.get(temp).size() - 1);
			}else {
				Connection2.orders.remove(temp);
			}
			
			lift2.liftDown();
		}
		//con2.sendUpdate("Returning home.");
		navigation2.executePath(homeWP, false);
		con2.sendUpdate("Finished");
		Thread.yield();
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
