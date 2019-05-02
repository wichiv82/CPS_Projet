package services;

public interface ItemService {
	
	public int getId();
	
	public ItemType getNature();
	
	public int getHeight();
	
	public int getColumn();
	
	/**
	 * pre : 0 <= id 
	 * pre : 0 <= x 
	 * pre : 0 <= y
	 * post : getId() == id
	 * post : getNature() == nature
	 * post : getColumn() == x
	 * post : getHeight() == y
	 */
	public void init(int id, ItemType nature, int x, int y);
	
	/**
	 * pre : 0 <= x
	 * post : getColumn() == x
	 */
	public void setColumn(int x);
	
	/**
	 * pre : 0 <= y
	 * post : getHeight() == y
	 */
	public void setHeight(int y);
}
