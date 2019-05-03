package services;

public interface GuardService extends CharacterService{
	
	public int getId();
	
	/**
	 * post : getId() == id
	 * post : getTarget() == target
	 * post : posInit == (x,y)
	 * post : getItem() == null
	 */
	public void init(ScreenService s, int x, int y, int id, CharacterService target);
	
	/**
	 * post : if getEngine().getEnvi().cellNature(getWidth(), getHeight()) in {EMP, HDR} then
	 * 			if getWitdth() < getTarget().getWidth() then getBehaviour() == RIGHT
	 * 			else if getWitdth() > getTarget().getWidth() then getBehaviour() == LEFT
	 * 			else getBahaviour() == NEUTRAL
	 * 		  else if getEngine().getEnvi().cellNature(getWidth(), getHeight()) == LAD then
	 * 			if getHeight() < getTarget().getHeight() then getBeahviour() == UP
	 * 			else if getHeight() > getTarget().getHeight() then getBeahviour() == DOWN
	 * 			else if getWitdth() < getTarget().getWidth() then getBehaviour() == RIGHT
	 * 			else if getWitdth() > getTarget().getWidth() then getBehaviour() == LEFT
	 * 			else getBehaviour() == NEUTRAL
	 * 		  else 
	 * 			getBehaviour() == NEUTRAL
	 */
	public Move getBehaviour();
	
	public CharacterService getTarget();
	
	public int getTimeInHole();
	
	public EngineService getEngine();
	
	/**
	 * post : getEngine() == engine
	 */
	public void setEngine(EngineService engine);
	
	/**
	 * pre : getEnvi().cellNature(getWidth(), getHeight()) = Cell.HOL
	 * pre : getEnvi().cellContent(getWidth(), getHeight() +1).getCharacter() == null
	 * pre : getWitdh() > 0 && getHeight() < getEnvi().getHeight()-1
	 * pre : getEnvi().cellNature(getWidth()-1, getHeight()+1) in {EMP, LAD, HDR}
	 * 
	 * post : getWitdh() == getWitdh()@pre -1
	 * post : getHeight() == getHeight()@pre +1
	 * post : getEngine().getEnvi().cellContent(getWitdh()@pre, getHeight()@pre).getCharacter() == null
	 * post ! getEnvi().cellContent(getWitdh()@pre, getHeight()@pre).getCharacter() == null
	 * post : getEngine().getEnvi().cellContent(getWitdh(), getHeight()).getCharacter() == this
	 * post : getEnvi().cellContent(getWitdh(), getHeight()).getCharacter() == this
	 */
	public void climbLeft();
	
	/**
	 * pre : getEnvi().cellNature(getWidth(), getHeight()) = Cell.HOL
	 * pre : getEnvi().cellContent(getWidth(), getHeight() +1).getCharacter() == null
	 * pre : getWitdh() < getEnvi().getWidth()-1 && getHeight() < getEnvi().getHeight()-1
	 * pre : getEnvi().cellNature(getWidth()-1, getHeight()+1) in {EMP, LAD, HDR}
	 * 
	 * post : getWitdh() == getWitdh()@pre +1
	 * post : getHeight() == getHeight()@pre +1
	 * post : getEngine().getEnvi().cellContent(getWitdh()@pre, getHeight()@pre).getCharacter() == null
	 * post ! getEnvi().cellContent(getWitdh()@pre, getHeight()@pre).getCharacter() == null
	 * post : getEngine().getEnvi().cellContent(getWitdh(), getHeight()).getCharacter() == this
	 * post : getEnvi().cellContent(getWitdh(), getHeight()).getCharacter() == this
	 */
	public void climbRight();
	
	/**
	 * post : if getEngine().getEnvi().cellNature(getWidth()@pre, getHeight()@pre) == HOL then
	 * 			getTimeInHole() == getTimeInHole()@pre +1
	 * 			if getTimeInHole() >= 25 then getTimeInHole() == 0
	 * post : if getBehaviour() == 
	 * forall g in getEngine().getGuards(){
	 * 			
	 * 
	 * post : getItem().getColumn() == getWidth()
	 * post : getItem().getHeight() == getHeight()			
	 */
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
	
	
	public ItemService getItem();
	
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
