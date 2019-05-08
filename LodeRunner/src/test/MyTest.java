package test;

import java.awt.Point;
import java.util.ArrayList;

import contracts.ContractError;
import services.Cell;
import services.EditableScreenService;
import services.EngineService;
import services.GuardService;
import services.ScreenService;
import services.ShadowService;

public abstract class MyTest {

	String NameClassTest;

	EditableScreenService edit;
	ShadowService shadow;
	GuardService guard;
	EngineService engine;
	ArrayList<Point> guards;
	ArrayList<Point> treasures;
	ScreenService screen;
	
	Cell[] platform = {Cell.LAD, Cell.MTL, Cell.PLT};
	Cell[] valid = {Cell.EMP, Cell.HDR, Cell.LAD};
	Cell[] unvalid = {Cell.MTL, Cell.PLT, Cell.HOL};
	
	public void message(String method, ContractError e) {
		String[] parse = e.toString().split(": ");
		parse[1] = parse[1].replace(" ", "").replace("failed", "Failed");
		System.out.println( NameClassTest + "/" + method + " : [" + parse[1] + "] " + parse[2] + ".");
	}
	
}
