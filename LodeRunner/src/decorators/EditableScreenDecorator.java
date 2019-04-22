package decorators;

import services.Cell;
import services.EditableScreenService;

public class EditableScreenDecorator extends ScreenDecorator implements EditableScreenService{

	
	public EditableScreenDecorator(EditableScreenService delegate) {
		// TODO Auto-generated constructor stub
		super(delegate);
	}
	
	public EditableScreenService getDelegate() {
		return (EditableScreenService) super.getDelegate();
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
		getDelegate().dig(x, y);
		
	}

	@Override
	public void fill(int x, int y) {
		// TODO Auto-generated method stub
		getDelegate().fill(x, y);
	}

	@Override
	public boolean isPlayable() {
		// TODO Auto-generated method stub
		return getDelegate().isPlayable();
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		// TODO Auto-generated method stub
		getDelegate().setNature(x, y, c);
		
	}

	@Override
	public void init(String file_name) {
		// TODO Auto-generated method stub
		getDelegate().init(file_name);
		
	}

}
