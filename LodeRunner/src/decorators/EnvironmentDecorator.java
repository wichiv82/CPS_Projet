package decorators;

import services.Cell;
import services.EditableScreenService;
import services.EnvironmentService;
import services.Paire;

public class EnvironmentDecorator extends ScreenDecorator implements EnvironmentService{

	public EnvironmentDecorator(EnvironmentService delegate) {
		// TODO Auto-generated constructor stub
		super(delegate);
	}
	
	public EnvironmentService getDelegate() {
		return (EnvironmentService) super.getDelegate();
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
	public Cell cellNature(int x, int y) {
		// TODO Auto-generated method stub
		return getDelegate().cellNature(x, y);
	}

	@Override
	public void init(int w, int h) {
		// TODO Auto-generated method stub
		getDelegate().init(w, h);
	}

	@Override
	public void dig(int x, int y) {
		// TODO Auto-generated method stub
		getDelegate().init(x, y);
	}

	@Override
	public void fill(int x, int y) {
		// TODO Auto-generated method stub
		getDelegate().fill(x, y);
	}

	@Override
	public Paire cellContent(int x, int y) {
		// TODO Auto-generated method stub
		return getDelegate().cellContent(x, y);
	}

	@Override
	public void init(EditableScreenService e) {
		// TODO Auto-generated method stub
		getDelegate().init(e);
	}

}
