package controller;
/**
 * Interface for Controller.
 * @author Eero
 *
 */
public interface Controller {
	/**
	 * Checks robot connection.
	 */
	public abstract void connectRobot();
	/**
	 * Adds packet into buffer.
	 */
	public abstract void addPacket();
	/**
	 * Updates buffer label value.
	 * @param integer Buffer size.
	 */
	public abstract void updateLabel(int integer);
}
