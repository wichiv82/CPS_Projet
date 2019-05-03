package services;


/**
 * getEnvi().cellNature(getWidth(), getHeight()) in {Cell.EMP, Cell.HOL, Cell.LAD, Cell.HDR}
 * getEnvi().cellContent(getWidth(), getHeight()) in {Cell.EMP, Cell.HOL, Cell.LAD, Cell.HDR}
 * Character x in getEnvi().cellContent(getHeight(), getWidth()) -> x = this
 */

public interface CharacterService {
	
	public EnvironmentService getEnvi();
	public int getHeight();
	public int getWidth();
	
	/**
	 * pre : s.cellNature(x, y) = Cell.EMP
	 */
	public void init(ScreenService s, int x, int y);
	
	/**
     * post : getHeigth() == getHeight()@pre
     * post : getWidth() == 0 -> getWidth() == getWidth()@pre
     * post : getEnvi().CellNature(getWidth()@pre -1, getHeight()@pre) in {Cell.MTL,Cell.PLT}
     *        -> getWidth() == getWidth()@pre
     * post : (getEnvi().cellContent(getWidth()@pre -1, getHeight()).getCharacter() == null &&
     * 		  	getEnvi().CellNature(getWidth()@pre, getHeight()@pre) in {Cell.LAD, Cell.HDR, Cell.HOL}) ||
     * 		  (getEnvi().cellContent(getWidth()@pre -1, getHeight()).getCharacter() == null &&
     * 		  	getEnvi().CellNature(getWidth()@pre, getHeight()@pre) == Cell.EMP && 
     * 		  	((getEnvi().CellNature(getWidth()@pre, getHeight()@pre -1) in {Cell.PLT, Cell.MTL, Cell.LAD}) ||
     * 			 (getEnvi().CellContent(getWidth()@pre, getHeight()@pre -1).getCharacter() == null)))
     * 		  -> getWidth() == getWidth()@pre-1, 
     * 			 getEnvi().cellContent(getWidth()@pre, getHeight()@pre).getCharacter() == null,     
     *        	 getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == this	
     *        
     * post : getEnvi().cellContent(getWidth()@pre -1, getHeight()).getCharacter() == null &&
     * 		  getEnvi().CellNature(getWidth()@pre, getHeight()@pre) == Cell.EMP &&
     *        getEnvi().CellNature(getWidth()@pre, getHeight()@pre -1) == Cell.HOL &&
     *        getEnvi().CellContent(getWidth()@pre, getHeight()@pre -1).getCharacter() != null
     * 		  -> getWidth() == getWidth()@pre
     */
	public void goLeft();
	
	/**
     * post : getHeigth() == getHeight()@pre
     * post : getWidth() == getEnvi().getWidth()-1 -> getWidth() == getWidth()@pre
     * post : getEnvi().CellNature(getWidth()@pre +1, getHeight()@pre) in {Cell.MTL,Cell.PLT}
     *        -> getWidth() == getWidth()@pre
     * post : (getEnvi().cellContent(getWidth()@pre +1, getHeight()).getCharacter() == null &&
     * 		  	getEnvi().CellNature(getWidth()@pre, getHeight()@pre) in {Cell.LAD, Cell.HDR, Cell.HOL}) ||
     * 		  (getEnvi().cellContent(getWidth()@pre +1, getHeight()).getCharacter() == null &&
     * 		  	getEnvi().CellNature(getWidth()@pre, getHeight()@pre) == Cell.EMP && 
     * 		  	((getEnvi().CellNature(getWidth()@pre, getHeight()@pre -1) in {Cell.PLT, Cell.MTL, Cell.LAD}) ||
     * 			 (getEnvi().CellContent(getWidth()@pre, getHeight()@pre -1).getCharacter() == null)))
     * 		  -> getWidth() == getWidth()@pre+1, 
     * 			 getEnvi().cellContent(getWidth()@pre, getHeight()@pre).getCharacter() == null,     
     *        	 getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == this	
     *        
     * post : getEnvi().cellContent(getWidth()@pre +1, getHeight()).getCharacter() == null &&
     * 		  getEnvi().CellNature(getWidth()@pre, getHeight()@pre) == Cell.EMP &&
     *        getEnvi().CellNature(getWidth()@pre, getHeight()@pre -1) == Cell.HOL &&
     *        getEnvi().CellContent(getWidth()@pre, getHeight()@pre -1).getCharacter() != null
     * 		  -> getWidth() == getWidth()@pre
     */
	public void goRight();
	
	/**
	 * post : getWidth() == getWidth()@pre
	 * post : getHeight() == getEnvi().getHeight()-1 -> getHeight() == getHeight()@pre
	 * post : getEnvi().cellContent(getWidth()@pre, getHeight()@pre +1).getCharacter() == null &&
	 * 		  getEnvi().cellNature(getWidth()@pre, getHeight()@pre + 1) in {Cell.EMP, Cell.HDR, Cell.LAD} &&
	 * 		  getEnvi().cellNature(getWidth()@pre, getHeight()@pre) == Cell.LAD
	 * 		  -> getHeight() == getHeight()@pre +1, 
	 * 			 getEnvi().cellContent(getWidth()@pre, getHeight()@pre).getCharacter() == null,
	 * 			 getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == this
	 */
	public void goUp();
	
	/**
	 * post : getWidth() == getWidth()@pre
	 * post : getHeight() == 0 -> getHeight() == getHeight()@pre
	 * post : getEnvi().cellContent(getWidth()@pre, getHeight()@pre-1).getCharacter() == null &&
	 * 		  getEnvi().cellNature(getWidth()@pre, getHeight()@pre-1) in {Cell.HOL, Cell.EMP, Cell.LAD, Cell.HDR} && 
	 * 		  getEnvi().cellNature(getWidth()@pre, getHeight()@pre) in {Cell.HOL, Cell.EMP, Cell.LAD, Cell.HDR}
	 * 		  -> getHeight() == getHeight()@pre -1,
	 * 			 getEnvi().cellContent(getWidth()@pre, getHeight()@pre).getCharacter() == null,     
     *        	 getEnvi().cellContent(getWidth(), getHeight()).getCharacter() == this
	 */
	public void goDown();
	
}
