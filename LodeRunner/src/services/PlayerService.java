package services;

public interface PlayerService extends CharacterService{
	
	public EngineService getEngine();
	
	/**
	 * post : getEngine() === e
	 */
	public void setEngine(EngineService e);
	
	/**
	 * 
	 */
	public void step();
}
