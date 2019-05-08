package test;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.PreconditionError;
import impl.EditableScreenImpl;
import impl.EngineImpl;
import services.Cell;
import services.EditableScreenService;
import services.EngineService;
import services.Command;
import services.Status;

public class EngineTest extends MyTest{

	private EngineService engine = new EngineContract(new EngineImpl(true));
	private EditableScreenService edit = new EditableScreenContract(new EditableScreenImpl());
	private ArrayList<Point> guards = new ArrayList<>();
	private ArrayList<Point> treasures = new ArrayList<>();
	
	public EngineTest() {
		NameClassTest = "Engine";
	}
	
	@Test
	public void init_valide() {
		edit.init(3, 2);
		edit.setNature(0, 0, Cell.MTL);
		edit.setNature(1, 0, Cell.MTL);
		edit.setNature(2, 0, Cell.MTL);
		treasures.add(new Point(1,1));
		guards.add(new Point(2,1));
		engine.init(edit, new Point(0,1), guards, treasures);
		treasures.clear();guards.clear();
	}
	
	@Test
	public void init_player_unbound() {
		edit.init(1,1);
		Point[] players = 
			{new Point(-1,0),
			 new Point(0,-1),
			 new Point(1,0),
			 new Point(0,1)
		};
		
		for(Point player : players) {
			try {
				engine.init(edit, player, guards, treasures);
				} catch(PreconditionError e) {
					message("init_player_unbound", e);
					continue;
			} Assert.fail();
		}
	}
	
	@Test
	public void init_guard_unbound() {
		edit.init(1,1);
		Point player = new Point(0,0);
		Point[] guards_pos = 
			{new Point(-1,0),
			 new Point(0,-1),
			 new Point(1,0),
			 new Point(0,1)
		};
		for(Point guard : guards_pos) {
			guards.add(guard);
			try {
				engine.init(edit, player, guards, treasures);
			}catch(PreconditionError e) {
				message("init_guard_unbound", e);
				guards.clear();
				continue;
			} Assert.fail();
		}
	}
	
	@Test
	public void init_treasure_unbound() {
		edit.init(1,1);
		Point player = new Point(0,0);
		Point[] treasures_pos = 
			{new Point(-1,0),
			 new Point(0,-1),
			 new Point(1,0),
			 new Point(0,1)
		};
		for(Point treasure : treasures_pos) {
			treasures.add(treasure);
			try {
				engine.init(edit, player, guards, treasures);
			}catch(PreconditionError e) {
				message("init_treasure_unbound", e);
				treasures.clear();
				continue;
			} Assert.fail();
		}
	}
	
	@Test 
	public void setCommand_valide() {
		init_valide();
		for(Command m : Command.values()) {
			engine.setCommand(m);
		}
	}
	
	@Test
	public void step_last_item() {
		edit.init(4, 2);
		for(int i = 0; i < 4; i++)
			edit.setNature(i, 0, Cell.MTL);
		treasures.add(new Point(3,1));
		guards.add(new Point(0,1));
		Point player = new Point(2,1);
		engine.init(edit, player, guards, treasures);
		treasures.clear();guards.clear();
		engine.setCommand(Command.RIGHT);
		engine.step();
		engine.setCommand(Command.LEFT);
		engine.step();
	}
	
	@Test
	public void step_guard_treasure() {
		edit.init(4, 3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		treasures.add(new Point(1,2));
		treasures.add(new Point(3,2));
		guards.add(new Point(2,2));
		Point player = new Point(0,2);
		engine.init(edit, player, guards, treasures);
		treasures.clear();guards.clear();
		engine.setCommand(Command.DOWN);
		engine.step();
		engine.setCommand(Command.DIGR);
		engine.step();
		engine.setCommand(Command.RIGHT);
		engine.step();
		engine.step();
		engine.setCommand(Command.LEFT);
		engine.step();
		engine.step();
	}
	
	@Test
	public void step_player_death() {
		edit.init(3, 2);
		edit.setNature(0, 0, Cell.MTL);
		edit.setNature(1, 0, Cell.MTL);
		edit.setNature(2, 0, Cell.MTL);
		guards.add(new Point(1,1));
		treasures.add(new Point(2,1));
		engine.init(edit, new Point(0,1), guards, treasures);
		treasures.clear();guards.clear();
		engine.setCommand(Command.DOWN);
		engine.step();
		if(engine.getStatus() == Status.PLAYING)
			Assert.fail();
	}
	
	@Test 
	public void the_mount() {
		edit.init(3,3);
		for(int i = 0; i < 3; i++)
			edit.setNature(i, 0, Cell.MTL);
		edit.setNature(2, 1, Cell.MTL);
		guards.add(new Point(0,1));
		treasures.add(new Point(2,2));
		Point player = new Point(0,2);
		engine.init(edit, player, guards, treasures);
		treasures.clear();guards.clear();
		engine.setCommand(Command.RIGHT);
		for(int i = 0; i < 5; i++)
			engine.step();
		if(!(engine.getStatus() == Status.WIN))
			Assert.fail();
	}
	
	
}
