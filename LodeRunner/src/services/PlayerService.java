package services;

public interface PlayerService extends CharacterService{
	
	public EngineService getEngine();
	
	/**
	 * post : getEngine() === e
	 */
	public void setEngine(EngineService e);
	
	/**
	 * post : if((engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.EMP || 
			engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL) &&
			engine.getEnvi().cellContent(getWidth(), getHeight()-1).getCharacter() == null &&
			(engine.getEnvi().cellNature(getWidth(), getHeight()-1) ==  Cell.EMP
			|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HDR
			|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HOL)) 
			then getWidth(step()) = getWidth() && getHeight(step()) = getHeight()-1  
	 */
	public void step();
}
