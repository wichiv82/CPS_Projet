package impl;

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
		return null;
	}

	@Override
	public void setEngine(EngineService engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move getBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void climbRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
