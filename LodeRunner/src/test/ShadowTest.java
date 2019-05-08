package test;


import java.awt.Point;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import contracts.EditableScreenContract;
import contracts.ShadowContract;
import impl.EditableScreenImpl;
import impl.ShadowImpl;
import services.Cell;
import services.EditableScreenService;
import services.EngineService;
import services.ShadowService;
import contracts.EngineContract;
import impl.EngineImpl;

public class ShadowTest extends MyTest {

	private ShadowService shadow = new ShadowContract(new ShadowImpl());
	private EditableScreenService edit = new EditableScreenContract(new EditableScreenImpl());
	private EngineService engine = new EngineContract(new EngineImpl(true));
	private ArrayList<Point> guards = new ArrayList<>();
	private ArrayList<Point> treasures = new ArrayList<>();
	
	@Test
	public void climbL_valide() {
		edit.init(7,3);
		for(int i = 0; i < 7; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		edit.setNature(0,1,Cell.EMP);
		guards.add(new Point(0,1));
		Point player = new Point(3,2);
		for(Cell c1 : Cell.values())
			for(Cell c2 : Cell.values()) {
				edit.setNature(2, 1, c1);
				edit.setNature(2, 2, c2);
				engine = new EngineContract(new EngineImpl(true));
				engine.init(edit, player, guards, treasures);
				shadow.init(edit, 3, 2);
				shadow.setEngine(engine);
				engine.getPlayer().goRight();
				engine.getPlayer().goRight();
				engine.getEnvi().dig(3, 1);
				engine.getShadow().setAlive(true);
				engine.getShadow().goDown();
				shadow.climbLeft();
				edit.setNature(2, 1, Cell.PLT);
				edit.setNature(2, 2, Cell.EMP);
			}
	}
	
	@Test
	public void climbR_valide() {
		edit.init(7,3);
		for(int i = 0; i < 7; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		edit.setNature(0,1,Cell.EMP);
		guards.add(new Point(0,1));
		Point player = new Point(3,2);
		for(Cell c1 : Cell.values())
			for(Cell c2 : Cell.values()) {				
				edit.setNature(4, 1, c1);
				edit.setNature(4, 2, c2);
				engine = new EngineContract(new EngineImpl(true));
				engine.init(edit, player, guards, treasures);
				shadow.init(edit, 3, 2);
				shadow.setEngine(engine);
				engine.getPlayer().goLeft();
				engine.getPlayer().goLeft();
				engine.getEnvi().dig(3, 1);
				engine.getShadow().setAlive(true);
				engine.getShadow().goDown();
				shadow.climbRight();
				edit.setNature(4, 1, Cell.PLT);
				edit.setNature(4, 2, Cell.EMP);
			}
	}
	
	@Test
	public void climb_block_player() {
		edit.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		treasures.add(new Point(3,2));
		treasures.add(new Point(2,2));
		Point player = new Point(1,2);
		engine = new EngineContract(new EngineImpl(true));
		engine.init(edit, player, guards, treasures);
		engine.getPlayer().goRight();
		engine.getEnvi().dig(1, 1);
		engine.getPlayer().getEnvi().dig(1,1);
		engine.getShadow().getEnvi().dig(1,1);
		engine.getShadow().setAlive(true);
		engine.getShadow().goDown();
		System.out.println("Shadow : (" + shadow.getWidth() + "," + shadow.getHeight() + ")");
		System.out.println("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")");
		engine.getShadow().climbRight();
		if(!(engine.getShadow().getWidth() == 1 && engine.getShadow().getHeight() == 1))
			Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ") "
					+ "Player : (" + engine.getPlayer().getWidth() + "," + engine.getPlayer().getHeight() + ")");
		engine.getPlayer().goLeft();
		engine.getShadow().climbRight();
		if(!(engine.getShadow().getWidth() == 1 && engine.getShadow().getHeight() == 1))
			Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ") "
					+ "Player : (" + engine.getPlayer().getWidth() + "," + engine.getPlayer().getHeight() + ")");
		engine.getShadow().climbLeft();
		if(!(engine.getShadow().getWidth() == 1 && engine.getShadow().getHeight() == 1))
			Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ") "
					+ "Player : (" + engine.getPlayer().getWidth() + "," + engine.getPlayer().getHeight() + ")");
		engine.getPlayer().goLeft();
		engine.getShadow().climbLeft();
		if(!(engine.getShadow().getWidth() == 1 && engine.getShadow().getHeight() == 1))
			Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ") "
					+ "Player : (" + engine.getPlayer().getWidth() + "," + engine.getPlayer().getHeight() + ")");
		
	}
}
