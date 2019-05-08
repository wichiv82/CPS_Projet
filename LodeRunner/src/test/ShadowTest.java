package test;


import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import contracts.EditableScreenContract;
import contracts.ShadowContract;
import impl.EditableScreenImpl;
import impl.ShadowImpl;
import services.Cell;
import services.EditableScreenService;
import services.EngineService;
import services.ShadowService;
import contracts.EngineContract;
import impl.EngineImpl;

public class ShadowTest extends MyTest {

	private ShadowService Shadow = new ShadowContract(new ShadowImpl());
	private EditableScreenService edit = new EditableScreenContract(new EditableScreenImpl());
	private EngineService engine = new EngineContract(new EngineImpl(true));
	private ArrayList<Point> guards = new ArrayList<>();
	private ArrayList<Point> treasures = new ArrayList<>();
	private Cell[] platform = {Cell.PLT, Cell.MTL, Cell.LAD};
	private Cell[] valide = {Cell.EMP, Cell.HDR, Cell.LAD};
	
	@Test
	public void climb_valide() {
		edit.init(7,3);
		for(int i = 0; i < 7; i++) {
			edit.setNature(i, 0, Cell.MTL);
			edit.setNature(i, 1, Cell.PLT);
		}
		edit.setNature(0,1,Cell.EMP);
		guards.add(new Point(0,1));
		Point player = new Point(3,2);
		for(Cell c1 : Cell.values())
			for(Cell c2 : Cell.values()) {
				edit.setNature(0, 1, c1);
				edit.setNature(0, 2, c2);
				engine.init(edit, player, guards, treasures);
				engine.getPlayer().goRight();
				engine.getPlayer().goRight();
				engine.getEnvi().dig(3, 1);
				System.out.println(engine.getShadow().getEnvi().getHeight());
				engine.getShadow().setAlive(true);
				System.out.println(engine.getShadow().getEnvi().getHeight());
				engine.getShadow().goDown();
				System.out.println(engine.getShadow().getEnvi().getHeight());
				Shadow.climbLeft();
				edit.setNature(0, 1, Cell.PLT);
				edit.setNature(0, 2, Cell.EMP);
				
				edit.setNature(2, 1, c1);
				edit.setNature(2, 2, c2);
				engine.init(edit, player, guards, treasures);
				engine.getPlayer().goLeft();
				engine.getPlayer().goLeft();
				engine.getEnvi().dig(3, 1);
				engine.getShadow().setAlive(true);
				engine.getShadow().goDown();
				Shadow.climbRight();
				edit.setNature(0, 1, Cell.PLT);
				edit.setNature(0, 2, Cell.EMP);
			}
	}
}
