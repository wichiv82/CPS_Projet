package contracts;

import java.util.ArrayList;

import decorators.EnvironmentDecorator;
import services.Cell;
import services.EditableScreenService;
import services.EnvironmentService;

public class EnvironmentContract extends EnvironmentDecorator{

	public EnvironmentContract(EnvironmentService delegate) {
		super(delegate);
	}

	public void checkInvariants() {
		for(int i = 0; i < getWidth(); i ++)
			for(int j = 0; j < getHeight(); j++) {
				if(cellContent(i,j) == null)
					throw new InvariantError("le contenu de la cellule "+ i +"," + j + " n'est pas initialisé.");
				if(cellContent(i, j).getCharacter() != null && !(cellContent(i, j).getCharacter().equals(cellContent(i, j).getCharacter())))
					throw new InvariantError("Environment::cellContent n'est pas déterministe.");
				switch(cellNature(i, j)) {
					case MTL:
					case PLT:
						if(!(cellContent(i, j).getCharacter() == null && cellContent(i, j).getItem() == null))
							throw new InvariantError("Un joueur ou un objet est sur un emplacement indisponible");
					default:
				
				}
				if(!(cellContent(i, j).getItem() == null))
					switch(cellNature(i, j - 1)) {
						case PLT:
						case MTL:
							if(cellNature(i, j) == Cell.EMP) break;
						default:
							throw new InvariantError("Un trésor est dans le vide ou sur un emplacement indisponible.");
					}
			}
	}
	
	public void init(EditableScreenService edit){
		super.init(edit);
		checkInvariants();
		
		if(!(edit.getWidth() == getWidth()))
			throw new PostconditionError("Largeur mal initialisée.");
		if(!(edit.getHeight() == getHeight()))
			throw new PostconditionError("Hauteur mal initialisée.");
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++) {
				if(cellContent(i,j) == null)
					throw new InvariantError("le contenu de la cellule "+ i +"," + j + " n'est pas initialisé.");
				if(!(cellNature(i, j) == edit.cellNature(i, j)))
					throw new PostconditionError("la cellule " + i + ", " + j + " s'est mal initialisée." );
			}
	}
	
	public void init(int w, int h) {
		if (!(0 < w && 0< h))
			throw new PreconditionError("Les dimensions du Screen doivent etre superieurs a 0");
		
		super.init(w, h);
		checkInvariants();
		
		if(!(w == getWidth()))
			throw new PostconditionError("Largeur mal initialisée.");
		if(!(h == getHeight()))
			throw new PostconditionError("Hauteur mal initialisée.");
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++) {
				super.init(i, j);
				if(!(cellNature(i, j) == Cell.EMP))
					throw new PostconditionError("la cellule " + i + ", " + j + " s'est mal initialisée." );
			}
	}
	
	public void dig(int x, int y) {
		if(!(cellNature(x, y) == Cell.PLT))
			throw new PreconditionError("Dig essaie de boucher une cellule non PLT.");
		
		ArrayList<ArrayList<Cell>> tmp = new ArrayList<>();
		for(int i = 0; i < getWidth(); i++) {
			tmp.add(new ArrayList<Cell>());
			for(int j = 0; j < getHeight(); j++)
				tmp.get(i).add(cellNature(i, j));
		}
		checkInvariants();
		super.dig(x, y);
		checkInvariants();
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++) {
				if(i == x && j == y)
					if(!(cellNature(x, y) == Cell.HOL))
						throw new PostconditionError("Le trou n'a pas été creusé.");
				else
					if(!(cellNature(x,y) == tmp.get(i).get(j)))
						throw new PostconditionError("Dig a modifié le mauvais emplacement.");
						
			}
		
	}

	public void fill(int x, int y) {
		if(!(cellNature(x, y) == Cell.HOL))
			throw new PreconditionError("Fill essaie de boucher une cellule non HOL.");
		
		ArrayList<ArrayList<Cell>> tmp = new ArrayList<>();
		for(int i = 0; i < getWidth(); i++) {
			tmp.add(new ArrayList<Cell>());
			for(int j = 0; j < getHeight(); j++)
				tmp.get(i).add(cellNature(i, j));
		}
		checkInvariants();
		super.fill(x, y);
		checkInvariants();
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++) {
				if(i == x && j == y)
					if(!(cellNature(x, y) == Cell.PLT))
						throw new PostconditionError("Le trou n'a pas été creusé.");
				else
					if(!(cellNature(x,y) == tmp.get(i).get(j)))
						throw new PostconditionError("Fill a modifié le mauvais emplacement.");
						
			}
	}
}
