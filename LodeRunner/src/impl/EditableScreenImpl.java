package impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import services.Cell;
import services.EditableScreenService;

public class EditableScreenImpl extends ScreenImpl implements EditableScreenService{

	private boolean playable;
	
	public void init(int w, int h) {
		super.init(w, h);
		// TODO Auto-generated constructor stub
		playable = false;
	}
	
	public void init(String file_name) {
		 
		try{
			InputStream flux = new FileInputStream(file_name); 
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			
			String[] parse = buff.readLine().split(" ");
			super.init(Integer.parseInt(parse[0]), Integer.parseInt(parse[1]));
			for(int j = getHeight() - 1; j >= 0; j--) {
				parse = buff.readLine().split(" ");
				for(int i = 0; i < getWidth(); i++) {
					switch(parse[i]) {
						case "EMP":
							setNature(i, j, Cell.EMP);
							break;
						case "PLT":
							setNature(i, j, Cell.PLT);
							break;
						case "MTL":
							setNature(i, j, Cell.MTL);
							break;
						case "LAD":
							setNature(i, j, Cell.LAD);
							break;
						case "HDR":
							setNature(i, j, Cell.HDR);
							break;
						case "HOL":
							setNature(i, j, Cell.HOL);
							break;
						default:
					}
				}
			}
			buff.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
		
	}
	
	public boolean isPlayable() {
		return playable;
	}

	public void setNature(int x, int y, Cell c) {
		cells[x][y] = c;
		
		playable = true;
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				if (cells[i][j] == Cell.HOL) {
					playable = false;
					break;
				}
				
				if(j == height-1 && cells[i][j] != Cell.MTL) {
					playable = false;
					break;
				}
			}
		}
	}
}
