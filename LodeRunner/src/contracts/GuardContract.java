package contracts;

import decorators.GuardDecorator;
import services.CharacterService;
import services.GuardService;
import services.ScreenService;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	public void checkInvariants() {
		switch(getEnvi().cellNature(getWidth(), getHeight())) {
			case EMP:
			case LAD:
			case HDR:
			case HOL :
				break;
			default:
				throw new InvariantError("L'emplacement (" + getWidth() + "," + getHeight() + ")"
						+ " n'est pas valide : " + getEnvi().cellNature(getWidth(), getHeight()));
		}
		if(getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null)
			throw new InvariantError("Aucun Character n'est présent sur l'emplacement (" + getWidth() + "," + getHeight() + ").");
		if(!(getEnvi().cellContent(getWidth(), getHeight()).getCharacter().equals(delegate)))
			throw new InvariantError("Un autre joueur est sur l'emplacement (" + getWidth() + "," + getHeight() + ").");
	}
	
	
	public void init(ScreenService s, int x, int y, int id, CharacterService target) {
		super.init(s, x, y);
		checkInvariants();
	}
	
	
}
