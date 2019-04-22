package decorators;

import services.CharacterService;
import services.GuardService;
import services.Move;

public class GuardDecorator extends CharacterDecorator implements GuardService {

	public GuardDecorator(GuardService delegate) {
		super(delegate);
	}
	
	public GuardService getDelegate() {
		return (GuardService) super.getDelegate();
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return getDelegate().getId();
	}

	@Override
	public Move getBehaviour() {
		// TODO Auto-generated method stub
		return getDelegate().getBehaviour();
	}

	@Override
	public CharacterService getTarget() {
		// TODO Auto-generated method stub
		return getDelegate().getTarget();
	}

	@Override
	public int getTimeInHole() {
		// TODO Auto-generated method stub
		return getDelegate().getTimeInHole();
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
		getDelegate().climbLeft();
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		getDelegate().step();
	}
	
	

}
