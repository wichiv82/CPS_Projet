package decorators;

import contracts.CharacterContract;
import services.CharacterService;
import services.EngineService;
import services.Move;
import services.ShadowService;

public class ShadowDecorator extends CharacterContract implements ShadowService{

	public ShadowDecorator(ShadowService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}
	
	public ShadowService getDelegate() {
		return (ShadowService) super.getDelegate();
	}

	@Override
	public int getTimeInHole() {
		// TODO Auto-generated method stub
		return getDelegate().getTimeInHole();
	}

	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return getDelegate().getEngine();
	}

	@Override
	public void setEngine(EngineService engine) {
		// TODO Auto-generated method stub
		getDelegate().setEngine(engine);
	}

	@Override
	public Move getBehaviour() {
		// TODO Auto-generated method stub
		return getDelegate().getBehaviour();
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
		System.out.println(getHeight() + " " + getEnvi());
		getDelegate().climbLeft();
	}

	@Override
	public void climbRight() {
		// TODO Auto-generated method stub
		getDelegate().climbRight();
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		getDelegate().step();
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
