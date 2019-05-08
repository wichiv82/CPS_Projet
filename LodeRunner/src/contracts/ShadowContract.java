package contracts;

import decorators.ShadowDecorator;
import services.Cell;
import services.CharacterService;
import services.EngineService;
import services.Move;
import services.ScreenService;

public class ShadowContract extends ShadowDecorator{

	public ShadowContract(CharacterService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}
	
	public void checkInvariants() {
		
	}
	
	public void init(ScreenService s, int x, int y) {
		if(s == null)
			throw new PreconditionError("ScreenService == null");
		if(!(0 <= x && x < s.getWidth()) || !(0 <= y && y < s.getHeight()))
			throw new PreconditionError("Position Shadow (" + x + "," + y + ") hors jeu");

		super.init(s, x, y);
		checkInvariants();
		
		if(!(!isAlive()))
			throw new PostconditionError("L'ombre commence la partie en alive.");	
		if(!(getTimeInHole() == 0))
			throw new PostconditionError("L'ombre commence la partie avec un timeInHole different de 0.");
	}
	
	public void setEngine(EngineService engine) {
		checkInvariants();
		super.setEngine(engine);
		checkInvariants();
		
		if(!(getBehaviour() == Move.NEUTRAL))
			throw new PostconditionError("L'ombre ne commence pas la partie en NEUTRAL.");
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
				throw new PostconditionError("La Shadow escalade le bord gauche de la carte.");
		
		
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
						if(!(getHeight() == y_atPre + 1 && getHeight() == x_atPre - 1))
							throw new PostconditionError("La Shadow s'est déplacé en "
									+ "(" + getWidth() + "," + getHeight() + ") au lieu d'aller en "
									+ "(" + (x_atPre - 1) + "," + (y_atPre + 1) + ") pour sortir d'un HOL");
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

		if(y_atPre == getEnvi().getHeight() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La cellule (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(getWidth() == getEnvi().getWidth() - 1)
			if(!(getHeight() == y_atPre))
				throw new PostconditionError("La Shadow escalade le bord droit de la carte.");
		
		
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
		boolean alive_atPre = isAlive();
		CharacterService pos_atPre = getEngine().getEnvi().cellContent(getWidth(), getHeight()).getCharacter();
		
		checkInvariants();
		super.setAlive(a);
		checkInvariants();
		
		if(a && !alive_atPre && pos_atPre == null) {
			if(!isAlive())
				throw new PostconditionError("Alive n'a pas été modifié.");
			if(getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null)
				throw new PostconditionError("Shadow Environement non mis à jour.");
			if(getEngine().getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null)
				throw new PostconditionError("Engine Environement non mis à jour.");
			if(getEngine().getPlayer().getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null)
				throw new PostconditionError("Player Environement non mis à jour.");
			
			for(int i=0; i<getEngine().getGuards().size(); i++)
				if(getEngine().getGuards().get(i).getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null)
					throw new PostconditionError("Guard Environement non mis à jour.");
		}else if(!a) {
			if(isAlive())
				throw new PostconditionError("Alive n'a pas été modifié.");
			if(!(getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null))
				throw new PostconditionError("Shadow Environement non mis à jour.");
			if(!(getEngine().getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null))
				throw new PostconditionError("Engine Environement non mis à jour.");
			if(!(getEngine().getPlayer().getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null))
				throw new PostconditionError("Player Environement non mis à jour.");
			
			for(int i=0; i<getEngine().getGuards().size(); i++)
				if(!(getEngine().getGuards().get(i).getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null))
					throw new PostconditionError("Guard Environement non mis à jour.");
		}
	}
}
