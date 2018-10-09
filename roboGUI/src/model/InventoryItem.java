package model;

import javax.persistence.*;

/**
 * ORM class for inventory items.
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
	 * Constructor that sets item name, container number and shelf number.
	 * @param name Item name.
	 * @param containerNum Container number.
	 * @param shelfNum Shelf number.
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
	 * Sets item name. Name is color of package.
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
