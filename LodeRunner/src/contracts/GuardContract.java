package contracts;

import java.util.ArrayList;

import decorators.GuardDecorator;
import services.Cell;
import services.CharacterService;
import services.EngineService;
import services.GuardService;
import services.ItemService;
import services.ScreenService;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	public void checkInvariants() {
		
	}
	
	
	public void init(ScreenService s, int x, int y, int id, CharacterService target) {
		super.init(s, x, y);
		checkInvariants();
		
		if(!(getId() == id))
			throw new PostconditionError("L'ID du Garde est " + id + " au lieu de " + getId() + ".");
		if(!(getTarget().equals(target)))
			throw new PostconditionError("La target s'est mal initialisée");
		if(!(getPosInit().getX() == x && getPosInit().getY() == y))
			throw new PostconditionError("La position initial a mal été enregistré en "
					+ "(" + getPosInit().getX() + "," + getPosInit().getY() + ") au lieu de "
					+ "(" + x + "," + y + ").");
		if(!(getItem() == null))
			throw new PostconditionError("Le garde commence la partie avec un item.");
	}
	
	public void setEngine(EngineService engine) {
		checkInvariants();
		super.setEngine(engine);
		checkInvariants();
		
		if(!(getEngine().equals(engine)))
			throw new PostconditionError("L'Engine du Guard n°" + getId() + " s'est mal initialisée.");
	}
	
	public void respawn() {
		
		int x_atPre = getWidth();
		int y_atPre = getHeight();
		ArrayList<ArrayList<CharacterService>> character_atPre = new ArrayList<>();
		ArrayList<ArrayList<CharacterService>> engine_character_atPre = new ArrayList<>();
		
		for(int i = 0; i < getEnvi().getWidth(); i++) {
			character_atPre.add(new ArrayList<>());
			engine_character_atPre.add(new ArrayList<>());
			for(int j = 0; j < getEnvi().getHeight(); j++) {
				character_atPre.get(i).add(getEnvi().cellContent(i, j).getCharacter());
				engine_character_atPre.get(i).add(getEngine().getEnvi().cellContent(i, j).getCharacter());
			}
		}
		
		checkInvariants();
		super.respawn();
		checkInvariants();
		
		if(!(getPosInit().getX() == getWidth() && getPosInit().getY() == getHeight()))
			throw new PostconditionError("Le Guard n°" + getId() + " a respawn en (" + getWidth() + "," + getHeight() + ")"
					+ " au lieu d'aller en (" + getPosInit().getX() + "," + getPosInit().getY() + ").");
		
		for(int i = 0; i < getEnvi().getWidth(); i++)
			for(int j = 0; j < getEnvi().getHeight(); j++) {
				if(i == x_atPre && j == y_atPre) {
					if(!(getEnvi().cellContent(i,j).getCharacter() == null))
						throw new PostconditionError("Un garde est toujours en (" + i + "," + j + ")"
								+ " dans l'Environement après un respawn.");
					if(!(getEngine().getEnvi().cellContent(i, j).getCharacter() == null))
						throw new PostconditionError("Un garde est toujours en (" + i + "," + j + ")"
								+ " dans l'Engine après un respawn.");
				}
				else
					if(!(i == getWidth() && j == getWidth())) {
						if(!(getEnvi().cellContent(i, j).getCharacter().equals(character_atPre.get(i).get(j))))
							throw new PostconditionError("La présence d'un garde à été modifié dans la cellule (" + i + "," + j + ")"
									+ " de l'Environement lors d'un respawn alors qu'elle n'était pas l'une des cellules cibles.");
						if(!(getEngine().getEnvi().cellContent(i, j).getCharacter().equals(engine_character_atPre.get(i).get(j))))
							throw new PostconditionError("La présence d'un garde à été modifié dans la cellule (" + i + "," + j + ")"
									+ " de l'Engine lors d'un respawn alors qu'elle n'était pas l'une des cellules cibles.");
					}
			}
	}
	
	public void climbLeft() {
		int x_atPre = getWidth();
		int y_atPre = getHeight();
		
		checkInvariants();
		super.climbLeft();
		checkInvariants();
		
		if(!(getWidth() == x_atPre))
			throw new PostconditionError("La Guard se deplace lateralement pendant un climb.");
		
		if(getHeight() == getEnvi().getHeight() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La cellule (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(getWidth() == 0)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Le Guard escalade le bord gauche de la carte.");
		
		
		if(!(getEnvi().cellNature(x_atPre, y_atPre) != Cell.HOL))
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Le Guard escalade par la gauche alors que la cellule (" + x_atPre + "," + y_atPre + ")"
						+ " n'est pas un HOL.");
			
		if(getEnvi().cellNature(x_atPre, y_atPre) == Cell.HOL)
			if(getWidth() > 0 && getHeight() < getEnvi().getHeight() - 1)
				if(getEnvi().cellContent(x_atPre, y_atPre + 1).getCharacter() == null)
					switch(getEnvi().cellNature(x_atPre, y_atPre + 1)) {
						case EMP:
						case HDR:
						case LAD:
							if(!(getHeight() == y_atPre + 1))
								throw new PostconditionError("Le Guard s'est déplacé en "
										+ "(" + getWidth() + "," + getHeight() + ") au lieu d'aller en "
										+ "(" + x_atPre + "," + (y_atPre + 1) + ") pour sortir d'un HOL");
						default:
						}
	}
	
	public void climbRight() {
		int x_atPre = getWidth();
		int y_atPre = getHeight();
		
		checkInvariants();
		super.climbRight();
		checkInvariants();
		
		if(!(getWidth() == x_atPre))
			throw new PostconditionError("La Guard se deplace lateralement pendant un climb.");
		
		
		if(getHeight() == getEnvi().getHeight() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La cellule (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(getWidth() == getEnvi().getWidth() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Le Guard escalade le bord droit de la carte.");
		
		if(!(getEnvi().cellNature(x_atPre, y_atPre) != Cell.HOL))
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Le Guard escalade par la gauche alors que la cellule (" + x_atPre + "," + y_atPre + ")"
						+ " n'est pas un HOL.");
			
		if(getEnvi().cellNature(x_atPre, y_atPre) == Cell.HOL)
			if(getWidth() > 0 && getHeight() < getEnvi().getHeight() - 1)
				if(getEnvi().cellContent(x_atPre, y_atPre + 1).getCharacter() == null)
					switch(getEnvi().cellNature(x_atPre, y_atPre + 1)) {
						case EMP:
						case HDR:
						case LAD:
							if(!(getHeight() == y_atPre + 1))
								throw new PostconditionError("Le Guard s'est déplacé en "
										+ "(" + getWidth() + "," + getHeight() + ") au lieu d'aller en "
										+ "(" + x_atPre + "," + (y_atPre + 1) + ") pour sortir d'un HOL");
						default:
						}
	}
	
	public void removeItem() {
		// TODO Auto-generated method stub
		getDelegate().removeItem();
	}
	
	public void giveItem(ItemService item) {
		// TODO Auto-generated method stub
		getDelegate().giveItem(item);
	}
	
	public void step() {
		// TODO Auto-generated method stub
		getDelegate().step();
	}
}
