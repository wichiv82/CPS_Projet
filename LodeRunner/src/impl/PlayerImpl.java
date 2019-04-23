package impl;


import services.Cell;
import services.EngineService;
import services.PlayerService;

public class PlayerImpl extends CharacterImpl implements PlayerService{

	private EngineService engine;
	
	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return engine;
	}
	
	public void setEngine(EngineService e) {
		engine = e;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
		if(engine.getEnvi().cellNature(getWidth(), getHeight()-1) ==  Cell.EMP) {
			goDown();
			return;
		}
		
		switch(engine.nextCommand()) {
			case LEFT:
				goLeft();
				break;
			case RIGHT:
				goRight();
				break;
			case UP :
				goUp();
				break;
			case DOWN:
				goDown();
				break;
			case DIGL:
				engine.getEnvi().dig(engine.getPlayer().getWidth()-1, engine.getPlayer().getHeight()-1);
				break;
			case DIGR:
				engine.getEnvi().dig(engine.getPlayer().getWidth()+1, engine.getPlayer().getHeight()-1);
				break;
			case NEUTRAL:
			default:
				break;
		}
	}

}
