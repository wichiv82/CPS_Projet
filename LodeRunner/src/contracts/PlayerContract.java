package contracts;

import decorators.PlayerDecorator;
import services.Cell;
import services.PlayerService;
import services.ScreenService;

public class PlayerContract extends PlayerDecorator {

	public PlayerContract(PlayerService delegate) {
		super(delegate);
	}

	public void checkInvariants() {
		switch(getEnvi().cellNature(getWidth(), getHeight())) {
			case EMP:
			case LAD:
			case HDR:
			case HOL :
				break;
			default:
				throw new InvariantError("Le joueur est sur un emplacement indisponible.");
		}
		if (!getEnvi().cellContent(getWidth(), getHeight()).getCharacter().equals(this))
			throw new InvariantError("Le joueur n'est pas présent sur son emplacement.");
	}
	
	public void init(ScreenService s, int x, int y) {
		if(!(x >= 0))
			throw new PreconditionError("coord x = " + x + " en dehors du jeu " + s.getWidth());
		if(!(x < s.getWidth()))
			throw new PreconditionError("coord x = " + x + " en dehors du jeu " + s.getWidth());
		if(!(y >= 0))
			throw new PreconditionError("coord y = " + y + "en dehors du jeu " + s.getHeight());
		if(!(y < s.getHeight()))
			throw new PreconditionError("coord y = " + y + "en dehors du jeu " + s.getHeight());
		
		getDelegate().init(s, x, y);
		
		for(int i = 0; i < getEnvi().getWidth(); i++)
			for(int j = 0; j < getEnvi().getWidth(); j++)
				if(!(s.cellNature(i, j) == getEnvi().cellNature(i, j)))
					throw new PostconditionError("jeu du joueur mal initialisé");
		if(!(getWidth() == x))
			throw new PostconditionError("x mal initialisé.");
		if(!(getHeight() == y))
			throw new PostconditionError("y mal initialisé.");
	}
	
	public void step() {
		int height_atPre = getHeight();
		int width_atPre = getWidth();
		
		checkInvariants();
		super.step();
		checkInvariants();
		
		if (height_atPre != 0)
			switch(getEnvi().cellNature(width_atPre, height_atPre)) {
				case HDR:
				case LAD:
					break;
				default:
					if(getEnvi().cellNature(width_atPre, height_atPre - 1) == Cell.EMP)
						if(getHeight() != height_atPre -1 && getWidth() != width_atPre)
							throw new PostconditionError("Le joueur ne tombe pas.");
			}
	}
}
