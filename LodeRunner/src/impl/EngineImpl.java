package impl;

import java.awt.Point;

import java.util.ArrayList;
import services.Cell;
import services.Command;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.Paire;
import services.PlayerService;
import services.Status;

public class EngineImpl implements EngineService {
	private PlayerService player;
	private int life;
	private int score;
	private ArrayList<GuardService> guards;
	private ArrayList<ItemService> treasures;
	
	private EnvironmentService envi;
	private int[][] holes;
	private Status status;
	
	private Command command;
	
	@Override
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		// TODO Auto-generated method stub
		envi = new EnvironmentImpl();
		envi.init(e);
		holes = new int[e.getWidth()][e.getHeight()];
		for(int i=0; i<e.getWidth(); i++)
			for(int j=0; j<e.getHeight(); j++)
				holes[i][j] = 16;
		
		this.guards = new ArrayList<GuardService>();
		this.treasures = new ArrayList<ItemService>();
		
		this.player = new PlayerImpl();
		this.player.init(e, player.x, player.y);
		envi.setCellContent(player.x, player.y, new Paire(this.player, null));
		
		for(int i=0; i<treasures.size(); i++) {
			ItemImpl t = new ItemImpl();
			t.init(i, ItemType.TREASURE, treasures.get(i).x, treasures.get(i).y);
			this.treasures.add(t);
			envi.setCellContent(treasures.get(i).x, treasures.get(i).y, new Paire(null, t));
		}
		
		for (int i=0; i<guards.size(); i++) {
			GuardImpl g = new GuardImpl();
			g.init(e, guards.get(i).x, guards.get(i).y, i, this.player); // TOUS LES GARDES PORTENT UN TRESOR A L'HEURE ACTUELLE !!!
			g.setEngine(this);
			this.guards.add(g);
			envi.setCellContent(guards.get(i).x, guards.get(i).y, new Paire(this.guards.get(i),null));
		}
		
		command = Command.NEUTRAL;
		status = Status.PLAYING;
		
		this.player.setEngine(this);
		this.life = 3;
		this.score = 0;
	}
	
	public int getLife() {
		return life;
	}
	
	public int getScore() {
		return score;
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
		/*ArrayList<ItemService> tresors = new ArrayList<ItemService>();
		for (ItemService i : treasures)
			if (i.getNature() == ItemType.TREASURE)
				tresors.add(i);*/
		return treasures;
	}
	
	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return status;
	}
	
	@Override
	public int[][] getHoles() {
		// TODO Auto-generated method stub
		return holes;
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
		for (int i=0; i<guards.size(); i++) {
			int xguard = guards.get(i).getWidth();
			int yguard = guards.get(i).getHeight();
			
			if(player.getWidth() == xguard && player.getHeight() == yguard) {
				status = Status.LOSS;
				life--;
				return;
			}
				
		}
		
		for (int i=0; i< treasures.size(); i++) {
			if (treasures.get(i).getHeight() == player.getHeight() && treasures.get(i).getColumn() == player.getWidth()) {
				envi.cellContent(treasures.get(i).getColumn(), treasures.get(i).getHeight() ).removeItem();
				treasures.remove(i);
				score++;
				break;
			}
		}
		
		if (treasures.size() == 0) {
			status = Status.WIN;
			return;
		}
		
		int xplayer = player.getWidth();
		int yplayer = player.getHeight();

		envi.cellContent(xplayer, yplayer).removeCharacter();
			
		player.step();
		
		xplayer = player.getWidth();
		yplayer = player.getHeight();
		
		envi.cellContent(xplayer, yplayer).setCharacter(player);
		
		
		for (int i=0; i<guards.size(); i++) {
			int xguard = guards.get(i).getWidth();
			int yguard = guards.get(i).getHeight();
			
			if(!guards.get(i).hasItem()
					&& envi.cellContent(xguard, yguard).getItem() != null
					&& envi.cellContent(xguard, yguard).getItem().getNature() == ItemType.TREASURE) {
				
				guards.get(i).giveItem(envi.cellContent(xguard, yguard).getItem());
				envi.cellContent(xguard, yguard).removeItem();
				
			}
			
			envi.cellContent(xguard, yguard).removeCharacter();
			
			guards.get(i).step();
			
			xguard = guards.get(i).getWidth();
			yguard = guards.get(i).getHeight();
			
			envi.cellContent(xguard, yguard).setCharacter(guards.get(i));
			
		}
		
		for (int i=0; i<holes.length; i++) {
			for (int j=0; j<holes[i].length; j++){
				holes[i][j]++;
				if(holes[i][j] == 15) {
					System.out.println("Un trou a été bouché");
					envi.fill(i, j);
					player.getEnvi().fill(i, j);
					for(GuardService g : guards) {
						g.getEnvi().fill(i, j);
						if(g.getWidth() == i && g.getHeight() == j)
							g.respawn();
					}
				}
			}
		}
		
		if(envi.cellNature(xplayer, yplayer) == Cell.PLT) {
			status = Status.LOSS;
			life--;
			return;
		}
		
	}
	
}
