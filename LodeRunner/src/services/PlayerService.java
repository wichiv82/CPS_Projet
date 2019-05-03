package services;

public interface PlayerService extends CharacterService{
	
	public EngineService getEngine();
	
	/**
	 * post : getEngine() == e
	 */
	public void setEngine(EngineService e);
	
	/**
	 * post : if((engine.getEnvi().cellNature(getWidth()@pre, getHeight()@pre) == Cell.EMP || 
			engine.getEnvi().cellNature(getWidth()@pre, getHeight()@pre) == Cell.HOL) &&
			engine.getEnvi().cellContent(getWidth()@pre, getHeight()@pre-1).getCharacter() == null &&
			(engine.getEnvi().cellNature(getWidth()@pre, getHeight()@pre-1) ==  Cell.EMP
			|| engine.getEnvi().cellNature(getWidth()@pre, getHeight()@pre-1) == Cell.HDR
			|| engine.getEnvi().cellNature(getWidth()@pre, getHeight()@pre-1) == Cell.HOL)) 
			then getWidth() == getWidth()@pre && getHeight() == getHeight()@pre -1  
	 */
	public void step();
}
