package impl;


import services.EditableScreenService;
import services.EnvironmentService;
import services.Paire;

public class EnvironmentImpl extends ScreenImpl implements EnvironmentService{
	
	private Paire[][] cell_content;
	
	public void init(EditableScreenService e) {
		super.init(e.getWidth(), e.getHeight());
		cell_content = new Paire[e.getWidth()][e.getHeight()];
		for(int i = 0; i < e.getWidth(); i++)
			for(int j = 0; j < e.getHeight(); j++) {
				cells[i][j] = e.cellNature(i, j);
				cell_content[i][j] = new Paire(null, null);
			}
				
	}

	
	public Paire cellContent(int x, int y) {
		return cell_content[x][y];
	}
}
