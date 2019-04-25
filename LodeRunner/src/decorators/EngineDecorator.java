package decorators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import services.Command;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.PlayerService;
import services.Status;

public class EngineDecorator implements EngineService{

	protected EngineService delegate;
	
	public EngineDecorator(EngineService delegate) {
		// TODO Auto-generated constructor stub
		this.delegate = delegate;
	}
	
	public EngineService getDelegate() {
		return delegate;
	}
	
	@Override
	public EnvironmentService getEnvi() {
		// TODO Auto-generated method stub
		return delegate.getEnvi();
	}

	@Override
	public PlayerService getPlayer() {
		// TODO Auto-generated method stub
		return delegate.getPlayer();
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		// TODO Auto-generated method stub
		return delegate.getGuards();
	}

	@Override
	public ArrayList<ItemService> getTreasures() {
		// TODO Auto-generated method stub
		return delegate.getTreasures();
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return delegate.getStatus();
	}

	@Override
	public int[][] getHoles() {
		// TODO Auto-generated method stub
		return delegate.getHoles();
	}

	@Override
	public Command nextCommand() {
		// TODO Auto-generated method stub
		return delegate.nextCommand();
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		delegate.step();
		
	}

	@Override
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		// TODO Auto-generated method stub
		delegate.init(e, player, guards, treasures);
	}

}
