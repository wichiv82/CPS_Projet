package services;

public interface ItemService {
	
	public void init(int id, ItemType nature, int x, int y);
	
	public int getId();
	
	public ItemType getNature();
	
	public int getHeight();
	
	public int getColumn();
	
	public void setColumn(int x);
	
	public void setHeight(int y);
}
