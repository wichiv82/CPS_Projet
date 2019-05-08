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
import impl.CharacterImpl;
import impl.EditableScreenImpl;
import impl.EngineImpl;
import impl.GuardImpl;
import services.Cell;
import services.CharacterService;
import services.EditableScreenService;
import services.EngineService;
import services.GuardService;

public class GuardTest extends MyTest{
	
	private GuardService guard = new GuardContract(new GuardImpl());
	private CharacterService character = new CharacterContract(new CharacterImpl());
	private EditableScreenService edit = new EditableScreenContract(new EditableScreenImpl());
	private EngineService engine = new EngineContract(new EngineImpl(true));
	private ArrayList<Point> guards = new ArrayList<>();
	private ArrayList<Point> treasures = new ArrayList<>();
	
	public GuardTest() {
		NameClassTest = "Guard";
	}
	
	
	@Test
	public void init_valide(){
		edit.init(2, 1);
		character.init(edit, 0, 0);
		guard.init(edit, 1, 0, 0, character);
	}
	
	@Test
	public void init_null_target() {
		edit.init(1,1);
		try {
			guard.init(edit, 0, 0, 0, null);
		} catch(PreconditionError e) {
			message("init_null_target", e);
			return;
		} Assert.fail();
	}
	
	@Test
	public void init_unvalide_target() {
		edit.init(1,1);
		character.init(edit,  0, 0);
		try {
			guard.init(edit, 0, 0, 0, character);
		} catch(PreconditionError e) {
			message("init_unvalide_target", e);
			return;
		} Assert.fail();
	}
	
	
	@Test
	public void respawn_valide() {
		edit.init(3,2);
		for(int i = 0; i < 3; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.EMP);
		}
		guards.add(new Point(0,1));
		engine.init(edit, new Point(2,1), guards, treasures);
		engine.getGuards().get(0).goRight();
		if(!(engine.getGuards().get(0).getWidth() == 1 && engine.getGuards().get(0).getHeight() == 1))
			Assert.fail();
		engine.getGuards().get(0).respawn();
		if(!(engine.getGuards().get(0).getWidth() == 0 && engine.getGuards().get(0).getHeight() == 1))
			Assert.fail();
	}
	
	
	/* difficile a tester mais ceci m'a permis de corriger Engine.step pour le respawn */
	@Test
	public void respawn_failed() {
		edit.init(4,2);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.EMP);
		}
		guards.add(new Point(1,1));
		guards.add(new Point(0,1));
		engine.init(edit, new Point(3,1), guards, treasures);
		engine.getGuards().get(0).goRight();
		if(!(engine.getGuards().get(0).getWidth() == 2 && engine.getGuards().get(0).getHeight() == 1))
			Assert.fail();
		
		engine.getGuards().get(1).goRight();
		if(!(engine.getGuards().get(1).getWidth() == 1 && engine.getGuards().get(1).getHeight() == 1))
			Assert.fail();
		
		engine.getGuards().get(0).respawn();
		if(!(engine.getGuards().get(0).getWidth() == 1 && engine.getGuards().get(0).getHeight() == 1))
			Assert.fail();
		if(!(engine.getGuards().get(1).getWidth() == 1 && engine.getGuards().get(1).getHeight() == 1))
			Assert.fail();
//		Assert.fail();
	}
	
	@Test
	public void climb_valide() {
		edit.init(7,3);
		for(int i = 0; i < 7; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		edit.setNature(0,1,Cell.EMP);
		guards.add(new Point(3,2));
		guards.add(new Point(0,1));
		Point player = new Point(5,2);
		for(Cell c1 : Cell.values())
			for(Cell c2 : Cell.values()) {
				edit.setNature(2, 1, c1);
				edit.setNature(2, 2, c2);
				engine = new EngineContract(new EngineImpl(true));
				engine.init(edit, player, guards, treasures);
				engine.getEnvi().dig(3, 1);
				engine.getGuards().get(0).goDown();
				engine.getGuards().get(0).climbLeft();
				edit.setNature(2, 1, Cell.PLT);
				edit.setNature(2, 2, Cell.EMP);
				
				edit.setNature(4, 1, c1);
				edit.setNature(4, 2, c2);
				engine = new EngineContract(new EngineImpl(true));
				engine.init(edit, player, guards, treasures);
				engine.getEnvi().dig(3, 1);
				engine.getGuards().get(0).goDown();
				engine.getGuards().get(0).climbRight();
				edit.setNature(4, 1, Cell.PLT);
				edit.setNature(4, 2, Cell.EMP);
			}
	}
}
