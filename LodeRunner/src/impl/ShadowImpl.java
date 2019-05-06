package impl;

import services.Cell;
import services.EngineService;
import services.Move;
import services.ShadowService;

public class ShadowImpl extends CharacterImpl implements ShadowService {
	
	private EngineService engine;
	private Move behaviour;
	private int timeInHole;
	
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
						// FAIRE MONTER LE GARDE A GAUCHE
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
						// FAIRE MONTER LE GARDE A DROITE
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
	}

}
