package services;

public interface ShadowService extends CharacterService{

	public void init(ScreenService s, int x, int y);
	
	public int getTimeInHole();
	
	public EngineService getEngine();
	
	public void setEngine(EngineService engine);
	
	public Move getBehaviour();
	
	public void climbLeft();
	
	public void climbRight();
	
	public void step();

}
