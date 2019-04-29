package services;

public interface GuardService extends CharacterService{
	
	public int getId();
	
	public void init(ScreenService s, int x, int y, int id, CharacterService target);
	
	public Move getBehaviour();
	
	public CharacterService getTarget();
	
	public int getTimeInHole();
	
	/**
	 * pre : getEnvi().cellNature(getHeight(), getWidth()) = Cell.HOL
	 */
	public void climbLeft();
	
	public void step();

	public void respawn();
	
	public boolean hasItem();
	
	public void removeItem();
	
	public void giveItem(ItemService item);
}
