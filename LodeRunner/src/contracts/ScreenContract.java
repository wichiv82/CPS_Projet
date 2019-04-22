package contracts;

import java.util.ArrayList;

import decorators.ScreenDecorator;
import services.Cell;
import services.ScreenService;

public class ScreenContract extends ScreenDecorator{
	
	public ScreenContract(ScreenService delegate) {
		super(delegate);
	}
	
	public void checkInvariants() {
		// RIEN A FAIRE
	}
	
	public void init(int w, int h) {
		if (!(0 < w && 0< h))
			throw new PreconditionError("Les dimensions du Screen doivent etre superieurs a 0");
		
		checkInvariants();
		super.init(w, h);
		checkInvariants();
		
		if(!(getWidth() == w && getHeight() == h))
			throw new PostconditionError("Erreur init : les dimensions n'ont pas ete correctement affectes");
		
		for (int i=0; i<getWidth(); i++){
			for (int j=0; j<getHeight(); j++) {
				if(!(cellNature(i, j) == Cell.EMP))
					throw new PostconditionError("Erreur init : les cellules ne sont pas tous vides");
			}
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
