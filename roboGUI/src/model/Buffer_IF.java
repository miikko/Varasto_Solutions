package model;

/**
 * Interface for buffer.
 * @author Eero
 *
 */
public interface Buffer_IF {
	/**
	 * Gets buffer size.
	 * @return
	 */
	public abstract int getBuffer();
	/**
	 * Decreases buffer size by one and updates buffer label.
	 */
	public abstract void remove();
	/**
	 * Increases buffer size bu one and updates buffer label.
	 */
	public abstract void addBuffer();
}
