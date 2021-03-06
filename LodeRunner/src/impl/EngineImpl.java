package impl;

import java.awt.Point;

import java.util.ArrayList;

import contracts.EnvironmentContract;
import contracts.GuardContract;
import contracts.ItemContract;
import contracts.PlayerContract;
import contracts.ShadowContract;
import services.Cell;
import services.Command;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.PlayerService;
import services.ShadowService;
import services.Status;

public class EngineImpl implements EngineService {
	private PlayerService player;
	private int life;
	private int score;
	private Point spawn;
	private ArrayList<GuardService> guards;
	private ArrayList<ItemService> treasures;
	private ShadowService shadow;
	
	private EnvironmentService envi;
	private int[][] holes;
	private Status status;
	
	private Command command;
	
	private boolean contract = false;
	public EngineImpl(boolean contract) {
		this.contract = contract;
	}
	
	@Override
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		// TODO Auto-generated method stub
		if(contract)
			envi = new EnvironmentContract(new EnvironmentImpl());
		else envi = new EnvironmentImpl();
		envi.init(e);
		holes = new int[e.getWidth()][e.getHeight()];
		for(int i=0; i<e.getWidth(); i++)
			for(int j=0; j<e.getHeight(); j++)
				holes[i][j] = 11;
		
		this.guards = new ArrayList<GuardService>();
		this.treasures = new ArrayList<ItemService>();
		
		if(contract)
			this.player = new PlayerContract(new PlayerImpl());
		else this.player = new PlayerImpl();
		this.player.init(e, player.x, player.y);
		envi.cellContent(player.x, player.y).setCharacter(this.player);
		spawn = player;
		
		ItemService t;
		for(int i=0; i<treasures.size(); i++) {
			if(contract)
				t = new ItemContract(new ItemImpl());
			else t = new ItemImpl();
			t.init(i, ItemType.TREASURE, treasures.get(i).x, treasures.get(i).y);
			this.treasures.add(t);
			envi.cellContent(treasures.get(i).x, treasures.get(i).y).setItem(t);
		}
		
		GuardService g;
		for (int i=0; i<guards.size(); i++) {
			if(contract)
				g = new GuardContract(new GuardImpl());
			else g = new GuardImpl();
			g.init(e, guards.get(i).x, guards.get(i).y, i, this.player);
			g.setEngine(this);
			this.guards.add(g);
			envi.cellContent(guards.get(i).x, guards.get(i).y).setCharacter(this.guards.get(i));
		}
		
		command = Command.NEUTRAL;
		status = Status.PLAYING;
		
		this.player.setEngine(this);
		
		if (contract) this.shadow = new ShadowContract(new ShadowImpl());
		else this.shadow = new ShadowImpl();
		this.shadow.init(e, spawn.x, spawn.y);
		this.shadow.setEngine(this);
		
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
		return treasures;
	}
	
	@Override
	public ShadowService getShadow() {
		// TODO Auto-generated method stub
		return shadow;
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
			
			player.getEnvi().cellContent(xguard, yguard).removeCharacter();
			envi.cellContent(xguard, yguard).removeCharacter();
			
			guards.get(i).step();
			
			xguard = guards.get(i).getWidth();
			yguard = guards.get(i).getHeight();
			
			player.getEnvi().cellContent(xguard, yguard).setCharacter(guards.get(i));
			envi.cellContent(xguard, yguard).setCharacter(guards.get(i));
		}
		
		for (int i=0; i<holes.length; i++) {
			for (int j=0; j<holes[i].length; j++){
				holes[i][j]++;
				if(holes[i][j] == 10) {
					envi.fill(i, j);
					player.getEnvi().fill(i, j);
					shadow.getEnvi().fill(i, j);
					ArrayList<Integer> kill = new ArrayList<>(); 
					for(GuardService g : guards) {
						g.getEnvi().fill(i, j);
						if(g.getWidth() == i && g.getHeight() == j) {
							for(int k = 0; k < getGuards().size(); k++)
								if(getGuards().get(k).getWidth() == g.getPosInit().getX()
								&& getGuards().get(k).getHeight() == g.getPosInit().getY())
									kill.add(getGuards().get(k).getId());
							g.respawn();
						}
					}
					
					for (int k=0; k<getGuards().size(); k++) {
						if (kill.contains(getGuards().get(k).getId())) {
							getGuards().remove(k);
							k--;
						}
					}
				}
			}
		}
		
		if(shadow.isAlive()) {
			int xshadow = shadow.getWidth();
			int yshadow = shadow.getHeight();
			
			envi.cellContent(xshadow, yshadow).removeCharacter();
			player.getEnvi().cellContent(xshadow, yshadow).removeCharacter();
			for(int i=0; i<guards.size(); i++)
				guards.get(i).getEnvi().cellContent(xshadow, yshadow).removeCharacter();
			
			shadow.step();
			
			xshadow = shadow.getWidth();
			yshadow = shadow.getHeight();
			
			envi.cellContent(xshadow, yshadow).setCharacter(shadow);
			player.getEnvi().cellContent(xshadow, yshadow).setCharacter(shadow);
			
			for (int i=0; i<guards.size(); i++) {
				int xguard = guards.get(i).getWidth();
				int yguard = guards.get(i).getHeight();
				
				guards.get(i).getEnvi().cellContent(xshadow, yshadow).setCharacter(shadow);
				
				if(shadow.getWidth() == xguard && shadow.getHeight() == yguard) {
					ItemService itemGardeMort = guards.get(i).getItem();
					
					if(guards.get(i).hasItem()) {
						
						if (envi.cellContent(xguard, yguard).getItem() == null) {
							envi.cellContent(xguard, yguard).setItem(itemGardeMort);
							player.getEnvi().cellContent(xguard, yguard).setItem(itemGardeMort);
							for (int j=0; j<guards.size(); j++) 
								guards.get(j).getEnvi().cellContent(xguard, yguard).setItem(itemGardeMort);
						}else{
							treasures.remove(itemGardeMort);
							score++;
						}
						
					}
					guards.remove(i);
					shadow.setAlive(false);
					break;
				}
			
			}
		}
		
		
		for(GuardService guard : guards)
			if(player.getWidth() == guard.getWidth() && player.getHeight() == guard.getHeight()) {
				status = Status.LOSS;
				life--;
				return;
			}
		
		if(envi.cellNature(xplayer, yplayer) == Cell.PLT) {
			status = Status.LOSS;
			life--;
			return;
		}
		
		for (int i=0; i< treasures.size(); i++) {
			if (treasures.get(i).getHeight() == player.getHeight() && treasures.get(i).getColumn() == player.getWidth()) {
				envi.cellContent(treasures.get(i).getColumn(), treasures.get(i).getHeight() ).removeItem();
				treasures.remove(i);
				score++;
				
				if(!shadow.isAlive()) {
					shadow.setAlive(true);
				}
				break;
			}
		}
		
		if (treasures.size() == 0 && guards.size() == 0) {
			status = Status.WIN;
			return;
		}
		
	}

}
