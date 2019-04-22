package services;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public interface EngineService {
	
	public EnvironmentService getEnvi();
	
	public PlayerService getPlayer();
	
	public ArrayList<GuardService> getGuards();
	
	public ArrayList<ItemService> getTreasures();
	
	public Status getStatus();
	
	public HashMap<Point,Integer > getHoles();
	
	public Command nextCommand();
	
	public void step();
	
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures);
	
}
