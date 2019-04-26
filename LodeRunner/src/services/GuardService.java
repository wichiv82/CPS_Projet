package services;

public interface GuardService extends CharacterService{
	
	public int getId();
	
	public Move getBehaviour();
	
	public CharacterService getTarget();
	
	public int getTimeInHole();
	
	/**
	 * pre : getEnvi().cellNature(getHeight(), getWidth()) = Cell.HOL
	 */
	public void climbLeft();
	
	public void step();

	public void respawn();
}
