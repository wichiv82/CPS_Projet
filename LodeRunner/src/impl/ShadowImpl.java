package impl;

import services.Cell;
import services.EngineService;
import services.Move;
import services.ScreenService;
import services.ShadowService;

public class ShadowImpl extends CharacterImpl implements ShadowService {
	
	private EngineService engine;
	private Move behaviour;
	private int timeInHole;
	private boolean alive;
	
	public void init(ScreenService s, int x, int y) {
		super.init(s, x, y);
		alive = false;
		behaviour = Move.NEUTRAL;
		timeInHole = 0;
	}
	
	@Override
	public int getTimeInHole() {
		// TODO Auto-generated method stub
		return timeInHole;
	}

	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return engine;
	}

	@Override
	public void setEngine(EngineService engine) {
		// TODO Auto-generated method stub
		this.engine = engine;
	}

	@Override
	public Move getBehaviour() {
		// TODO Auto-generated method stub
		switch(engine.nextCommand()) {
			case RIGHT:
				behaviour = Move.RIGHT;
				break;
			case LEFT:
				behaviour = Move.LEFT;
				break;
			case UP:
				behaviour = Move.UP;
				break;
			case DOWN:
				behaviour = Move.DOWN;
				break;
			case NEUTRAL:
				behaviour = Move.NEUTRAL;
				break;
			default :
				break;
		}
		return behaviour;
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
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

	@Override
	public void climbRight() {
		// TODO Auto-generated method stub
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

	@Override
	public void step() {
		// TODO Auto-generated method stub
		if(engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.EMP &&
				engine.getEnvi().cellContent(getWidth(), getHeight()-1).getCharacter() == null &&
				(engine.getEnvi().cellNature(getWidth(), getHeight()-1) ==  Cell.EMP
				|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HDR
				|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HOL)) {
			
			goDown();
			
			return;
		}
		
		getBehaviour();
		
		if (engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL){
			timeInHole++;
			if(timeInHole >= 5){
				if(behaviour == Move.RIGHT &&
					(engine.getEnvi().cellNature(getWidth()+1, getHeight()) == Cell.MTL 
					|| engine.getEnvi().cellNature(getWidth()+1, getHeight()) == Cell.PLT
					|| getEnvi().cellContent(getWidth()+1, getHeight()).getCharacter() != null)) { 
					 switch(engine.getEnvi().cellNature(getWidth()+1, getHeight()+1)) {
					 	case HOL:
					 	case HDR:
					 	case LAD:
					 	case EMP:
					 		climbRight();
					 		return;
					 	default:
					 		break;
					 }
				}else if(behaviour == Move.LEFT  &&
					(engine.getEnvi().cellNature(getWidth()-1, getHeight()) == Cell.MTL 
					|| engine.getEnvi().cellNature(getWidth()-1, getHeight()) == Cell.PLT
					|| getEnvi().cellContent(getWidth()-1, getHeight()).getCharacter() != null)) { 
					switch(engine.getEnvi().cellNature(getWidth()-1, getHeight()+1)) {
						case HOL:
						case HDR:
						case LAD:
						case EMP:
							climbLeft();
						 	return;
						default:
						 	break;
					}
				}
			}
		}
		
		
		switch(behaviour) {
		case LEFT:
			if (width > 0 && engine.getEnvi().cellContent(width-1, height).getCharacter() == engine.getPlayer())
				return;
			goLeft();
			break;
		case RIGHT:
			if (width < envi.getWidth()-1 && engine.getEnvi().cellContent(width+1, height).getCharacter() == engine.getPlayer())
				return;
			goRight();
			break;
		case UP:
			if (height < envi.getHeight()-1 && engine.getEnvi().cellContent(width, height+1).getCharacter() == engine.getPlayer())
				return;
			goUp();
			break;
		case DOWN:
			if (height > 0 && engine.getEnvi().cellContent(width, height-1).getCharacter() == engine.getPlayer())
				return;
			goDown();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return alive;
	}

	@Override
	public void setAlive(boolean a) {
		// TODO Auto-generated method stub
		if(a && !alive && engine.getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null) {
			alive = a;
			envi.cellContent(getWidth(), getHeight()).setCharacter(this);
			engine.getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
			engine.getPlayer().getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
			
			for(int i=0; i<engine.getGuards().size(); i++)
				engine.getGuards().get(i).getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
				
		}else if(!a) {
			alive = a;
			envi.cellContent(getWidth(), getHeight()).removeCharacter();;
			engine.getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
			engine.getPlayer().getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
			
			for(int i=0; i<engine.getGuards().size(); i++)
				engine.getGuards().get(i).getEnvi().cellContent(getWidth(), getHeight()).removeCharacter();
			
		}
	}

}
