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
					throw new InvariantError("La cellule (" + i + ",0) est une MTL.");
			for(int j = 0; j < getHeight(); j++)
				if(cellNature(i, j) == Cell.HOL)
					if(isPlayable())
						throw new InvariantError("La cellule (" + i + "," + j + ") est un HOL.");
		}
	}
	
	public void init (int h, int w) {
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
						throw new PostconditionError("La cellule (" + i + "," + j + ") n'a pas été modifiée");
					}
				} else {
					if(!(cellNature(i, j) == tmp.get(i).get(j)))
						throw new PostconditionError("setNature a modifié la cellule (" + i + "," + j + ") "
								+ "alors que la cellule cible est (" + x + "," + y + ").");
				}
			}
	}
}
