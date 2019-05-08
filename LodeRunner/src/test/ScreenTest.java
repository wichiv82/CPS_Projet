package test;

import org.junit.Assert;
import org.junit.Test;

import contracts.PreconditionError;
import contracts.ScreenContract;
import impl.ScreenImpl;
import services.ScreenService;

public class ScreenTest {

	private ScreenService screen = new ScreenContract(new ScreenImpl());

	/* public void init(int w, int h) */
	
	@Test
	public void init_zeros_dimensions() {
		screen.init(0, 0);
	}
	
	@Test
	public void init_positives_dimentions() {
		screen.init(1, 1);
	}
	
	@Test
	public void init_negative_width() {
		try {
			screen.init(-1, 1);
		} catch(PreconditionError e) {
			return;
		} Assert.fail();
	}
	
	@Test
	public void init_negative_height() {
		try {
			screen.init(1, -1);
		} catch(PreconditionError e) {
			return;
		}Assert.fail();
	}
}
