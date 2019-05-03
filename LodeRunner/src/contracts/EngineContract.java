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
		boolean alive = true;
		for(GuardService guard : getGuards())
			if(guard.getWidth() == getPlayer().getWidth() && guard.getHeight() == getPlayer().getHeight())
				alive = false;

		switch(getStatus()) {
			case WIN:
				if (getTreasures().size() != 0)
					throw new InvariantError("La partie est gagné alors qu'il reste " + getTreasures().size() + " trésors.");
				break;
			case LOSS:
				if (alive)
					throw new InvariantError("La partie est perdu alors que le joueur est en vie.");
				break;
			case PLAYING:
				if (getTreasures().size() == 0)
					throw new InvariantError("La partie continue alors que tous les trésors ont été ramassé.");
				if (!alive)
					throw new InvariantError("La partie continue alors que le joueur est mort.");
				break;

			default:
				throw new InvariantError("Le status " + getStatus() + " n'est pas reconnu.");
		}
	}
	
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		super.init(e, player, guards, treasures);
		checkInvariants();
		
		if(!(getPlayer().getWidth() == player.getX() && getPlayer().getHeight() == player.getY()))
			throw new PostconditionError("Le player aurait dû être en "
					+ "(" + player.getX() + "," + player.getY() + ") "
					+ "mais a été mal initialisé en (" + getPlayer().getWidth() + "," + getPlayer().getHeight() + ").");
		
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
