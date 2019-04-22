package services;


/**
 * getEnvi().cellNature(getHeight(), getWidth()) in {Cell.EMP, Cell.HOL, Cell.LAD, Cell.HDR}
 * getEnvi().cellContent(getHeight(), getWidth()) in {Cell.EMP, Cell.HOL, Cell.LAD, Cell.HDR}
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
     * post : getHeigth(goLeft()) = getHeight()
     *        getWidth() == 0 -> getWidth(goLeft()) == getWidth()
     *        Environnement::CellNature(getEnvi(),getWidth()-1,getHeight()) \in {Cell.MTL,Cell.PLT,Cell.LAD}
     *              \implies getWidth(goLeft()) == getWidth()
     *          Environnement::CellNature(getEnvi(),getWidth(),getHeight()) \not \in {Cell.HDR,Cell.LAD} &&
     *                 Environnement::CellNature(getEnvi(),getWidth(),getHeight()-1) \not \in {Cell.MTL,Cell.PLT} &&
     *                 \not \exists c : Character \in Environment::CellContent(getEnvi(),getWidth(),getHeight()-1)
     *                  \implies getWidth(goLeft()) == getWidth()
     *          \exists c : Character \in Environment::CellContent(getEnvi(),getWidth()-1,getHeight())
     *            \implies getWidth(goLeft()) == getWidth()
     *          getWidth() != 0 &&
     *            Environnement::CellNature(getEnvi(),getWidth()-1,getHeight()) \not \in {Cell.MTL,Cell.PLT} &&
     *            Environnement::CellNature(getEnvi(),getWidth(),getHeight()) \in {Cell.LAD,Cell.HDR} ||
     *            Environnement::CellNature(getEnvi(),getWidth(),getHeight()-1) \in {Cell.MTL,Cell.PLT,Cell.LAD} ||
     *            \exists c : Character \in Environment::CellContent(getEnvi(),getWidth(),getHeight()-1) &&
     *            \not \exists c : Character \in Environment::CellContent(getEnvi(),getWidth()-1,getHeight())
     *            \implies getWidth(goLeft()) == getWidth()-1
     *         
     */
	public void goLeft();
	
	public void goRight();
	
	public void goUp();
	
	public void goDown();
	
}
