package main;

import java.awt.Point;
import java.util.ArrayList;

import impl.EditableScreenImpl;
import services.Cell;
import services.Status;

public class LodeRunner1 extends LodeRunnerMain{

	public LodeRunner1(EditableScreenImpl e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		super(e, player, guards, treasures);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static void main(String[] args) {
		EditableScreenImpl e = new EditableScreenImpl();
		
		String[][] lignes = readFile("levels/level1.txt");
		e.init(lignes.length, lignes[0].length);
		
		for(int i=0; i<lignes.length; i++) {
			for(int j=0; j<lignes[i].length; j++) {
				switch(lignes[i][j]) {
				case "-":
					e.setNature(i, j, Cell.EMP);
					break;
				case "=":
					e.setNature(i, j, Cell.PLT);
					break;
				case "_":
					e.setNature(i, j, Cell.HOL);
					break;
				case "H":
					e.setNature(i, j, Cell.LAD);
					break;
				case "‾":
					e.setNature(i, j, Cell.HDR);
					break;
				case "X" :
					e.setNature(i, j, Cell.MTL);
					break;
					
				}
			}
		}
		
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(6,5));
		treasures.add(new Point(7,1));
		treasures.add(new Point(0,2));
		
		ArrayList<Point> gardes = new ArrayList<Point>();
		
		LodeRunner1 run = new LodeRunner1(e,new Point(1,1), gardes, treasures);
		run.afficher();
		
		while(run.engine.getStatus() == Status.PLAYING) {
			run.readCommand();
			run.engine.step();
			run.afficher();
		}
	}

}
