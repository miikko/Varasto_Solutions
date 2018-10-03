package controller;

public interface Controller {
	public abstract void connectRobot();
	public abstract void sendData(String string);
	public abstract void addPacket();
}
