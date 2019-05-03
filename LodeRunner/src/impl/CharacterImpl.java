package impl;

import java.util.ArrayList;
import services.Cell;
import services.CharacterService;
import services.EditableScreenService;
import services.EnvironmentService;
import services.ScreenService;

public class CharacterImpl implements CharacterService{

	protected EnvironmentService envi;
	protected int height;
	protected int width;
	
	protected ArrayList<Cell> cell_libre;//{Cell.EMP, Cell.LAD, Cell.HDR, Cell.HOL};
	protected ArrayList<Cell> cell_non_libre; //{Cell.MTL, Cell.PLT};
	
	@Override
	public void init(ScreenService s, int x, int y) {
		// TODO Auto-generated method stub
		envi = new EnvironmentImpl();
		envi.init((EditableScreenService)s);
		width = x;
		height = y;
		
		getEnvi().cellContent(x, y).setCharacter(this);
		
		cell_libre = new ArrayList<Cell>();
		cell_libre.add(Cell.EMP);
		cell_libre.add(Cell.LAD);
		cell_libre.add(Cell.HDR);
		cell_libre.add(Cell.HOL);
		
		cell_non_libre = new ArrayList<Cell>();
		cell_non_libre.add(Cell.MTL);
		cell_non_libre.add(Cell.PLT);
		cell_non_libre.add(Cell.LAD);
	}
	
	@Override
	public EnvironmentService getEnvi() {
		// TODO Auto-generated method stub
		return envi;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}
	
	
	@Override
	public void goLeft() {
		if(getWidth() != 0) {
			switch(getEnvi().cellNature(getWidth() - 1, getHeight())) {
				case MTL:
				case PLT:
					break;
				default:
					if(getEnvi().cellContent(getWidth() - 1, getHeight()).getCharacter() == null) {
						switch(getEnvi().cellNature(getWidth(), getHeight())) {
							case LAD:
							case HDR:
							case HOL:
								getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
								width--;
								getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
								break;
							case EMP:
								switch(getEnvi().cellNature(getWidth(), getHeight() - 1)) {
									case HOL:
										if(getEnvi().cellContent(getWidth(), getHeight() - 1).getCharacter() != null) {
											break;
										}
									case PLT:
									case MTL:
									case LAD:
										getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
										width--;
										getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
										break;
									default:
							}
							default:
						}
					}
			}
		}
	}

	@Override
	public void goRight() {
		if(getWidth() != getEnvi().getWidth() - 1) {
			switch(getEnvi().cellNature(getWidth() + 1, getHeight())) {
				case MTL:
				case PLT:
					break;
				default:
					if(getEnvi().cellContent(getWidth() + 1, getHeight()).getCharacter() == null) {
						switch(getEnvi().cellNature(getWidth(), getHeight())) {
							case LAD:
							case HDR:
							case HOL:
								getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
								width++;
								getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
								break;
							case EMP:
								switch(getEnvi().cellNature(getWidth(), getHeight() - 1)) {
									case HOL:
										if(getEnvi().cellContent(getWidth(), getHeight() - 1).getCharacter() != null) {
											break;
										}
									case PLT:
									case MTL:
									case LAD:
										getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
										width++;
										getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
									default:
							}
							default:
						}
					}
			}
		}
	}

	@Override
	public void goUp() {
		if(getHeight() != getEnvi().getHeight() - 1)
			if(getEnvi().cellContent(getWidth(), getHeight() + 1).getCharacter() == null &&
					getEnvi().cellNature(getWidth(), getHeight()) == Cell.LAD)
				switch(getEnvi().cellNature(getWidth(), getHeight() + 1)) {
					case EMP:
					case HDR:
					case LAD:
						getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
						height++;
						getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						break;
					default:
						break;
				}
	}

	@Override
	public void goDown() {
		if(!(getHeight() == 0))
			if(getEnvi().cellContent(getWidth(), getHeight() - 1).getCharacter() == null)
				switch(getEnvi().cellNature(getWidth(), getHeight() - 1)) {
				case HOL:
				case EMP:
				case LAD:
				case HDR:
					switch(getEnvi().cellNature(getWidth(), getHeight())) {
						case EMP:
						case LAD:
						case HDR:
						case HOL:
							getEnvi().cellContent(getWidth(), getHeight()).setCharacter(null);
							height--;
							getEnvi().cellContent(getWidth(), getHeight()).setCharacter(this);
						default:
					}
				default:
				}
	}	
		
}