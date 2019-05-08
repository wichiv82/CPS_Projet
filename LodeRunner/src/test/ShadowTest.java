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
import services.EnvironmentService;
import services.Paire;
import services.ShadowService;
import contracts.EngineContract;
import contracts.PreconditionError;
import impl.EngineImpl;

public class ShadowTest extends MyTest {

	
	@Test
	public void init_valid() {
		edit = new EditableScreenContract(new EditableScreenImpl());
		shadow = new ShadowContract(new ShadowImpl());
		edit.init(1,1);
		shadow.init(edit, 0, 0);		
	}
	
	@Test
	public void init_unbound() {
		edit = new EditableScreenContract(new EditableScreenImpl());
		shadow = new ShadowContract(new ShadowImpl());
		edit.init(1, 1);
		for(int x = -1; x < 2; x = x + 2)
			for(int y = -1; y < 2; y = y + 2) {
				try {
					shadow.init(edit, x, y);
				} catch(PreconditionError e) {
					continue;
				} Assert.fail("Shadow : (" + x + "," + y + ")");
			}
	}
	
	@Test
	public void setAlive_valid() {
		edit = new EditableScreenContract(new EditableScreenImpl());
		engine = new EngineContract(new EngineImpl(true));
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		
		edit.init(3, 3);
		for(int i = 0; i < 3; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		guards.add(new Point(0,2));
		for(Cell c1 : valid)
			for(Cell c2 : platform) {
				edit.setNature(2, 2, c1);
				edit.setNature(2, 1, c2);
				engine.init(edit, new Point(1,2), guards, treasures);
				EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi(), engine.getGuards().get(0).getEnvi()};
				engine.getPlayer().goRight();
				for(EnvironmentService e : envi) {
					e.setCellContent(1, 2, new Paire(null, null));
					e.setCellContent(2, 2, new Paire(engine.getPlayer(), null));
				}
				engine.getShadow().setAlive(true);
				if(!(engine.getShadow().isAlive()))
					Assert.fail("Shadow.isAlive False -> True Failed");
				engine.getShadow().setAlive(false);
				if(engine.getShadow().isAlive())
					Assert.fail("Shadow.isAlive True -> False Failed");
			}
	}

	@Test
	public void climbL_valid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(5,3);
		for(int i = 0; i < 5; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(2,2);
		treasures.add(new Point(0,2));
		treasures.add(new Point(4,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(1, 1, c1);
					edit.setNature(1, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi()};
					engine.getPlayer().goRight();
					engine.getPlayer().goRight();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(4, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().setAlive(true);
					engine.getShadow().goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getShadow(), null));
					}
					engine.getShadow().climbLeft();
					if(!(engine.getShadow().getWidth() == 1 && engine.getShadow().getHeight() == 2))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbL_unvalid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(5,3);
		for(int i = 0; i < 5; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(2,2);
		treasures.add(new Point(0,2));
		treasures.add(new Point(4,2));
		for(Cell c1 : platform)
			for(Cell c2 : unvalid)
				for(Cell c3 : valid) {
					edit.setNature(1, 1, c1);
					edit.setNature(1, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi()};
					engine.getPlayer().goRight();
					engine.getPlayer().goRight();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(4, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().setAlive(true);
					engine.getShadow().goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getShadow(), null));
					}
					engine.getShadow().climbLeft();
					if(!(engine.getShadow().getWidth() == 2 && engine.getShadow().getHeight() == 1))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbL_blocked() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(5,3);
		for(int i = 0; i < 5; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(2,2);
		treasures.add(new Point(0,2));
		treasures.add(new Point(4,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(1, 1, c1);
					edit.setNature(1, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi()};
					engine.getPlayer().goLeft();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(1, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().setAlive(true);
					engine.getShadow().goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getShadow(), null));
					}
					engine.getShadow().climbLeft();
					if(!(engine.getShadow().getWidth() == 2 && engine.getShadow().getHeight() == 1))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1 + " with Player : (1,2)");
					engine.getPlayer().goRight();
					for(EnvironmentService e : envi) {
						e.setCellContent(1, 2, new Paire(null, null));
						e.setCellContent(2, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().climbLeft();
					if(!(engine.getShadow().getWidth() == 2 && engine.getShadow().getHeight() == 1))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1 + " with Player : (2,2)");
				}
	}
	
	@Test
	public void climbR_valid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(5,3);
		for(int i = 0; i < 5; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(2,2);
		treasures.add(new Point(0,2));
		treasures.add(new Point(4,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(3, 1, c1);
					edit.setNature(3, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi()};
					engine.getPlayer().goLeft();
					engine.getPlayer().goLeft();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(0, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().setAlive(true);
					engine.getShadow().goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getShadow(), null));
					}
					engine.getShadow().climbRight();
					if(!(engine.getShadow().getWidth() == 3 && engine.getShadow().getHeight() == 2))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbR_unvalid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(5,3);
		for(int i = 0; i < 5; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(2,2);
		treasures.add(new Point(0,2));
		treasures.add(new Point(4,2));
		for(Cell c1 : platform)
			for(Cell c2 : unvalid)
				for(Cell c3 : valid) {
					edit.setNature(3, 1, c1);
					edit.setNature(3, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi()};
					engine.getPlayer().goLeft();
					engine.getPlayer().goLeft();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(0, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().setAlive(true);
					engine.getShadow().goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getShadow(), null));
					}
					engine.getShadow().climbRight();
					if(!(engine.getShadow().getWidth() == 2 && engine.getShadow().getHeight() == 1))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbR_blocked() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(5,3);
		for(int i = 0; i < 5; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(2,2);
		treasures.add(new Point(0,2));
		treasures.add(new Point(4,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(3, 1, c1);
					edit.setNature(3, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi()};
					engine.getPlayer().goRight();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(3, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().setAlive(true);
					engine.getShadow().goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getShadow(), null));
					}
					engine.getShadow().climbRight();
					if(!(engine.getShadow().getWidth() == 2 && engine.getShadow().getHeight() == 1))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1 + " with Player : (1,2)");
					engine.getPlayer().goLeft();
					for(EnvironmentService e : envi) {
						e.setCellContent(1, 2, new Paire(null, null));
						e.setCellContent(2, 2, new Paire(engine.getPlayer(), null));
						e.dig(2, 1);
					}
					engine.getShadow().climbRight();
					if(!(engine.getShadow().getWidth() == 2 && engine.getShadow().getHeight() == 1))
						Assert.fail("Shadow : (" + engine.getShadow().getWidth() + "," + engine.getShadow().getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1 + " with Player : (2,2)");
				}
	}
}
