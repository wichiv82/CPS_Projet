package services;


/**
 * inv : forall (x,y) in [0; getWidth()-1] * [0; getHeight()-1]{
 * 			forall Character c1, c2 in CellContent(x, y)²{
 * 				c1 = c2
 * inv : forall (x,y) in [0; getWidth()-1] * [0; getHeight()-1]{
 * 			if cellNature(x, y) in {Cell.MTL, Cell.PLT} then cellContent(x, y) = Null
 * inv : forall (x,y) in [0; getWidth()-1] * [0; getHeight()-1]{
 * 			if Item.Treasure in cellContent(x, y) then cellNature(x, y) = Cell.EMP && cellNature(x, y-1) in {Cell.MTL, Cell.PLT}
 */

public interface EnvironmentService extends ScreenService{
	
	/**
	 * pre : 0 <= y < getHeight() 
	 * pre : 0 <= x < getWidth()
	 */
	public Paire cellContent(int x, int y);
	
	/**
	 * post : forall (x,y) in [0; getWidth()-1] * [0; getHeight()-1]{
	 * 			cellNature(init(e),x,y) = e.cellNature(x,y)
	 */
	public void init(EditableScreenService e);
	
	public void setCellContent(int x, int y, Paire p);
	
}
