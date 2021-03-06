package impl;

import java.awt.Point;
import services.Cell;
import services.CharacterService;
import services.EngineService;
import services.GuardService;
import services.ItemService;
import services.Move;
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
		this.behaviour = Move.NEUTRAL;
		this.timeInHole = 0;
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
	
	@Override
	public ItemService getItem() {
		return item;
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
			case HOL: 
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
	
	public void climbLeft() {
		if(getWidth()>0 && getHeight()<engine.getEnvi().getHeight()-1){
			if(engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL &&
				engine.getEnvi().cellContent(getWidth(), getHeight()+1).getCharacter() == null &&
				engine.getEnvi().cellContent(getWidth()-1, getHeight()+1).getCharacter() == null){
				switch(engine.getEnvi().cellNature(getWidth()-1, getHeight()+1)){
					case EMP:
					case LAD:
					case HDR:
						engine.getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						
						width--;
						height++;
						
						engine.getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						
						timeInHole = 0;
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
				engine.getEnvi().cellContent(getWidth(), getHeight()+1).getCharacter() == null &&
				engine.getEnvi().cellContent(getWidth()+1, getHeight()+1).getCharacter() == null){
				switch(engine.getEnvi().cellNature(getWidth()+1, getHeight()+1)){
					case EMP:
					case LAD:
					case HDR:
						engine.getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
						
						width++;
						height++;

						engine.getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						
						timeInHole = 0;
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
				
			if(item != null) {
				item.setColumn(getWidth());
				item.setHeight(getHeight());
			}
			
			return;
		}
		
		if (engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL){
			timeInHole++;
			if(timeInHole >= 5){
				if(getWidth() < target.getWidth() &&
					(engine.getEnvi().cellNature(getWidth()+1, getHeight()) == Cell.MTL 
					|| engine.getEnvi().cellNature(getWidth()+1, getHeight()) == Cell.PLT
					|| getEnvi().cellContent(getWidth()+1, getHeight()).getCharacter() != null)) { 
					 switch(engine.getEnvi().cellNature(getWidth()+1, getHeight()+1)) {
					 	case HOL:
					 	case HDR:
					 	case LAD:
					 	case EMP:
					 		climbRight();
					 		break;
					 	default:
					 		break;
					 }
				}else if(getWidth() > target.getWidth() &&
					(engine.getEnvi().cellNature(getWidth()-1, getHeight()) == Cell.MTL 
					|| engine.getEnvi().cellNature(getWidth()-1, getHeight()) == Cell.PLT
					|| getEnvi().cellContent(getWidth()-1, getHeight()).getCharacter() != null)) { 
					switch(engine.getEnvi().cellNature(getWidth()-1, getHeight()+1)) {
						case HOL:
						case HDR:
						case LAD:
						case EMP:
							climbLeft();
						 	break;
						default:
						 	break;
					}
				}
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


	@Override
	public Point getPosInit() {
		// TODO Auto-generated method stub
		return posInit;
	}

}
