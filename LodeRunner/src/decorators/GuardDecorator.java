package decorators;

import services.CharacterService;
import services.GuardService;
import services.ItemService;
import services.Move;
import services.ScreenService;

public class GuardDecorator extends CharacterDecorator implements GuardService {

	public GuardDecorator(GuardService delegate) {
		super(delegate);
	}
	
	public GuardService getDelegate() {
		return (GuardService) super.getDelegate();
	}
	
	@Override
	public void init(ScreenService s, int x, int y, int id, CharacterService target) {
		// TODO Auto-generated method stub
		getDelegate().init(s, x, y, id, target);
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

	@Override
	public void respawn() {
		// TODO Auto-generated method stub
		getDelegate().respawn();
	}

	@Override
	public boolean hasItem() {
		// TODO Auto-generated method stub
		return getDelegate().hasItem();
	}

	@Override
	public void removeItem() {
		// TODO Auto-generated method stub
		getDelegate().removeItem();
	}

	@Override
	public void giveItem(ItemService item) {
		// TODO Auto-generated method stub
		getDelegate().giveItem(item);
	}

	
	
	

}
