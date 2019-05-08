package contracts;


import decorators.GuardDecorator;
import services.Cell;
import services.CharacterService;
import services.EngineService;
import services.GuardService;
import services.ItemService;
import services.Move;
import services.ScreenService;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	public void checkInvariants() {
		
	}
	
	
	public void init(ScreenService s, int x, int y, int id, CharacterService target) {
		if(s == null)
			throw new PreconditionError("ScreenService == null");
		if(!(0 <= x && x < s.getWidth()) || !(0 <= y && y < s.getHeight()))
			throw new PreconditionError("Position Guard (" + x + "," + y + ") hors jeu");
		if (target == null)
			throw new PreconditionError("Target == null");
		if (target.getWidth() == x && target.getHeight() == y)
			throw new PreconditionError("Cellule (" + x + "," + y + ") -> 2 Character");
		super.init(s, x, y, id, target);
		checkInvariants();
		
		if(!(getId() == id))
			throw new PostconditionError("L'ID du Garde est " + id + " au lieu de " + getId() + ".");
		if(getTarget() == null)
			throw new PostconditionError("La target n'a pas été enregistrée.");
		if(!(getPosInit().getX() == x && getPosInit().getY() == y))
			throw new PostconditionError("La position initial a mal été enregistré en "
					+ "(" + getPosInit().getX() + "," + getPosInit().getY() + ") au lieu de "
					+ "(" + x + "," + y + ").");
		if(!(getItem() == null))
			throw new PostconditionError("Le garde commence la partie avec un item.");
		
		if(!(getTimeInHole() == 0))
			throw new PostconditionError("Le garde commence la partie avec un timeInHole different de 0.");
	}
	
	public void setEngine(EngineService engine) {
		checkInvariants();
		super.setEngine(engine);
		checkInvariants();
		
		if(!(engine == null) && getEngine() == null)
			throw new PostconditionError("L'engine n'a pas été enregistré.");
	}
	
	public void respawn() {
		
		int x_atPre = getWidth();
		int y_atPre = getHeight();

		checkInvariants();
		super.respawn();
		checkInvariants();
		
		if(!(getPosInit().getX() == getWidth() && getPosInit().getY() == getHeight()))
			throw new PostconditionError("Le Guard n°" + getId() + " a respawn en (" + getWidth() + "," + getHeight() + ")"
					+ " au lieu d'aller en (" + getPosInit().getX() + "," + getPosInit().getY() + ").");
		
		if(!(getEnvi().cellContent(x_atPre, y_atPre).getCharacter() == null))
			throw new PostconditionError("Un garde est présent dans la cellule (" + x_atPre + "," + y_atPre + ") après un respawn.");
		
	}
	
	public void climbLeft() {
		int x_atPre = getWidth();
		int y_atPre = getHeight();
		checkInvariants();
		super.climbLeft();
		checkInvariants();

		if(y_atPre == getEngine().getEnvi().getHeight() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La cellule (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(getWidth() == 0)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Le Guard escalade le bord gauche de la carte.");
		
		
		if(!(getEnvi().cellNature(x_atPre, y_atPre) == Cell.HOL))
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Cellule (" + x_atPre + "," + y_atPre + ") is " + getEnvi().cellNature(x_atPre, y_atPre));
		
		
		if(!(x_atPre == 0 || y_atPre == getEnvi().getHeight() - 1)) {
			switch(getEnvi().cellNature(x_atPre - 1, y_atPre)) {
				case HOL:
					if(!(getEnvi().cellContent(x_atPre - 1, y_atPre).getCharacter() == null))
						break;
				case EMP:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("Le Guard ne s'appuie sur rien pour escalader.");
				default:
			}
			switch(getEnvi().cellNature(x_atPre, y_atPre + 1)) {
			case PLT:
			case MTL:
				if(!(getHeight() == y_atPre))
					throw new PostconditionError("Le Guard escalade le HOL alors que la cellule "
							+ "(" + x_atPre + "," + (y_atPre + 1) + ") est " + getEnvi().cellNature(x_atPre, y_atPre + 1) + ".");
			default:
				if(!(getEnvi().cellContent(x_atPre, y_atPre + 1).getCharacter() == null))
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("Le Guard escalade le HOL alors qu'un shadow est sur la cellule "
								+ "(" + x_atPre + "," + (y_atPre + 1) + ").");
			}
			switch(getEnvi().cellNature(x_atPre - 1, y_atPre + 1)) {
				case PLT:
				case MTL:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("Le Guard escalade alors que le mur est trop haut.");
				default:
			}
		}
	}
	
	public void climbRight() {
		int x_atPre = getWidth();
		int y_atPre = getHeight();
		
		checkInvariants();
		super.climbRight();
		checkInvariants();
		
		if(y_atPre == getEngine().getEnvi().getHeight() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La cellule (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(getWidth() == 0)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Le Guard escalade le bord gauche de la carte.");
		
		
		if(!(getEnvi().cellNature(x_atPre, y_atPre) == Cell.HOL))
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("Cellule (" + x_atPre + "," + y_atPre + ") is " + getEnvi().cellNature(x_atPre, y_atPre));
		
		if(!(x_atPre == getEnvi().getWidth() - 1 || y_atPre == getEnvi().getHeight() - 1)) {
			switch(getEnvi().cellNature(x_atPre + 1, y_atPre)) {
				case HOL:
					if(!(getEnvi().cellContent(x_atPre + 1, y_atPre).getCharacter() == null))
						break;
				case EMP:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("Le Guard ne s'appuie sur rien pour escalader.");
				default:
			}
			switch(getEnvi().cellNature(x_atPre, y_atPre + 1)) {
				case PLT:
				case MTL:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("Le Guard escalade le HOL alors que la cellule "
								+ "(" + x_atPre + "," + (y_atPre + 1) + ") est " + getEnvi().cellNature(x_atPre, y_atPre + 1) + ".");
				default:
					if(!(getEnvi().cellContent(x_atPre, y_atPre + 1).getCharacter() == null))
						if(!(getHeight() == y_atPre))
							throw new PostconditionError("Le guard escalade le HOL alors qu'un garde est sur la cellule "
									+ "(" + x_atPre + "," + (y_atPre + 1) + ").");
				}
				switch(getEnvi().cellNature(x_atPre + 1, y_atPre + 1)) {
					case PLT:
					case MTL:
						if(!(getHeight() == y_atPre))
							throw new PostconditionError("Le Guard escalade alors que le mur est trop haut.");
					default:
				}
		}
			
		if(getWidth()<getEngine().getEnvi().getWidth()-1 && getHeight()<getEngine().getEnvi().getHeight()-1){
			if(getEngine().getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL &&
					getEngine().getEnvi().cellContent(getWidth(), getHeight()+1).getCharacter() == null &&
							getEngine().getEnvi().cellContent(getWidth()+1, getHeight()+1).getCharacter() == null){
				switch(getEngine().getEnvi().cellNature(getWidth()+1, getHeight()+1)){
					case EMP:
					case LAD:
					case HDR:
						if(!(getHeight() == y_atPre + 1))
							throw new PostconditionError("Le Guard s'est déplacé en "
									+ "(" + getWidth() + "," + getHeight() + ") au lieu d'aller en "
									+ "(" + x_atPre + "," + (y_atPre + 1) + ") pour sortir d'un HOL");
						break;
					default:
				}
				
			}	
		}
	}
	
	public void removeItem() {
		checkInvariants();
		super.removeItem();
		checkInvariants();
		
		if(!(getItem() == null))
			throw new PostconditionError("L'item du Guard n'a pas été suprimé.");
	}
	
	public void giveItem(ItemService item) {
		getDelegate().giveItem(item);
		
		if(!(item == null) && getItem() == null)
			throw new PostconditionError("L'item du Guard n'a pas été enregistré.");
		if(!(getItem().equals(item)))
			throw new PostconditionError("Le Guard a enregistré le mauvais item.");
	}
	
	public void step() {
		checkInvariants();
		super.step();
		checkInvariants();
	}
}
