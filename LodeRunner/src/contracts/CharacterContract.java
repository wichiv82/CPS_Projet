package contracts;

import decorators.CharacterDecorator;
import services.Cell;
import services.CharacterService;
import services.ScreenService;

public class CharacterContract extends CharacterDecorator {

	public CharacterContract(CharacterService delegate) {
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
				throw new InvariantError("Cellule Character (" + getWidth() + "," + getHeight() + ") " + getEnvi().cellNature(getWidth(), getHeight()) + " invalide");
		}
		if(getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == null)
			throw new InvariantError("Aucun Character n'est présent sur l'emplacement (" + getWidth() + "," + getHeight() + ").");
		if(!(getEnvi().cellContent(getWidth(), getHeight()).getCharacter().equals(delegate)))
			throw new InvariantError("Un autre joueur est sur l'emplacement (" + getWidth() + "," + getHeight() + ").");
	}
	
	
	public void init(ScreenService s, int x, int y) {
		if(s == null)
			throw new PreconditionError("ScreenService == null");
		if(!(0 <= x && x < s.getWidth()) || !(0 <= y && y < s.getHeight()))
			throw new PreconditionError("Position Character (" + x + "," + y + ") hors jeu");
			
		super.init(s, x, y);
		checkInvariants();
		
		if(!(getWidth() == x && getHeight() == y))
			throw new PostconditionError("Position (" + getWidth() + "," + getHeight() + ")"
					+ " not (" + x + "," + y + ")");
		if(getEnvi() == null)
			throw new PostconditionError("ScreenService not initialize");
		
	}
	
	public void goLeft() {
		int height_atPre = getHeight();
		int width_atPre = getWidth();
		
		checkInvariants();
		super.goLeft();
		checkInvariants();
		
		if (!(getHeight() == height_atPre))
			throw new PostconditionError("Le Character se deplace verticalement pendant un deplacement latéral");
		
		if(width_atPre == 0)
			if(!(getWidth() == width_atPre))
				throw new PostconditionError("L'emplacement (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		switch(getEnvi().cellNature(width_atPre, height_atPre)) {
			case HDR:
			case LAD:
				break;
			default:
				switch(getEnvi().cellNature(width_atPre,  height_atPre -1)) {
					case MTL:
					case PLT:
					case LAD:
						break;
					default:
						if(getEnvi().cellContent(width_atPre, height_atPre -1).getCharacter() == null)
							if(!(getWidth() == width_atPre))
								throw new PostconditionError("Le Character se deplace latéralement pendant une chute libre.");
				}
		}
		
		if(width_atPre != 0) {
			switch(getEnvi().cellNature(width_atPre - 1, height_atPre)) {
				case MTL:
				case PLT:
					break;
				default:
					if(getEnvi().cellContent(width_atPre - 1, height_atPre).getCharacter() == null) {
						switch(getEnvi().cellNature(width_atPre, height_atPre)) {
							case LAD:
							case HDR:
							case HOL:
								if (!(getWidth() == width_atPre - 1))
									throw new PostconditionError("Le Character aurait dû se deplacer en "
											+ "(" + (width_atPre - 1) + "," + height_atPre + ") mais se trouve en "
											+ "(" + getWidth() + "," + getHeight() + ").");
							case EMP:
								switch(getEnvi().cellNature(width_atPre, height_atPre - 1)) {
									case EMP:
									case HOL:
										if(getEnvi().cellContent(getWidth(), getHeight() - 1).getCharacter() != null) {
											break;
										}
									case PLT:
									case MTL:
									case LAD:
										if (!(getWidth() == width_atPre - 1))
											throw new PostconditionError("Le Character aurait dû se deplacer en "
													+ "(" + (width_atPre - 1) + "," + height_atPre + ") mais se trouve en "
													+ "(" + getWidth() + "," + getHeight() + ").");
									default:
							}
							default:
						}
					}
			}
		}
	}
	
	
	
	public void goRight() {
		int height_atPre = getHeight();
		int width_atPre = getWidth();
		
		
		
		checkInvariants();
		super.goRight();
		checkInvariants();
		
		if (!(getHeight() == height_atPre))
			throw new PostconditionError("Le Character se deplace verticalement pendant un deplacement latéral");
		
		if(width_atPre == getEnvi().getWidth() - 1)
			if(!(getWidth() == width_atPre))
				throw new PostconditionError("L'emplacement (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		switch(getEnvi().cellNature(width_atPre, height_atPre)) {
			case HDR:
			case LAD:
				break;
			default:
				switch(getEnvi().cellNature(width_atPre,  height_atPre -1)) {
					case MTL:
					case PLT:
					case LAD:
						break;
					default:
						if(getEnvi().cellContent(width_atPre, height_atPre -1).getCharacter() == null)
							if(!(getWidth() == width_atPre))
								throw new PostconditionError("Le Character se deplace latéralement pendant une chute libre.");
				}
		}
		
		if(width_atPre != getEnvi().getWidth() - 1) {
			switch(getEnvi().cellNature(width_atPre + 1, height_atPre)) {
				case MTL:
				case PLT:
					break;
				default:
					if(getEnvi().cellContent(width_atPre + 1, height_atPre).getCharacter() == null) {
						switch(getEnvi().cellNature(width_atPre, height_atPre)) {
							case LAD:
							case HDR:
							case HOL:
								if (!(getWidth() == width_atPre + 1))
									throw new PostconditionError("Le Character aurait dû se deplacer en "
											+ "(" + (width_atPre + 1) + "," + height_atPre + ") mais se trouve en "
											+ "(" + getWidth() + "," + getHeight() + ").");
							case EMP:
								switch(getEnvi().cellNature(width_atPre, height_atPre - 1)) {
									case EMP:
									case HOL:
										if(getEnvi().cellContent(getWidth(), getHeight() - 1).getCharacter() == null) {
											break;
										}
									case PLT:
									case MTL:
									case LAD:
										if (!(getWidth() == width_atPre + 1))
											throw new PostconditionError("Le Character aurait dû se deplacer en "
													+ "(" + (width_atPre + 1) + "," + height_atPre + ") mais se trouve en "
													+ "(" + getWidth() + "," + getHeight() + ").");
									default:
							}
							default:
						}
						
					}
			}
		}
	}
	
	
	public void goUp() {
		int height_atPre = getHeight();
		int width_atPre = getWidth();
		
		checkInvariants();
		super.goUp();
		checkInvariants();
		
		if(!(getWidth() == width_atPre))
			throw new PostconditionError("Le Character se deplace latéralement pendant un deplacement vertical");
		
		if(height_atPre == getEnvi().getHeight() - 1)
			if(!(getWidth() == width_atPre))
				throw new PostconditionError("L'emplacement (" + getWidth() + "," + getHeight() + ") est hors jeu.");
			
		switch(getEnvi().cellNature(width_atPre, height_atPre)) {
			case HDR:
			case LAD:
				break;
			default:
				switch(getEnvi().cellNature(width_atPre,  height_atPre -1)) {
					case MTL:
					case PLT:
						break;
					default:
						if(getEnvi().cellContent(width_atPre, height_atPre -1).getCharacter() == null)
							if(!(getWidth() == width_atPre))
								throw new PostconditionError("Le Character vole pendant une chute libre.");
				}
		}
		
		if(height_atPre != getEnvi().getHeight() - 1)
			if(getEnvi().cellContent(width_atPre, height_atPre + 1).getCharacter() == null)
				if(getEnvi().cellNature(getWidth(), getHeight()) == Cell.LAD)
					switch(getEnvi().cellNature(width_atPre, height_atPre + 1)) {
						case LAD:
						case HDR:
						case EMP:
							if(!(getHeight() == height_atPre + 1))
								throw new PostconditionError("Le Character aurait dû se deplacer en "
										+ "(" + width_atPre + "," + (height_atPre + 1) + ") mais se trouve en "
										+ "(" + getWidth() + "," + getHeight() + ").");
						default:
					}
	}
	
	
	public void goDown() {
		int height_atPre = getHeight();
		int width_atPre = getWidth();
		
		checkInvariants();
		super.goDown();
		checkInvariants();
		
		if(!(getWidth() == width_atPre))
			throw new PostconditionError("Le Character se deplace latéralement pendant un deplacement vertical");
		
		if(height_atPre == 0)
			if(!(getHeight() == height_atPre))
				throw new PostconditionError("L'emplacement (" + getWidth() + "," + getHeight() + ") est hors jeu.");
		
		if(!(height_atPre == 0))
			if(getEnvi().cellContent(width_atPre, height_atPre - 1).getCharacter() == null)
				switch(getEnvi().cellNature(width_atPre, height_atPre - 1)) {
				case HOL:
				case EMP:
				case LAD:
				case HDR:
					switch(getEnvi().cellNature(width_atPre, height_atPre)) {
						case EMP:
						case LAD:
						case HDR:
						case HOL:
							if(!(getHeight() == height_atPre - 1))
								throw new PostconditionError("Le Character aurait dû se deplacer en "
										+ "(" + width_atPre + "," + (height_atPre + 1) + ") mais se trouve en "
										+ "(" + getWidth() + "," + getHeight() + ").");
						default:
					}
				default:
				}
	}	
}
