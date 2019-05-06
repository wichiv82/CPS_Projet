package decorators;

import contracts.ScreenContract;
import services.Cell;
import services.EditableScreenService;
import services.EnvironmentService;
import services.Paire;

public class EnvironmentDecorator extends ScreenContract implements EnvironmentService{

	public EnvironmentDecorator(EnvironmentService delegate) {
		// TODO Auto-generated constructor stub
		super(delegate);
	}
	
	public EnvironmentService getDelegate() {
		return (EnvironmentService) super.getDelegate();
	}

	@Override
	public void init(EditableScreenService e) {
		// TODO Auto-generated method stub
		getDelegate().init(e);
	}
	
	@Override
	public Paire cellContent(int x, int y) {
		// TODO Auto-generated method stub
		return getDelegate().cellContent(x, y);
	}

	@Override
	public void setCellContent(int x, int y, Paire p) {
		// TODO Auto-generated method stub
		getDelegate().setCellContent(x, y, p);
	}

	

}
