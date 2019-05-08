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
}
