package decorators;

import contracts.ScreenContract;
import services.Cell;
import services.EditableScreenService;

public class EditableScreenDecorator extends ScreenContract implements EditableScreenService{

	
	public EditableScreenDecorator(EditableScreenService delegate) {
		// TODO Auto-generated constructor stub
		super(delegate);
	}
	
	public EditableScreenService getDelegate() {
		return (EditableScreenService) super.getDelegate();
	}

	@Override
	public void init(String file_name) {
		// TODO Auto-generated method stub
		getDelegate().init(file_name);
		
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

}
