package contracts;

import decorators.PlayerDecorator;
import services.Cell;
import services.EngineService;
import services.PlayerService;

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
		if(getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null)
			throw new InvariantError("Le joueur n'est pas présent sur son emplacement.");
	}
	
	public void setEngine(EngineService e) {
		super.setEngine(e);
		checkInvariants();
		
		if(!(e == null) && getEngine() == null)
			throw new PostconditionError("L'Engine du Player ne s'est pas enregistrée.");
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
						if(getEngine().getEnvi().cellContent(width_atPre, height_atPre - 1).getCharacter() == null)
							if(getHeight() != height_atPre -1 && getWidth() != width_atPre)
								throw new PostconditionError("Le joueur ne tombe pas.");
			}
	}
}
