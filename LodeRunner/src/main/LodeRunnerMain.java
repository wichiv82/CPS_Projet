package main;

import java.awt.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import impl.EditableScreenImpl;
import impl.EngineImpl;
import services.Command;
import services.GuardService;
import services.ItemService;

public class LodeRunnerMain {
	
	protected static EngineImpl engine;
	private static Scanner scan = new Scanner(System.in);
	
	public LodeRunnerMain(EditableScreenImpl e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		engine = new EngineImpl();
		engine.init(e, player, guards, treasures);
	}
	
	
	public EngineImpl getEngine() {
		return this.engine;
	}
	
	public static ArrayList<String[][]> readFile(String file) {
		ArrayList<String[][]> res = new ArrayList<String[][]>();
		
		try (FileReader reader = new FileReader(file);
				 BufferedReader br = new BufferedReader(reader)) {
			
			String line = br.readLine();
            String[] tailles = line.split(" ");
            res.add(new String[Integer.parseInt(tailles[0])][Integer.parseInt(tailles[1])]);
            
            int j= Integer.parseInt(tailles[1])-1; 
            
	        while ((line = br.readLine()) != null) {
	        	if (j>-1) { // RECUPERE LES LIGNES DU NIVEAU
	        		for(int i=0; i<line.length(); i++) {
	        			res.get(0)[i][j] = String.valueOf(line.charAt(i));
	        		}
	        	} else if (j == -1){ // RECUPERE LES COORDONNEES DU JOUEUR
	        		String[][] posJoueur = new String[1][2];
	        		posJoueur[0] = line.split(" ");
	        		res.add(posJoueur);
	        	} else if(j == -2) { // RECUPERE LES COORDONNEES DES TRESORS
	        		String[] posTresors = line.split(" ");
	        		String[][] tresors = new String[1][posTresors.length];
	        		tresors[0] = posTresors;
	        		res.add(tresors);;
	        	} else if(j == -3) { // RECUPERE LES COORDONNEES DES GARDES
	        		String[] posGardes = line.split(" ");
	        		String[][] gardes = new String[1][posGardes.length];
	        		gardes[0] = posGardes;
	        		res.add(gardes);;
	        	}
	        	
	        	j--;
	        }
	        
	        reader.close();

		 } catch (IOException e) {
	        System.err.format("IOException: %s%n", e);
	     }
		
		return res;
	}
	
	public static void afficher() {
		String [][] res = new String[engine.getEnvi().getWidth()][engine.getEnvi().getHeight()];

		for (int i=0; i<engine.getEnvi().getWidth(); i++) {
			for (int j=0; j<engine.getEnvi().getHeight(); j++) {
				switch(engine.getEnvi().cellNature(i, j)) {
					case EMP:
						res[i][j] = " ";
						break;
					case PLT:
						res[i][j] = "=";
						break;
					case HOL:
						res[i][j] = "_";
						break;
					case LAD:
						res[i][j] = "H";
						break;
					case HDR:
						res[i][j] = "‾";
						break;
					case MTL:
						res[i][j] = "X";
						break;
						
				}
			}
		}
		
		for (ItemService i: engine.getTreasures()) 
			res[i.getColumn()][i.getHeight()] = "*";

		for (GuardService g: engine.getGuards())
			if(g.hasItem())
				res[g.getWidth()][g.getHeight()] = "G";
			else
				res[g.getWidth()][g.getHeight()] = "g";
		
		res[engine.getPlayer().getWidth()][engine.getPlayer().getHeight()] = "O";
		
		for(int j=res[0].length-1; j>=0; j--){
			for(int i=0; i<res.length; i++) {
				System.out.print(res[i][j]);
			}
			System.out.println();
		}
		
	}
	
	public void readCommand() {
		System.out.print("# ");
		String ligne = scan.nextLine();
		
		System.out.println("Score : "+ engine.getScore() +" ------------>>>> " + ligne);
		switch(ligne) {
			case "z":
				engine.setCommand(Command.UP);
				break;
			case "q":
				engine.setCommand(Command.LEFT);
				break;
			case "s":
				engine.setCommand(Command.DOWN);
				break;
			case "d":
				engine.setCommand(Command.RIGHT);
				break;
			case "4":
				engine.setCommand(Command.DIGL);
				break;
			case "6":
				engine.setCommand(Command.DIGR);
				break;
			default:
				//engine.setCommand(Command.NEUTRAL);
				break;
		}
		
	}
}
