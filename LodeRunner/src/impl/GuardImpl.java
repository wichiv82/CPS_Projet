package impl;

import java.awt.Point;

import services.Cell;
import services.CharacterService;
import services.EngineService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.Move;
import services.Paire;
import services.ScreenService;

public class GuardImpl extends CharacterImpl implements GuardService{

	private int id;
	private Point posInit;
	private CharacterService target;
	private EngineService engine;
	private Move behaviour;
	private int timeInHole;
	private ItemService item;
	
	
	public void init(ScreenService s, int x, int y, int id, CharacterService target) {
		super.init(s, x, y);
		this.id = id;
		this.target = target;
		this.posInit = new Point(x,y);
		this.item = null;
	}
	
	
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return engine;
	}
	
	public void setEngine(EngineService engine) {
		this.engine = engine;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean hasItem() {
		return item != null;
	}
	
	public void removeItem() {
		item = null;
	}
	
	public void giveItem(ItemService item) {
		this.item = item;
	}
	
	public void respawn() {
		getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
		engine.getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
		
		width = posInit.x;
		height = posInit.y;
		
		getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
		engine.getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
	}
	
	public Move getBehaviour() {
		
		switch(engine.getEnvi().cellNature(getWidth(), getHeight())) {
			case EMP:
			case HDR:
				if(getWidth() < target.getWidth()) {
					behaviour = Move.RIGHT;
				}else if(getWidth() > target.getWidth()) {
					behaviour = Move.LEFT;
				}else {
					behaviour = Move.NEUTRAL;
				}
				break;
			case LAD:
				if(getHeight() < target.getHeight()) {
					behaviour = Move.UP;
				}else if (getHeight() > target.getHeight()){
					behaviour = Move.DOWN;
				}else if(getWidth() < target.getWidth()) {
					behaviour = Move.RIGHT;
				}else if(getWidth() > target.getWidth()) {
					behaviour = Move.LEFT;
				}else {
					behaviour = Move.NEUTRAL;
				}
				break;
			default:
				behaviour = Move.NEUTRAL;
				break;
		}
		
		return behaviour;
	}
	
	public CharacterService getTarget() {
		return target;
	}
	
	public int getTimeInHole() {
		return timeInHole;
	}
	
	/**
	 * pre : getEnvi().cellNature(getHeight(), getWidth()) = Cell.HOL
	 */
	public void climbLeft() {
		if(getWidth()>0 && getHeight()<engine.getEnvi().getHeight()-1){
			if(engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL &&
				engine.getEnvi().cellContent(getWidth(), getHeight()+1).getCharacter() == null){
				switch(engine.getEnvi().cellNature(getWidth()-1, getHeight()+1)){
					case EMP:
					case LAD:
					case HDR:
						// FAIRE MONTER LE GARDE A GAUCHE
						engine.getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						width--;
						height++;
						//engine.getEnvi().setCellContent(getWidth(), getHeight(), new Paire(this, null));
						//getEnvi().setCellContent(getWidth(), getHeight(), new Paire(this, null));
						
						engine.getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						break;
					default:
						break;
				}
				
			}	
		}
	}
	
	public void climbRight(){
		if(getWidth()<engine.getEnvi().getWidth()-1 && getHeight()<engine.getEnvi().getHeight()-1){
			if(engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL &&
				engine.getEnvi().cellContent(getWidth(), getHeight()+1).getCharacter() == null){
				switch(engine.getEnvi().cellNature(getWidth()+1, getHeight()+1)){
					case EMP:
					case LAD:
					case HDR:
						// FAIRE MONTER LE GARDE A DROITE
						//engine.getEnvi().setCellContent(getWidth(), getHeight(), new Paire(null, null));
						//getEnvi().setCellContent(getWidth(), getHeight(), new Paire(null, null));
						
						engine.getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						width++;
						height++;
						//engine.getEnvi().setCellContent(getWidth(), getHeight(), new Paire(this, null));
						//getEnvi().setCellContent(getWidth(), getHeight(), new Paire(this, null));
						engine.getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						break;
					default:
						break;
				}
				
			}	
		}
	}

	public void step() {
		// TODO Auto-generated method stub
		
		if(engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.EMP &&
				engine.getEnvi().cellContent(getWidth(), getHeight()-1).getCharacter() == null &&
				(engine.getEnvi().cellNature(getWidth(), getHeight()-1) ==  Cell.EMP
				|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HDR
				|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HOL)) {
			
			if(engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HOL 
				 && hasItem()
				 && engine.getEnvi().cellContent(getWidth(), getHeight()).getItem() == null) {
				
				item.setColumn(getWidth());
				item.setHeight(getHeight());
				engine.getEnvi().cellContent(getWidth(), getHeight()).setItem(item);
				getEnvi().cellContent(getWidth(), getHeight()).setItem(item);
				removeItem();
			}
			goDown();
				
			return;
		}
		
		if (engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL){
			timeInHole++;
			if(timeInHole >= 25){
				if(getWidth() < target.getWidth())
					climbRight();
				else
					climbLeft();
				timeInHole = 0;
				return;
			}
		}
		
		getBehaviour();
		
		switch(behaviour) {
			case LEFT:
				for (GuardService g : engine.getGuards())
					if (engine.getEnvi().cellContent(width-1, height).getCharacter() == g)
						return;
				goLeft();
				break;
			case RIGHT:
				for (GuardService g : engine.getGuards())
					if (engine.getEnvi().cellContent(width+1, height).getCharacter() == g)
						return;
				goRight();
				break;
			case UP:
				for (GuardService g : engine.getGuards())
					if (engine.getEnvi().cellContent(width, height+1).getCharacter() == g)
						return;
				goUp();
				break;
			case DOWN:
				for (GuardService g : engine.getGuards())
					if (engine.getEnvi().cellContent(width, height-1).getCharacter() == g)
						return;
				goDown();
				break;
			default:
				break;
		}
		
		if(item != null) {
			item.setColumn(getWidth());
			item.setHeight(getHeight());
		}
	}

}
