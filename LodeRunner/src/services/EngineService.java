package services;

import java.awt.Point;
import java.util.ArrayList;

public interface EngineService {
	
	public EnvironmentService getEnvi();
	
	public PlayerService getPlayer();
	
	public int getLife();
	
	public int getScore();
	
	public ArrayList<GuardService> getGuards();
	
	public ArrayList<ItemService> getTreasures();
	
	public Status getStatus();
	
	public int[][] getHoles();
	
	public Command nextCommand();
	
	/**
	 * post : nextCommand() == m
	 */
	public void setCommand(Command m);
	
	public void step();
	
	/**
	 * pre : 0 <= player.x <= e.getWidth()
	 * pre : 0 <= player.y <= e.getHeight()
	 * pre : forall g in guards {
		 0 <= g.x <= e.getWidth() && 0 <= g.y <= e.getHeight()
	 * pre : forall t in treasures {
		 0 <= t.x <= e.getWidth() && 0 <= t.y <= e.getHeight()
	 *
	 * post : getEnvi == e
	 * post : getPlayer().getWidth() == player.x && getPlayer().getHeight() == player.y
	 * post : forall i in [0; getTreasures().size()[ {
		 getTreasures()[i].getColumn() == treasures[i].x && 
		 getTreasures()[i].getHeight() == treasures[i].y &&
		 getEnvi().cellContent(treasures[i].x, treasures[i].y).getItem() == getTreasures()[i]
	 * post : forall i in [0; getGuards().size()[ {
		 getGuards()[i].getWidth() == guards[i].x && 
		 getGuards()[i].getHeight() == guards[i].y && 
		 getEnvi().cellContent(treasures[i].x, treasures[i].y).getCharacter() == getCharacter()[i]
	 * post : forall (x,y) in [0; getEnvi().getWidth()[ * [0; getEnvi().getWidth()[{ 
		 getHoles[x][y] == 16
	 * post : getCommand() == Command.NEUTRAL
	 * post : getStatus() == Status.PLAYING
	 * post : getPlayer().getEngine() == this
	 * post : getLife() == 3
	 * post : getScore() == 0
	 */
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}