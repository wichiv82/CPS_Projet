package decorators;

import services.Cell;
import services.ScreenService;

public class ScreenDecorator implements ScreenService{

	protected ScreenService delegate;
	
	public ScreenDecorator(ScreenService delegate) {
		this.delegate = delegate;
	}
	
	public ScreenService getDelegate() {
		return delegate;
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
	public Cell cellNature(int x, int y) {
		// TODO Auto-generated method stub
		return delegate.cellNature(x, y);
	}

	@Override
	public void init(int w, int h) {
		// TODO Auto-generated method stub
		delegate.init(w, h);
	}

	@Override
	public void dig(int x, int y) {
		// TODO Auto-generated method stub
		delegate.dig(x, y);
	}

	@Override
	public void fill(int x, int y) {
		// TODO Auto-generated method stub
		delegate.fill(x, y);
	}

}
