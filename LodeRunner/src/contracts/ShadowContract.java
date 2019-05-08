package contracts;

import decorators.ShadowDecorator;
import services.Cell;
import services.CharacterService;
import services.ShadowService;

public class ShadowContract extends ShadowDecorator{

	public ShadowContract(ShadowService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	public void checkInvariants() {
		
	}
	
	public void climbLeft() {
		int x_atPre = getWidth();
		int y_atPre = getHeight();
		System.out.println(getHeight() + " " + getEnvi());
		checkInvariants();
		super.climbLeft();
		checkInvariants();
		
		if(!(getWidth() == x_atPre))
			throw new PostconditionError("La shadow se deplace lateralement pendant un climb.");
		
		System.out.println(getHeight() + " " + getEnvi());
		if(getHeight() == getEngine().getEnvi().getHeight() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La cellule (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(getWidth() == 0)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La Shadow escalade le bord gauche de la carte.");
		
		
		if(!(getEnvi().cellNature(x_atPre, y_atPre) != Cell.HOL))
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La Shadow escalade par la gauche alors que la cellule (" + x_atPre + "," + y_atPre + ")"
						+ " n'est pas un HOL.");
		
		if(!(x_atPre == 0 && y_atPre == getEnvi().getHeight() - 1)) {
			switch(getEnvi().cellNature(x_atPre - 1, y_atPre)) {
				case HOL:
					if(!(getEnvi().cellContent(x_atPre - 1, y_atPre).getCharacter() == null))
						break;
				case EMP:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("La Shadow ne s'appuie sur rien pour escalader.");
				default:
			}
			switch(getEnvi().cellNature(x_atPre, y_atPre + 1)) {
			case PLT:
			case MTL:
				if(!(getHeight() == y_atPre))
					throw new PostconditionError("La Shadow escalade le HOL alors que la cellule "
							+ "(" + x_atPre + "," + (y_atPre + 1) + ") est " + getEnvi().cellNature(x_atPre, y_atPre + 1) + ".");
			default:
				if(!(getEnvi().cellContent(x_atPre, y_atPre + 1).getCharacter() == null))
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("La Shadow escalade le HOL alors qu'un shadow est sur la cellule "
								+ "(" + x_atPre + "," + (y_atPre + 1) + ").");
			}
			switch(getEnvi().cellNature(x_atPre - 1, y_atPre + 1)) {
				case PLT:
				case MTL:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("La Shadow escalade alors que le mur est trop haut.");
				default:
			}
		}
		
		if(getWidth()>0 && getHeight()<getEngine().getEnvi().getHeight()-1){
			if(getEngine().getEnvi().cellNature(getWidth(), getHeight()) == Cell.HOL &&
				getEngine().getEnvi().cellContent(getWidth(), getHeight()+1).getCharacter() == null &&
				getEngine().getEnvi().cellContent(getWidth()-1, getHeight()+1).getCharacter() == null){
				switch(getEngine().getEnvi().cellNature(getWidth()-1, getHeight()+1)){
					case EMP:
					case LAD:
					case HDR:
						if(!(getHeight() == y_atPre + 1))
							throw new PostconditionError("La Shadow s'est déplacé en "
									+ "(" + getWidth() + "," + getHeight() + ") au lieu d'aller en "
									+ "(" + x_atPre + "," + (y_atPre + 1) + ") pour sortir d'un HOL");
						break;
					default:
						break;
				}
				
			}	
		}
	}
	
	public void climbRight() {
		int x_atPre = getWidth();
		int y_atPre = getHeight();
		
		checkInvariants();
		super.climbRight();
		checkInvariants();
		
		if(!(getWidth() == x_atPre))
			throw new PostconditionError("La shadow se deplace lateralement pendant un climb.");
		
		if(getHeight() == getEnvi().getHeight() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La cellule (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(getWidth() == getEnvi().getWidth() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La Shadow escalade le bord droit de la carte.");
		
		
		if(!(getEnvi().cellNature(x_atPre, y_atPre) != Cell.HOL))
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La Shadow escalade par la droit alors que la cellule (" + x_atPre + "," + y_atPre + ")"
						+ " n'est pas un HOL.");
		
		if(!(x_atPre == getEnvi().getWidth() - 1 && y_atPre == getEnvi().getHeight() - 1)) {
			switch(getEnvi().cellNature(x_atPre + 1, y_atPre)) {
				case HOL:
					if(!(getEnvi().cellContent(x_atPre + 1, y_atPre).getCharacter() == null))
						break;
				case EMP:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("La Shadow ne s'appuie sur rien pour escalader.");
				default:
			}
			switch(getEnvi().cellNature(x_atPre, y_atPre + 1)) {
			case PLT:
			case MTL:
				if(!(getHeight() == y_atPre))
					throw new PostconditionError("La Shadow escalade le HOL alors que la cellule "
							+ "(" + x_atPre + "," + (y_atPre + 1) + ") est " + getEnvi().cellNature(x_atPre, y_atPre + 1) + ".");
			default:
				if(!(getEnvi().cellContent(x_atPre, y_atPre + 1).getCharacter() == null))
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("La Shadow escalade le HOL alors qu'une shadow est sur la cellule "
								+ "(" + x_atPre + "," + (y_atPre + 1) + ").");
			}
			switch(getEnvi().cellNature(x_atPre + 1, y_atPre + 1)) {
				case PLT:
				case MTL:
					if(!(getHeight() == y_atPre))
						throw new PostconditionError("La Shadow escalade alors que le mur est trop haut.");
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
							throw new PostconditionError("La Shadow s'est déplacé en "
									+ "(" + getWidth() + "," + getHeight() + ") au lieu d'aller en "
									+ "(" + x_atPre + "," + (y_atPre + 1) + ") pour sortir d'un HOL");
						break;
					default:
				}
				
			}	
		}
	}
	
	public void setAlive(boolean a) {
		// TODO Auto-generated method stub
		super.setAlive(a);
		if(! (super.isAlive() == a))
			throw new PostconditionError("La variable alive n'a pas été modifiée.");
	}
}
