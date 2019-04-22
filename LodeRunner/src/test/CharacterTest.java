package test;

import org.junit.Test;

import contracts.*;
import impl.*;
import services.*;

public class CharacterTest {
	Cell[] VALID = {Cell.EMP,Cell.LAD,Cell.HDR, Cell.HOL};
	Cell[] ALL = {Cell.EMP, Cell.LAD, Cell.HDR, Cell.MTL, Cell.PLT, Cell.HOL};
	
	@Test
	public void testGoLeft() {
		EditableScreenContract edit = new EditableScreenContract(new EditableScreenImpl());
		EnvironmentContract envi = new EnvironmentContract(new EnvironmentImpl());
		CharacterContract character = new CharacterContract(new CharacterImpl());
		
		for(Cell start: VALID) {
			edit.init("Environments/Test/Movement/EMP_EMP_EMP_" + start + "_EMP");
			envi.init(edit);
			character.init(envi, 0, 1);
			character.goLeft();
		}
		
		for(Cell start : VALID)
			for(Cell up : ALL)
				for(Cell down : ALL)
					for(Cell left : ALL)
						for(Cell right : ALL) {
							edit.init("Environments/Test/Movement/" + start + "_" + up + "_" + down + "_" + left + "_" + right);
							envi.init(edit);
							character.init(envi, 1, 2);
							character.goLeft();
						}
	}
	
	@Test
	public void testGoRight() {
		EditableScreenContract edit = new EditableScreenContract(new EditableScreenImpl());
		EnvironmentContract envi = new EnvironmentContract(new EnvironmentImpl());
		CharacterContract character = new CharacterContract(new CharacterImpl());
		
		for(Cell start: VALID) {
			edit.init("Environments/Test/Movement/EMP_EMP_EMP_EMP_" + start);
			envi.init(edit);
			character.init(envi, 2, 1);
			character.goLeft();
		}
		
		for(Cell start : VALID)
			for(Cell up : ALL)
				for(Cell down : ALL)
					for(Cell left : ALL)
						for(Cell right : ALL) {
							edit.init("Environments/Test/Movement/" + start + "_" + up + "_" + down + "_" + left + "_" + right);
							envi.init(edit);
							character.init(envi, 1, 2);
							character.goRight();
						}
	}
	
	@Test
	public void testGoUp() {
		EditableScreenContract edit = new EditableScreenContract(new EditableScreenImpl());
		EnvironmentContract envi = new EnvironmentContract(new EnvironmentImpl());
		CharacterContract character = new CharacterContract(new CharacterImpl());
		
		for(Cell start: VALID) {
			edit.init("Environments/Test/Movement/EMP_" + start + "_EMP_EMP_EMP");
			envi.init(edit);
			character.init(envi, 2, 1);
			character.goLeft();
		}
		
		for(Cell start : VALID)
			for(Cell up : ALL)
				for(Cell down : ALL)
					for(Cell left : ALL)
						for(Cell right : ALL) {
							edit.init("Environments/Test/Movement/" + start + "_" + up + "_" + down + "_" + left + "_" + right);
							envi.init(edit);
							character.init(envi, 1, 2);
							character.goUp();
						}
	}
	
	@Test
	public void testGoDown() {
		EditableScreenContract edit = new EditableScreenContract(new EditableScreenImpl());
		EnvironmentContract envi = new EnvironmentContract(new EnvironmentImpl());
		CharacterContract character = new CharacterContract(new CharacterImpl());
		
		for(Cell start: VALID) {
			edit.init("Environments/Test/Movement/EMP_EMP_" + start + "_EMP_EMP");
			envi.init(edit);
			character.init(envi, 2, 1);
			character.goLeft();
		}
		
		for(Cell start : VALID)
			for(Cell up : ALL)
				for(Cell down : ALL)
					for(Cell left : ALL)
						for(Cell right : ALL) {
							edit.init("Environments/Test/Movement/" + start + "_" + up + "_" + down + "_" + left + "_" + right);
							envi.init(edit);
							character.init(envi, 1, 2);
							character.goDown();
						}
	}
}
