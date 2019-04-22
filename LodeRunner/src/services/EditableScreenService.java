package services;

public interface EditableScreenService extends ScreenService{
	/**
	 * inv : forall (x,y) in [0; getWidth()-1] * [0; getHeight()-1]{
	 * 			CellNeture(x, y) != Cell.HOL
	 * inv : forall x in [0; getWidth()-1] {
	 * 			CellNeture(x, 0) != Cell.MTL
	 */
	public boolean isPlayable();
	
	/**
	 * pre : 0 <= y < getHeight() 
	 * pre : 0 <= x < getWidth()
	 * 
	 * inv : CellNature(SetNature(x,y,c), x, y) = c
	 * inv : forall (x,y) in [0; getWidth()-1] * [0; getHeight()-1]{
	 * 			if x!=u or y!=v then CellNature(SetNature(u,v,c), x, y) = CellNature(x,y)
	 */
	public void setNature(int x, int y, Cell c) ;
	
	public void init(String file_name);
	
}
