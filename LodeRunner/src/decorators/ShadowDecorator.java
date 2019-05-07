package decorators;

import services.CharacterService;
import services.EngineService;
import services.Move;
import services.ShadowService;

public class ShadowDecorator extends CharacterDecorator implements ShadowService{

	public ShadowDecorator(CharacterService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}
	
	public ShadowService getDelegate() {
		return (ShadowService) super.getDelegate();
	}

	@Override
	public int getTimeInHole() {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return getDelegate().isAlive();
	}

	@Override
	public void setAlive(boolean a) {
		// TODO Auto-generated method stub
		getDelegate().setAlive(a);
	}

}
