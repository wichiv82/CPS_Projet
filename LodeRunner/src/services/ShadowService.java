package services;

public interface ShadowService extends CharacterService{

	public int getTimeInHole();
	
	public EngineService getEngine();
	
	public boolean isAlive();
	
	public void setAlive(boolean a);
	
	public void setEngine(EngineService engine);
	
	public Move getBehaviour();
	
	public void climbLeft();
	
	public void climbRight();
	
	public void step();

}
