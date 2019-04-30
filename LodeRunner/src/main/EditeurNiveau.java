package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditeurNiveau {
	
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		
		ArrayList<String> entrees = new ArrayList<String>();
		
		String ligne = "";
		
		System.out.println("EDITEUR DE NIVEAU");
		System.out.println("Entrez un numéro de ligne pour modifier la ligne souhaitée");
		System.out.println("MTL = X | EMP = - | PLT = = | ");
		System.out.println("HDR = ‾ | LAD = H |");
		
		while(true) {
			ligne = scan.nextLine();
			
			if (ligne.equals("FIN"))
				break;
			
			boolean isNumerique = false;
			int num_ligne = 0; 	
		       	
			try {
		        num_ligne = Integer.parseInt(ligne);
		        isNumerique = true;
		    } catch (NumberFormatException e) {
		    	isNumerique = false;
		    }
			
			if(isNumerique && num_ligne < entrees.size()) {
				System.out.println("Modification de la ligne "+ num_ligne +" : ");
				entrees.set(num_ligne, scan.nextLine());
			} else {
				entrees.add(ligne);
			}
		}
		
		int ligne_max = entrees.get(0).length();
		for (String l : entrees)
			if (ligne_max < l.length())
				ligne_max = l.length();
		
		System.out.println("ligne_max = "+ ligne_max);
		
		for (int i=0; i<entrees.size(); i++) {
			while(entrees.get(i).length() < ligne_max) 
				entrees.set(i,entrees.get(i) + "-" );
			System.out.println(entrees.get(i));
		}
		
		System.out.println("Entrez la position du joueur");
		String posJoueur = scan.nextLine();
		
		System.out.println("Entrez la position des coffres dans ce format: ");
		System.out.println("x1 y1 x2 y2 ... xn yn");
		String posCoffres = scan.nextLine();
		
		System.out.println("Entrez la position des gardes dans ce format: ");
		System.out.println("x1 y1 x2 y2 ... xn yn");
		String posGardes = scan.nextLine();
		
		String filename;
		
		while(true) {
			System.out.println("Entrez un nom pour le niveau : ");
			filename = scan.nextLine();
			if (new File("levels/"+filename+".txt").exists())
				System.out.println("Nom de niveau deja pris, veuillez choisir un autre");
			else
				break;
		}
		
		BufferedWriter writer;
		
		try {
			writer = new BufferedWriter(new FileWriter("levels/"+filename+".txt"));
			writer.write(ligne_max + " "+ entrees.size()+"\n");
			
			for (int i=0; i<entrees.size(); i++) {
				System.out.println(entrees.get(i));
				writer.write(entrees.get(i)+"\n");
			}
			
			writer.write(posJoueur + "\n");
			writer.write(posCoffres + "\n");
			
			if (!posGardes.equals(""))
				writer.write(posGardes + "\n");

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		scan.close();
		
		System.out.println("Niveau crée avec succès !");
	}
}
