package impl;

import services.Cell;
import services.ScreenService;

public class ScreenImpl implements ScreenService{
	protected int height;
	protected int width;
	protected Cell[][] cells;
	
	@Override
	public void init(int w, int h) {
		// TODO Auto-generated method stub
		height = h;
		width = w; 
		cells = new Cell[width][height];
		
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				cells[i][j] = Cell.EMP;
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
	public Cell cellNature(int x, int y) {
		// TODO Auto-generated method stub
		return cells[x][y];
	}


	@Override
	public void dig(int x, int y) {
		// TODO Auto-generated method stub
		if (cells[x][y] == Cell.PLT)
			cells[x][y] = Cell.HOL;
		System.out.println("CREUSE "+x +" "+y);
	}

	@Override
	public void fill(int x, int y) {
		// TODO Auto-generated method stub
		if (cells[x][y] == Cell.HOL)
			cells[x][y] = Cell.PLT;
	}
	
}
