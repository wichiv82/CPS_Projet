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
	
	public void init (int w, int h) {
		if (!(0 < w))
			throw new PreconditionError("Largeur de EditableScreen est incorrecte : " + w);
		if (!(0 < h))
			throw new PreconditionError("Hauteur de EditableScreen est incorrecte : " + h);
		
		super.init(w, h);
		checkInvariants();
		
		if(!(isPlayable() == false))
			throw new PostconditionError("Playable s'est mal initialisé.");		
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
