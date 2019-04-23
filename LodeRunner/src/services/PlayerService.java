package services;

public interface PlayerService extends CharacterService{
	
	public EngineService getEngine();
	public void setEngine(EngineService e);
	public void step();
}
