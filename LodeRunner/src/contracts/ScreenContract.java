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
		if (!(0 < w))
			throw new PreconditionError("Largeur de EditableScreen est incorrecte : " + w);
		if (!(0 < h))
			throw new PreconditionError("Hauteur de EditableScreen est incorrecte : " + h);
		
		super.init(w, h);
		checkInvariants();
		
		if(!(w == getWidth()))
			throw new PostconditionError("La largeur de EditableScreen devrait être " + w + " mais c'est mal initialisée : " + getWidth());
		if(!(h == getHeight()))
			throw new PostconditionError("La hauteur de EditableScreen devrait être " + h + " mais c'est mal initialisée : " + getHeight());
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++)
				if(!(cellNature(i,j) == Cell.EMP))
					throw new PostconditionError("La cellule (" + i + "," + j + ") n'est pas EMP : " + cellNature(i,j));
	}
	
	public void dig(int x, int y) {
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
					switch(tmp.get(i).get(j)) {
						case PLT:
							if(!(cellNature(x, y) == Cell.HOL))
								if(cellNature(x, y) == tmp.get(i).get(j))
									throw new PostconditionError("Aucun HOL n'a été créé alors que la cellule (" + i + "," + j + ") est PLT.");
								else
									throw new PostconditionError("La cellule (" + i + "," + j + ") a été transformée en " + cellNature(i,j) + ".");
						default:
							if(!(tmp.get(i).get(j) == cellNature(i,j)))
								throw new PostconditionError("Un HOL à été créé en (" + i + "," + j + ") "
										+ "alors que la cellule était" + tmp.get(i).get(j) + ".");
					}
				else
					if(!(cellNature(x,y) == tmp.get(i).get(j)))
						throw new PostconditionError("Dig a modifié la cellule (" + i + "," + j + ") "
								+ "alors que la cellule cible est (" + x + "," + y + ").");
			}
	}

	public void fill(int x, int y) {
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
					switch(tmp.get(i).get(j)) {
						case HOL:
							if(!(cellNature(x, y) == Cell.PLT))
								if(cellNature(x, y) == tmp.get(i).get(j))
									throw new PostconditionError("Aucun PLT n'a été créé alors que la cellule (" + i + "," + j + ") est HOL.");
								else
									throw new PostconditionError("La cellule (" + i + "," + j + ") a été transformée en " + cellNature(i,j) + ".");
						default:
							if(!(tmp.get(i).get(j) == cellNature(i,j)))
								throw new PostconditionError("Un PLT à été créé en (" + i + "," + j + ") "
										+ "alors que la cellule était" + tmp.get(i).get(j) + ".");
					}
				else
					if(!(cellNature(x,y) == tmp.get(i).get(j)))
						throw new PostconditionError("Fill a modifié la cellule (" + i + "," + j + ") "
								+ "alors que la cellule cible est (" + x + "," + y + ").");
			}
	}
}
