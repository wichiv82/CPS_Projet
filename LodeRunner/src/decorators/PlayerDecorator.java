package decorators;

import contracts.CharacterContract;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;
import services.ScreenService;

public class PlayerDecorator extends CharacterContract implements PlayerService{

	public PlayerDecorator(PlayerService delegate) {
		// TODO Auto-generated constructor stub
		super(delegate);
	}
	
	public PlayerService getDelegate() {
		return (PlayerService) super.getDelegate();
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
