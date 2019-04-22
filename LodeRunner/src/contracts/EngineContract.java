package contracts;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import decorators.EngineDecorator;
import services.Command;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.PlayerService;
import services.Status;

public class EngineContract extends EngineDecorator{

	public EngineContract(EngineService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}
	
	public void checkInvariants() {
		switch(getStatus()) {
			case WIN:
				if (getTreasures().size() != 0)
					throw new InvariantError("Status incorrect");
			case LOSS:
				if (getTreasures().size() == 0) // Et joueur pas sur un garde
					throw new InvariantError("Status incorrect");
			case PLAYING:
				if (getTreasures().size() == 0) // ou joueur sur un garde
					throw new InvariantError("Status incorrect");
			default:
				throw new InvariantError("Status inconnu");
		}
	}
	
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		checkInvariants();
		super.init(e, player, guards, treasures);
		checkInvariants();
		
		if(!(super.getPlayer().equals(player)))
			if(!(super.getGuards().equals(guards)))
				if(!(super.getTreasures().equals(treasures)))
					if(!(super.getEnvi().equals((EnvironmentService)e)))
						throw new PostconditionError("le jeu s'est mal initialisé.");
	}

	public void step(){
		
		checkInvariants();
		super.step();
		checkInvariants();
	}
	
	
}
