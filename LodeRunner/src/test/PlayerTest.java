package test;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Assert;

import org.junit.Test;

import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.InvariantError;
import contracts.PlayerContract;
import contracts.PreconditionError;
import impl.EditableScreenImpl;
import impl.EngineImpl;
import impl.PlayerImpl;
import services.Cell;
import services.Command;
import services.EditableScreenService;
import services.EngineService;
import services.PlayerService;

public class PlayerTest extends MyTest{
	
	
	private EngineService engine = new EngineContract(new EngineImpl(true));
	private EditableScreenService edit = new EditableScreenContract(new EditableScreenImpl());
	
	private EngineService engine_clone = new EngineContract(new EngineImpl(true));
	private EditableScreenService edit_clone = new EditableScreenContract(new EditableScreenImpl());
	
	private Point player, player_clone;
	private ArrayList<Point> guards = new ArrayList<>();
	private ArrayList<Point> treasures = new ArrayList<>();
	
	private Cell[] valide = {Cell.EMP, Cell.HDR, Cell.LAD};
	
	public PlayerTest() {
		NameClassTest = "Player";
	}
	
	@Test
	public void command_left() {
		edit.init(3, 2);
		edit_clone.init(3,2);
		for(int i = 0; i < 3; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit_clone.setNature(i, 0, Cell.MTL);
		}
		
		treasures.add(new Point(2,1));
		player = new Point(1,1);
		player_clone = new Point(1,1);
		for(Cell c1 : valide)
			for(Cell c2 : Cell.values()) {
				edit.setNature(1, 1, c1);
				edit.setNature(0, 1, c2);
				edit_clone.setNature(1, 1, c1);
				edit_clone.setNature(0, 1, c2);
				engine.init(edit, player, guards, treasures);
				engine_clone.init(edit_clone, player_clone, guards, treasures);
				engine.setCommand(Command.LEFT);
				engine_clone.getPlayer().goLeft();
				engine.step();
				if(!(engine.getPlayer().getWidth() == engine_clone.getPlayer().getWidth()
					&& engine.getPlayer().getHeight() == engine_clone.getPlayer().getHeight()))
					Assert.fail();
			}
	}
	
	@Test
	public void command_right() {
		edit.init(3, 2);
		edit_clone.init(3,2);
		for(int i = 0; i < 3; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit_clone.setNature(i, 0, Cell.MTL);
		}
		
		treasures.add(new Point(0,1));
		player = new Point(1,1);
		player_clone = new Point(1,1);
		for(Cell c1 : valide)
			for(Cell c2 : Cell.values()) {
				edit.setNature(1, 1, c1);
				edit.setNature(2, 1, c2);
				edit_clone.setNature(1, 1, c1);
				edit_clone.setNature(2, 1, c2);
				engine.init(edit, player, guards, treasures);
				engine_clone.init(edit_clone, player_clone, guards, treasures);
				engine.setCommand(Command.RIGHT);
				engine_clone.getPlayer().goRight();
				engine.step();
				if(!(engine.getPlayer().getWidth() == engine_clone.getPlayer().getWidth()
					&& engine.getPlayer().getHeight() == engine_clone.getPlayer().getHeight()))
					Assert.fail();
			}
	}
	
	@Test
	public void command_up() {
		edit.init(3, 3);
		edit_clone.init(3,3);
		for(int i = 0; i < 3; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit_clone.setNature(i, 0, Cell.MTL);
		}
		
		treasures.add(new Point(0,1));
		player = new Point(1,1);
		player_clone = new Point(1,1);
		for(Cell c1 : valide)
			for(Cell c2 : Cell.values()) {
				edit.setNature(1, 1, c1);
				edit.setNature(1, 2, c2);
				edit_clone.setNature(1, 1, c1);
				edit_clone.setNature(1, 2, c2);
				engine.init(edit, player, guards, treasures);
				engine_clone.init(edit_clone, player_clone, guards, treasures);
				engine.setCommand(Command.UP);
				engine_clone.getPlayer().goUp();
				engine.step();
				if(!(engine.getPlayer().getWidth() == engine_clone.getPlayer().getWidth()
					&& engine.getPlayer().getHeight() == engine_clone.getPlayer().getHeight()))
					Assert.fail();
			}
	}
	
	@Test
	public void command_down() {
		edit.init(3, 3);
		edit_clone.init(3,3);
		for(int i = 0; i < 3; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit_clone.setNature(i, 0, Cell.MTL);
		}
		
		treasures.add(new Point(0,1));
		player = new Point(1,2);
		player_clone = new Point(1,2);
		for(Cell c1 : valide)
			for(Cell c2 : Cell.values()) {
				edit.setNature(1, 2, c1);
				edit.setNature(1, 1, c2);
				edit_clone.setNature(1, 2, c1);
				edit_clone.setNature(1, 1, c2);
				engine.init(edit, player, guards, treasures);
				engine_clone.init(edit_clone, player_clone, guards, treasures);
				engine.setCommand(Command.DOWN);
				engine_clone.getPlayer().goDown();
				engine.step();
				if(!(engine.getPlayer().getWidth() == engine_clone.getPlayer().getWidth()
					&& engine.getPlayer().getHeight() == engine_clone.getPlayer().getHeight()))
					Assert.fail();
			}
	}
	
	@Test
	public void command_neutral() {
		edit.init(3, 2);
		edit_clone.init(3,2);
		for(int i = 0; i < 3; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit_clone.setNature(i, 0, Cell.MTL);
		}
		
		treasures.add(new Point(0,1));
		player = new Point(1,1);
		player_clone = new Point(1,1);
		for(Cell c1 : valide)
			for(Cell c2 : Cell.values()) {
				edit.setNature(1, 1, c1);
				edit.setNature(2, 1, c2);
				edit_clone.setNature(1, 1, c1);
				edit_clone.setNature(2, 1, c2);
				engine.init(edit, player, guards, treasures);
				engine_clone.init(edit_clone, player_clone, guards, treasures);
				engine.setCommand(Command.NEUTRAL);
				engine.step();
				if(!(engine.getPlayer().getWidth() == engine_clone.getPlayer().getWidth()
					&& engine.getPlayer().getHeight() == engine_clone.getPlayer().getHeight()))
					Assert.fail();
			}
	}
	
	@Test
	public void command_digL() {
		edit.init(4, 3);
		edit_clone.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
			edit_clone.setNature(i, 0, Cell.MTL);
			edit_clone.setNature(i, 1, Cell.PLT);
		}
		
		treasures.add(new Point(3,2));
		player = new Point(1,2);
		player_clone = new Point(1,2);
		for(Cell c : Cell.values()) {
			edit.setNature(0, 1, c);
			edit_clone.setNature(0, 1, c);
			engine.init(edit, player, guards, treasures);
			engine_clone.init(edit_clone, player_clone, guards, treasures);
			engine.setCommand(Command.DIGL);
			engine_clone.getEnvi().dig(0, 1);;
			engine.step();
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(!(engine.getEnvi().cellNature(i, j) == engine_clone.getEnvi().cellNature(i, j)))
						Assert.fail();
		}
	}
	
	@Test
	public void command_digR() {
		edit.init(4, 3);
		edit_clone.init(4,3);
		for(int i = 0; i < 4; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
			edit_clone.setNature(i, 0, Cell.MTL);
			edit_clone.setNature(i, 1, Cell.PLT);
		}
		
		treasures.add(new Point(3,2));
		player = new Point(1,2);
		player_clone = new Point(1,2);
		for(Cell c : Cell.values()) {
			edit.setNature(2, 1, c);
			edit_clone.setNature(2, 1, c);
			engine.init(edit, player, guards, treasures);
			engine_clone.init(edit_clone, player_clone, guards, treasures);
			engine.setCommand(Command.DIGR);
			engine_clone.getEnvi().dig(2, 1);;
			engine.step();
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(!(engine.getEnvi().cellNature(i, j) == engine_clone.getEnvi().cellNature(i, j)))
						Assert.fail();
		}
	}
	
	
	
}
