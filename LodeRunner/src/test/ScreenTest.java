package test;

import org.junit.Assert;
import org.junit.Test;

import contracts.EditableScreenContract;
import contracts.PreconditionError;
import contracts.ScreenContract;
import contracts.ShadowContract;
import impl.EditableScreenImpl;
import impl.ScreenImpl;
import impl.ShadowImpl;
import services.Cell;
import services.ScreenService;

public class ScreenTest extends MyTest{

	@Test
	public void init_valid() {
		screen = new ScreenContract(new ScreenImpl());
		screen.init(0, 0);		
	}
	
	@Test
	public void init_unbound() {
		screen = new ScreenContract(new ScreenImpl());
		for(int x = -1; x < 1; x++)
			for(int y = -1; y < 1; y++) {
				if(x == 0 && y == 0) continue;
				try {
					screen.init(x, y);
				} catch(PreconditionError e) {
					continue;
				} Assert.fail("Screen : (" + x + "," + y + ")");
			}
	}
}
