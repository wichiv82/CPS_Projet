package contracts;


import java.util.ArrayList;

import decorators.EditableScreenDecorator;
import services.Cell;
import services.EditableScreenService;

public class EditableScreenContract extends EditableScreenDecorator{

	public EditableScreenContract(EditableScreenService delegate) {
		super(delegate);
	}
	
	public void checkInvariants() {
		for(int i = 0; i < getWidth(); i++) {
			if(cellNature(i, 0) != Cell.MTL)
				if(isPlayable())
					throw new InvariantError("Une cellule du bas est une plateforme métallique.");
			for(int j = 0; j < getHeight(); j++)
				if(cellNature(i, j) == Cell.HOL)
					if(isPlayable())
						throw new InvariantError("Une cellule est un trou.");
		}
	}
	
	public void init (int h, int w) {
		if (!(0 < w && 0< h))
			throw new PreconditionError("Les dimensions du Screen doivent etre superieurs a 0");
		
		super.init(w, h);
		checkInvariants();
		
		if(!(w == getWidth()))
			throw new PostconditionError("Largeur mal initialisée.");
		if(!(h == getHeight()))
			throw new PostconditionError("Hauteur mal initialisée.");
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++)
				if(!(cellNature(i,j) == Cell.EMP))
					throw new PostconditionError("L'écran éditable c'est mal initialisé.");
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
	
	public void setNature(int x, int y, Cell c) {
		ArrayList<ArrayList<Cell>> tmp = new ArrayList<>();
		for(int i = 0; i < getWidth(); i++) {
			tmp.add(new ArrayList<>());
			for(int j = 0; j < getHeight(); j++)
				tmp.get(i).add(cellNature(i, j));
		}
		
		checkInvariants();
		super.setNature(x, y, c);
		checkInvariants();
		
		
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++) {
				if(i == x && j == y) {
					if(!(cellNature(x, y) == c)) {
						throw new PostconditionError("La cellule n'a pas été modifié.");
					}
				} else {
					if(!(cellNature(i, j) == tmp.get(i).get(j)))
						throw new PostconditionError("setNature à modifié un autre emplacement que celui voulu");
				}
			}
	}
}
