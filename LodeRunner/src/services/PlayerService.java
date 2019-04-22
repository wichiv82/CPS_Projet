package services;

public interface PlayerService extends CharacterService{
	
	public EngineService getEngine();
	
	public void step();
}
