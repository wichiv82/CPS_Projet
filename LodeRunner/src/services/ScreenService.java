package services;

public interface ScreenService {
	public int getHeight();
	public int getWidth();
	
	/**
	 * pre : 0 <= y < getHeight() 
	 * pre : 0 <= x < getWidth()
	 */
	public Cell cellNature(int x, int y);
	
	
	/**
	 * pre : w > 0
	 * pre : h > 0
	 * post : getWidth() == w
	 * post : getHeight() == h
	 * post : forall i:int in [0...getWidth()[ {
	 * 			forall j:int in [0...getHeight()[ {
	 * 				cellNature(i,j) == Cell.EMP
	 */
	public void init(int w, int h);
	
	
	/**
	 * pre : 0 <= y < getHeight() 
	 * pre : 0 <= x < getWidth()
	 * pre : cellNature(x,y) == Cell.PLT
	 * post : cellNature(x,y) == Cell.HOL
	 * post : forall i:int in [0...getWidth()[ {
	 * 			forall j:int in [0...getHeight()[ {
	 * 				if i!=x and j!=y then cellNature(i,j) == cellNature(i,j)@pre
	 */
	public void dig(int x, int y);
	
	/**
	 * pre : 0 <= y < getHeight() 
	 * pre : 0 <= x < getWidth()
	 * pre : cellNature(x,y) == Cell.HOL
	 * post : cellNature(x,y) == Cell.PLT
	 * post : forall i:int in [0...getWidth()[ {
	 * 			forall j:int in [0...getHeight()[ {
	 * 				if i!=x and j!=y then cellNature(i,j) == cellNature(i,j)@pre
	 */
	public void fill(int x, int y);
}

