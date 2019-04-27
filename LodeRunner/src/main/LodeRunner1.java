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
		
		ArrayList<String[][]> fileInfos = readFile("levels/level3.txt");
		
		String[][] lignes = fileInfos.get(0);
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
		for (int i=0; i<fileInfos.get(2)[0].length; i += 2) {
			Point posTresor = new Point(Integer.parseInt(fileInfos.get(2)[0][i]), Integer.parseInt(fileInfos.get(2)[0][i+1]));
			treasures.add(posTresor);
		}
		
		ArrayList<Point> gardes = new ArrayList<Point>();
		if(fileInfos.size() > 3) {
			for (int i=0; i<fileInfos.get(3)[0].length; i += 2) {
				Point posGarde = new Point(Integer.parseInt(fileInfos.get(3)[0][i]), Integer.parseInt(fileInfos.get(3)[0][i+1]));
				gardes.add(posGarde);
			}
		}
		
		Point posJoueur = new Point(Integer.parseInt(fileInfos.get(1)[0][0]), Integer.parseInt(fileInfos.get(1)[0][1]));
		LodeRunner1 run = new LodeRunner1(e,posJoueur, gardes, treasures);
		run.afficher();
		
		while(run.engine.getStatus() == Status.PLAYING) {
			run.readCommand();
			run.engine.step();
			run.afficher();
		}
		
		System.out.println("FIN DE PARTIE !!!");
		
		if(engine.getStatus() == Status.WIN) {
			System.out.println("YOU WIN");
		}else if(engine.getStatus() == Status.LOSS) {
			System.out.println("YOU LOSE");
		}
	}

}
