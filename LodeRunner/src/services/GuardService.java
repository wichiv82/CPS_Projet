package services;

public interface GuardService extends CharacterService{
	
	public int getId();
	
	/**
	 * post : getId() == id
	 * post : getTarget() == target
	 * post : posInit = (x,y)
	 * post : getItem() == null
	 *
	 */
	public void init(ScreenService s, int x, int y, int id, CharacterService target);
	
	public Move getBehaviour();
	
	public CharacterService getTarget();
	
	public int getTimeInHole();
	
	public EngineService getEngine();
	
	/**
	 * post : getEngine() == engine
	 */
	public void setEngine(EngineService engine);
	
	/**
	 * pre : getEnvi().cellNature(getHeight(), getWidth()) = Cell.HOL
	 */
	public void climbLeft();
	
	/**
	 * pre : getEnvi().cellNature(getHeight(), getWidth()) = Cell.HOL
	 */
	public void climbRight();
	
	public void step();

	/**
	 * pre : getEnvi().cellNature(getWidth(), getHeight()) == Cell.PLT
	 * 
	 * post : getEngine().getEnvi().cellNature(getWidth(), getHeight()). getCharacter() == null
	 * post : getEnvi().cellNature(getWidth(), getHeight()). getCharacter() == null
	 * post : getWidth() == posInit.x
	 * post : getHeight() == posInit.y
	 * post : getEngine().getEnvi().cellContent(posInit.x, posInit.y).getCharacter() == this
	 * post : getEnvi().cellContent(posInit.x, posInit.y).getCharacter() == this
	 */
	public void respawn();
	
	public boolean hasItem();
	
	/**
	 * post : getItem() == null
	 */
	public void removeItem();
	
	/**
	 * post : getItem() == item
	 */
	public void giveItem(ItemService item);
}
