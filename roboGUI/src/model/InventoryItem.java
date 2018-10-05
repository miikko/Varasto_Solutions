package model;

import javax.persistence.*;


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
	
	public InventoryItem(String name, int containerNum, int shelfNum) {
		super();
		this.setName(name);
		this.setShelfNum(shelfNum);
		this.containerNum = containerNum;
	}
	
	public InventoryItem() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String color) {
		this.name = color;
	}

	public int getShelfNum() {
		return shelfNum;
	}

	public void setShelfNum(int shelfNum) {
		this.shelfNum = shelfNum;
	}
	
	public int getContainerNum() {
		return containerNum;
	}
	
	public void setWP(int containerNum) {
		this.containerNum = containerNum;
	}
}
