package test;

import org.junit.Assert;

import org.junit.Test;

import contracts.CharacterContract;
import contracts.EditableScreenContract;
import contracts.InvariantError;
import contracts.PreconditionError;
import impl.CharacterImpl;
import impl.EditableScreenImpl;
import services.Cell;
import services.CharacterService;
import services.EditableScreenService;

public class CharacterTest extends MyTest{

	private CharacterService character = new CharacterContract(new CharacterImpl());
	private EditableScreenService edit = new EditableScreenContract(new EditableScreenImpl());
	
	private Cell[] valide = {Cell.EMP, Cell.HDR, Cell.LAD, Cell.HOL};
	private Cell[] unvalide = {Cell.MTL, Cell.PLT};
	private Cell[] platform = {Cell.MTL, Cell.PLT, Cell.LAD};
	private Cell[] grip = {Cell.LAD, Cell.HDR};
	
	public CharacterTest() {
		// TODO Auto-generated constructor stub
		NameClassTest = "Character";
	}
	
	@Test
	public void init_valide() {
		edit.init(1,1);
		character.init(edit, 0, 0);
	}
	
	@Test
	public void init_null_service() {
		try {
			character.init(null,0,0);
		} catch(PreconditionError e) {
			message("init_null_service", e);
			return;
		} Assert.fail();
	}
	
	@Test
	public void init_negative_width() {
		edit.init(1,1);
		try {
			character.init(edit, -1, 0);
		} catch(PreconditionError e) {
			message("init_negative_width", e);
			return;
		} Assert.fail();
	}
	
	@Test 
	public void init_negative_height() {
		edit.init(1,1);
		try {
			character.init(edit,  0,  -1);
		} catch(PreconditionError e) {
			message("init_negative_height", e);
			return;
		} Assert.fail();
	}
	
	@Test
	public void init_unbound_width() {
		edit.init(1,1);
		try {
			character.init(edit, edit.getWidth(), 0);
		} catch(PreconditionError e) {
			message("init_unbound_width", e);
			return;
		} Assert.fail();
	}
	
	@Test
	public void init_unbound_height() {
		edit.init(1,1);
		try {
			character.init(edit,  0, edit.getHeight());
		} catch(PreconditionError e) {
			message("init_unbound_height", e);
			return;
		} Assert.fail();
	}
	
	@Test
	public void invariant_valide_emplacement() {
		edit.init(1,1);
		for(Cell c : valide) {
			edit.setNature(0, 0, c);
			character.init(edit, 0, 0);
		}
	}
	
	@Test
	public void invariant_unvalide_emplacement() {
		edit.init(1,1);
		for(Cell c : unvalide) {
			edit.setNature(0, 0, c);
			try {
				character.init(edit, 0, 0);
			} catch(InvariantError e) {
				message("invariant_unvalide_emplacement(" + c + ")", e);
				continue;
			} Assert.fail();
		}
	}
	
	
	@Test 
	public void goLeft_to_unvalide() {
		edit.init(2, 2);
		edit.setNature(0, 0, Cell.EMP);
		edit.setNature(1, 0, Cell.EMP);
		for(Cell go : valide)
			for(Cell to : unvalide) {
				edit.setNature(1, 1, go);
				edit.setNature(0, 1, to);
				character.init(edit, 1, 1);
				character.goLeft();
				character.goRight();
			}
	}
	
	@Test
	public void goRight_to_unvalide() {
		edit.init(2, 2);
		edit.setNature(0, 0, Cell.EMP);
		edit.setNature(1, 0, Cell.EMP);
		for(Cell go : valide)
			for(Cell to : unvalide) {
				edit.setNature(0, 1, go);
				edit.setNature(1, 1, to);
				character.init(edit, 0, 1);
				character.goRight();
				character.goLeft();
			}
	}

	@Test
	public void transition_LR_on_platform() {
		edit.init(2, 2);
		for(Cell on1 : platform)
			for(Cell on2 : platform) {
				edit.setNature(0, 0, on1);
				edit.setNature(1, 0, on2);
				for(Cell go : valide)
					for(Cell to : valide) {
						edit.setNature(1, 1, go);
						edit.setNature(0, 1, to);
						character.init(edit, 1, 1);
						character.goLeft();
						character.goRight();
						character.goLeft();
					}
			}
	}
	
	@Test
	public void transition_LR_on_grip() {
		edit.init(2, 2);
		edit.setNature(0, 0, Cell.EMP);
		edit.setNature(1, 0, Cell.EMP);
		for(Cell go : grip)
			for(Cell to : grip) {
				edit.setNature(1, 1, go);
				edit.setNature(0, 1, to);
				character.init(edit, 1, 1);
				character.goLeft();
				character.goRight();
				character.goLeft();
			}
	}

	
	
	@Test
	public void transition_UD_valide() {
		edit.init(1, 3);
		for(Cell plat : platform) {
				edit.setNature(0, 0, plat);
				for(Cell go : valide)
					for(Cell to : valide) {
						edit.setNature(0, 1, go);
						edit.setNature(0, 2, to);
						character.init(edit, 0, 1);
						character.goUp();
						character.goDown();
					}
			}
	}
	
	@Test
	public void goUp_to_unvalide() {
		edit.init(1,4);
		edit.setNature(0, 0, Cell.MTL);
		for(Cell plat : platform) {
			edit.setNature(0, 1, plat);
			for(Cell go : valide) {
				for(Cell to : unvalide) {
					edit.setNature(0, 2, go);
					edit.setNature(0, 3, to);
					character.init(edit, 0, 2);
					character.goUp();
				}
				for(int i = 0; i < 3; i++)
					character.goDown();
			}
		}
	}
}
