package decorators;

import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;
import services.ScreenService;

public class PlayerDecorator extends CharacterDecorator implements PlayerService{

	public PlayerDecorator(PlayerService delegate) {
		// TODO Auto-generated constructor stub
		super(delegate);
	}
	
	public PlayerService getDelegate() {
		return (PlayerService) super.getDelegate();
	}
	
	@Override
	public EnvironmentService getEnvi() {
		// TODO Auto-generated method stub
		return getDelegate().getEnvi();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return getDelegate().getHeight();
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return getDelegate().getWidth();
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		// TODO Auto-generated method stub
		getDelegate().init(s, x, y);
	}

	@Override
	public void goLeft() {
		// TODO Auto-generated method stub
		getDelegate().goLeft();
	}

	@Override
	public void goRight() {
		// TODO Auto-generated method stub
		delegate.goRight();
	}

	@Override
	public void goUp() {
		// TODO Auto-generated method stub
		getDelegate().goUp();
	}

	@Override
	public void goDown() {
		// TODO Auto-generated method stub
		getDelegate().goDown();
	}

	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return getDelegate().getEngine();
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		getDelegate().step();
	}

	@Override
	public void setEngine(EngineService e) {
		// TODO Auto-generated method stub
		getDelegate().setEngine(e);
	}

}
