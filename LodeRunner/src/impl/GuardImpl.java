package impl;



import services.Cell;
import services.CharacterService;
import services.EngineService;
import services.GuardService;
import services.Move;
import services.PlayerService;

public class GuardImpl extends CharacterImpl implements GuardService{

	private int id;
	private CharacterService target;
	private EngineService engine;
	private Move behaviour;
	private int timeInHole;
	
	private double[] p1 = {0.5,0.7,0.9,1};
	private double[] p2 = {0.4,0.8,0.9,1};
	
	public void init(int id, CharacterService target) {
		this.id = id;
		this.target = target;
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
	
	public Move getBehaviour() {
		/*double r = Math.random();
		
		if(getEngine().getEnvi().cellNature(getWidth(), getHeight()) == Cell.EMP) {
			if(getHeight() == target.getHeight()) {
				if(getWidth() > target.getWidth()) {
					if (r < p1[0])
						return Move.LEFT;
					if (r < p1[1])
						return Move.DOWN;
					if (r < p1[2])
						return Move.UP;
					if (r < p1[3])
						return Move.RIGHT;
				}
				if(getWidth() < target.getWidth()) {
					if (r < p1[0])
						return Move.RIGHT;
					if (r < p1[1])
						return Move.DOWN;
					if (r < p1[2])
						return Move.UP;
					if (r < p1[3])
						return Move.LEFT;
				}	
			}
			if(getWidth() == target.getWidth()) {
				if(getHeight() > target.getHeight()) {
					if (r < p1[0])
						return Move.DOWN;
					if (r < p1[1])
						return Move.RIGHT;
					if (r < p1[2])
						return Move.UP; //A CHANGER A PARTIR D'ICI
					if (r < p1[3])
						return Move.RIGHT;
				}
			}
		}*/
		
		switch(engine.getEnvi().cellNature(getWidth(), getHeight())) {
			case EMP:
			case HDR:
				if(getWidth() < target.getWidth()) {
					behaviour = Move.RIGHT;
				}else {
					behaviour = Move.LEFT;
				}
				break;
			case LAD:
				if(getHeight() < target.getHeight()) {
					behaviour = Move.UP;
				}else if (getHeight() > target.getHeight()){
					behaviour = Move.DOWN;
				}else if(getWidth() < target.getWidth()) {
					behaviour = Move.RIGHT;
				}else {
					behaviour = Move.LEFT;
				}
				break;
			default:
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
		
	}

	public void step() {
		// TODO Auto-generated method stub
		if(engine.getEnvi().cellNature(getWidth(), getHeight()) == Cell.EMP &&
			(engine.getEnvi().cellNature(getWidth(), getHeight()-1) ==  Cell.EMP
			|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HDR
			|| engine.getEnvi().cellNature(getWidth(), getHeight()-1) == Cell.HOL)) {
			goDown();
			return;
		}
		
		switch(behaviour) {
			case LEFT:
				goLeft();
				break;
			case RIGHT:
				goRight();
				break;
			case UP:
				goUp();
				break;
			case DOWN:
				goDown();
				break;
			default:
				break;
		}
		
	}

}
