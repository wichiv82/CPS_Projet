package test;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import contracts.CharacterContract;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.GuardContract;
import contracts.PreconditionError;
import contracts.ShadowContract;
import impl.CharacterImpl;
import impl.EditableScreenImpl;
import impl.EngineImpl;
import impl.GuardImpl;
import impl.ShadowImpl;
import services.Cell;
import services.CharacterService;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.Paire;

public class GuardTest extends MyTest{
	@Test
	public void init_valid() {
		edit = new EditableScreenContract(new EditableScreenImpl());
		guard = new GuardContract(new GuardImpl());
		edit.init(1,1);
		guard.init(edit, 0, 0);		
	}
	
	@Test
	public void init_unbound() {
		edit = new EditableScreenContract(new EditableScreenImpl());
		guard = new GuardContract(new GuardImpl());
		edit.init(1, 1);
		for(int x = -1; x < 2; x = x + 2)
			for(int y = -1; y < 2; y = y + 2) {
				try {
					guard.init(edit, x, y);
				} catch(PreconditionError e) {
					continue;
				} Assert.fail("Guard : (" + x + "," + y + ")");
			}
	}

	@Test
	public void climbL_valid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(0,2);
		guards.add(new Point(2,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(1, 1, c1);
					edit.setNature(1, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi(), engine.getGuards().get(0).getEnvi()};
					for(EnvironmentService e : envi)
						e.dig(2, 1);
					engine.getGuards().get(0).goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getGuards().get(0), null));
					}
					engine.getGuards().get(0).climbLeft();
					if(!(engine.getGuards().get(0).getWidth() == 1 && engine.getGuards().get(0).getHeight() == 2))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbL_unvalid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(0,2);
		guards.add(new Point(2,2));
		for(Cell c1 : platform)
			for(Cell c2 : unvalid)
				for(Cell c3 : valid) {
					edit.setNature(1, 1, c1);
					edit.setNature(1, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi(), engine.getGuards().get(0).getEnvi()};
					for(EnvironmentService e : envi)
						e.dig(2, 1);
					engine.getGuards().get(0).goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getGuards().get(0), null));
					}
					engine.getGuards().get(0).climbLeft();
					if(!(engine.getGuards().get(0).getWidth() == 2 && engine.getGuards().get(0).getHeight() == 1))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbL_blocked() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(0,2);
		guards.add(new Point(2,2));
		guards.add(new Point(1,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(1, 1, c1);
					edit.setNature(1, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(),
							engine.getShadow().getEnvi(), engine.getGuards().get(0).getEnvi(),
							engine.getGuards().get(1).getEnvi()};
					for(EnvironmentService e : envi)
						e.dig(2, 1);
					engine.getGuards().get(0).goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getGuards().get(0), null));
					}
					engine.getGuards().get(0).climbLeft();
					if(!(engine.getGuards().get(0).getWidth() == 2 && engine.getGuards().get(0).getHeight() == 1))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Guard under " + c3 + " to " + c2 + " on " + c1 + " with Guard : (1,2)");
					engine.getGuards().get(1).goRight();
					for(EnvironmentService e : envi) {
						e.setCellContent(1, 2, new Paire(null, null));
						e.setCellContent(2, 2, new Paire(engine.getGuards().get(1), null));
					}
					engine.getGuards().get(0).climbLeft();
					if(!(engine.getGuards().get(0).getWidth() == 2 && engine.getGuards().get(0).getHeight() == 1))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Guard under " + c3 + " to " + c2 + " on " + c1 + " with Guard : (2,2)");
				}
	}
	
	@Test
	public void climbR_valid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(0,2);
		guards.add(new Point(2,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(3, 1, c1);
					edit.setNature(3, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi(), engine.getGuards().get(0).getEnvi()};
					for(EnvironmentService e : envi)
						e.dig(2, 1);
					engine.getGuards().get(0).goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getGuards().get(0), null));
					}
					engine.getGuards().get(0).climbRight();
					if(!(engine.getGuards().get(0).getWidth() == 3 && engine.getGuards().get(0).getHeight() == 2))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbR_unvalid() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(0,2);
		guards.add(new Point(2,2));
		for(Cell c1 : platform)
			for(Cell c2 : unvalid)
				for(Cell c3 : valid) {
					edit.setNature(3, 1, c1);
					edit.setNature(3, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(), engine.getShadow().getEnvi(), engine.getGuards().get(0).getEnvi()};
					for(EnvironmentService e : envi)
						e.dig(2, 1);
					engine.getGuards().get(0).goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getGuards().get(0), null));
					}
					engine.getGuards().get(0).climbRight();
					if(!(engine.getGuards().get(0).getWidth() == 2 && engine.getGuards().get(0).getHeight() == 1))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Shadow under " + c3 + " to " + c2 + " on " + c1);
				}
	}
	
	@Test
	public void climbR_blocked() {
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		edit = new EditableScreenContract(new EditableScreenImpl());
		edit.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		Point player = new Point(0,2);
		guards.add(new Point(2,2));
		guards.add(new Point(3,2));
		for(Cell c1 : platform)
			for(Cell c2 : valid)
				for(Cell c3 : valid) {
					edit.setNature(3, 1, c1);
					edit.setNature(3, 2, c2);
					edit.setNature(2, 2, c3);
					engine = new EngineContract(new EngineImpl(true));
					engine.init(edit, player, guards, treasures);
					EnvironmentService[] envi = {engine.getEnvi(), engine.getPlayer().getEnvi(),
							engine.getShadow().getEnvi(), engine.getGuards().get(0).getEnvi(),
							engine.getGuards().get(1).getEnvi()};
					for(EnvironmentService e : envi)
						e.dig(2, 1);
					engine.getGuards().get(0).goDown();
					for(EnvironmentService e : envi) {
						e.setCellContent(2, 2, new Paire(null, null));
						e.setCellContent(2, 1, new Paire(engine.getGuards().get(0), null));
					}
					engine.getGuards().get(0).climbRight();
					if(!(engine.getGuards().get(0).getWidth() == 2 && engine.getGuards().get(0).getHeight() == 1))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Guard under " + c3 + " to " + c2 + " on " + c1 + " with Guard : (3,2)");
					engine.getGuards().get(1).goLeft();
					for(EnvironmentService e : envi) {
						e.setCellContent(3, 2, new Paire(null, null));
						e.setCellContent(2, 2, new Paire(engine.getGuards().get(1), null));
					}
					engine.getGuards().get(0).climbRight();
					if(!(engine.getGuards().get(0).getWidth() == 2 && engine.getGuards().get(0).getHeight() == 1))
						Assert.fail("Guard : (" + engine.getGuards().get(0).getWidth() + "," + engine.getGuards().get(0).getHeight() + ")"
								+ " for Guard under " + c3 + " to " + c2 + " on " + c1 + " with Guard : (2,2)");
				}
	}
}
