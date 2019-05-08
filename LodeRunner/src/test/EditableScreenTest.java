package test;

import org.junit.Test;

import contracts.EditableScreenContract;
import impl.EditableScreenImpl;
import services.Cell;
import services.EditableScreenService;

public class EditableScreenTest {
	
	private EditableScreenService edit = new EditableScreenContract(new EditableScreenImpl());

	@Test
	public void setNature() {
		edit.init(1, 1);
		for(Cell c : Cell.values())
			edit.setNature(0, 0, c);
	}
	
	@Test
	public void dig_fill_all() {
		for(Cell i : Cell.values())
			for(Cell j : Cell.values()) {
				edit = new EditableScreenContract(new EditableScreenImpl());
				edit.init(2, 1);
				edit.setNature(0, 0, i);
				edit.setNature(1, 0, j);
				edit.dig(0, 0);
				edit.fill(0, 0);
				edit.dig(0, 0);
			}
	}
}
