package application;

public class InventoryItem {

	private String name;
	private int shelfNum;
	
	public InventoryItem(String name, int shelfNum) {
		this.setName(name);
		this.setShelfNum(shelfNum);
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
}
