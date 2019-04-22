package decorators;

import services.CharacterService;
import services.EnvironmentService;
import services.ScreenService;

public class CharacterDecorator implements CharacterService{
	
	protected CharacterService delegate;

	public CharacterDecorator(CharacterService delegate) {
		this.delegate = delegate;
	}
	
	public CharacterService getDelegate() {
		return delegate;
	}
	
	@Override
	public EnvironmentService getEnvi() {
		// TODO Auto-generated method stub
		return delegate.getEnvi();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return delegate.getHeight();
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return delegate.getWidth();
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		// TODO Auto-generated method stub
		delegate.init(s, x, y);
	}

	@Override
	public void goLeft() {
		// TODO Auto-generated method stub
		delegate.goLeft();
	}

	@Override
	public void goRight() {
		// TODO Auto-generated method stub
		delegate.goRight();
	}

	@Override
	public void goUp() {
		// TODO Auto-generated method stub
		delegate.goUp();
	}

	@Override
	public void goDown() {
		// TODO Auto-generated method stub
		delegate.goDown();
	}

}
