package model;

import javax.persistence.*;

/**
 * 
 * @author Eero
 * 
 */

@Entity
@Table(name="InventoryItem")
public class InventoryItem {

	@Column(name="name")
	private String name;
	
	@Column(name="shelfNum")
	private int shelfNum;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id")
	private int Id;
	
	@Column(name="containerNum")
	private int containerNum;
	
	/**
	 * 
	 * @param name
	 * @param containerNum
	 * @param shelfNum
	 */
	public InventoryItem(String name, int containerNum, int shelfNum) {
		super();
		this.setName(name);
		this.setShelfNum(shelfNum);
		this.containerNum = containerNum;
	}
	
	/**
	 * Parameterless constructor.
	 */
	public InventoryItem() {
		super();
	}

	/**
	 * Gets item name. In this case name is color of package.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets item name
	 * @param color
	 */
	public void setName(String color) {
		this.name = color;
	}

	/**
	 * Gets shelf number of item.
	 * @return
	 */
	public int getShelfNum() {
		return shelfNum;
	}

	/**
	 * Sets shelf number of item.
	 * @param shelfNum
	 */
	public void setShelfNum(int shelfNum) {
		this.shelfNum = shelfNum;
	}
	
	/**
	 * Gets container number of item.
	 * @return
	 */
	public int getContainerNum() {
		return containerNum;
	}
	
	/**
	 * Sets container number of item.
	 * @param containerNum
	 */
	public void setWP(int containerNum) {
		this.containerNum = containerNum;
	}
}
