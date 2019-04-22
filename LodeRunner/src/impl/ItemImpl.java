package impl;

import services.ItemService;
import services.ItemType;

public class ItemImpl implements ItemService{
	private int id;
	private ItemType nature;
	private int height;
	private int width;
	
	public void init(int id, ItemType nature, int x, int y) {
		this.id = id;
		this.nature = nature;
		this.width = x;
		this.height = y;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public ItemType getNature() {
		// TODO Auto-generated method stub
		return nature;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return width;
	}
	
	public void setHeight(int y) {
		this.height = y;
	}
	
	public void setColumn(int x) {
		this.width = x;
	}

}
