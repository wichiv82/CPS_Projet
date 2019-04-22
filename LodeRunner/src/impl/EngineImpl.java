package impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import contracts.ItemContract;
import contracts.PlayerContract;
import services.Cell;
import services.Command;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.PlayerService;
import services.Status;

public class EngineImpl implements EngineService {
	private PlayerService player;
	private ArrayList<GuardService> guards;
	private ArrayList<ItemService> treasures;
	
	private EnvironmentService envi;
	private Status status;
	
	private Command command;
	
	@Override
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		// TODO Auto-generated method stub
		envi.init(e);
		this.guards = new ArrayList<>();
		this.treasures = new ArrayList<>();
		
		this.player = new PlayerContract(new PlayerImpl());
		this.player.init(e, (int)player.getX(), (int)player.getY());
		
		for(Point coord : treasures) {
			this.treasures.add(new ItemContract(new ItemImpl()));
			this.treasures.get(this.treasures.size()).init(this.treasures.size() - 1, ItemType.TREASURE, (int)coord.getX(), (int)coord.getY());
		}
		
	}
	
	@Override
	public EnvironmentService getEnvi() {
		// TODO Auto-generated method stub
		return envi;
	}
	@Override
	public PlayerService getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}
	@Override
	public ArrayList<GuardService> getGuards() {
		// TODO Auto-generated method stub
		return guards;
	}
	@Override
	public ArrayList<ItemService> getTreasures() {
		// TODO Auto-generated method stub
		ArrayList<ItemService> tresors = new ArrayList<ItemService>();
		for (ItemService i : treasures)
			if (i.getNature() == ItemType.TREASURE)
				tresors.add(i);
		return tresors;
	}
	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return status;
	}
	@Override
	public HashMap<Point, Integer> getHoles() {
		// TODO Auto-generated method stub
		HashMap<Point, Integer> trous = new HashMap<Point, Integer>();
		
		for(int i=0; i<getEnvi().getWidth(); i++) {
			for(int j=0; j<getEnvi().getHeight(); j++) {
				if(getEnvi().cellNature(i, j) == Cell.HOL) {
					trous.put(new Point(i,j), 0);
				}
			}
		}
		
		return trous;
	}
	@Override
	public Command nextCommand() {
		// TODO Auto-generated method stub
		return command;
	}
	
	public void setCommand(Command m) {
		command = m;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		for (int i=0; i< treasures.size(); i++) {
			if (treasures.get(i).getHeight() == player.getHeight() && treasures.get(i).getColumn() == player.getWidth()) {
				envi.cellContent(treasures.get(i).getColumn(), treasures.get(i).getHeight() ).removeItem();
				treasures.remove(i);
				break;
			}
		}
		
		if (treasures.size() == 0) {
			status = Status.LOSS;
			return;
		}
		
		int xplayer = player.getWidth();
		int yplayer = player.getHeight();
		
			
		player.step();
		
		envi.cellContent(xplayer, yplayer).removeCharacter();
		envi.cellContent(xplayer, yplayer).setCharacter(player);
		
	}
	
}
