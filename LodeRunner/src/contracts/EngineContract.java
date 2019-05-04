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
		
		if(!(guards.size() == getGuards().size()))
			throw new PostconditionError("Le nombre de garde devrait être de " + guards.size()
					+ " mais est de " + getGuards().size());
		
		for(int i = 0; i < guards.size(); i++)
			if(!(guards.get(i).getX() == getGuards().get(i).getWidth() && guards.get(i).getY() == getGuards().get(i).getHeight()))
				throw new PostconditionError("le garde n°" + i + " devrait être en "
						+ "(" + guards.get(i).getX() + "," + guards.get(i).getY() + ") mais c'est mal initialisé en "
						+ "(" + getGuards().get(i).getWidth() + "," + getGuards().get(i).getHeight() + ").");
		
		if (!(treasures.size() == getTreasures().size()))
			throw new PostconditionError("Le nombre de trésors devrait être de " + treasures.size()
					+ " mais est de " + getTreasures().size());
		
		for(int i = 0; i < treasures.size(); i++)
			if(!(treasures.get(i).getX() == getTreasures().get(i).getColumn() && treasures.get(i).getY() == getTreasures().get(i).getHeight()))
				throw new PostconditionError("le trésor n°" + i + " devrait être en "
						+ "(" + treasures.get(i).getX() + "," + treasures.get(i).getY() + ") mais c'est mal initialisé en "
						+ "(" + getTreasures().get(i).getColumn() + "," + getTreasures().get(i).getHeight() + ").");
		
		if (!(getEnvi().equals(e)))
			throw new PostconditionError("EditableScreen c'est mal initialisé dans Engine");
	}

	public void setCommand(Command m) {
		Command m_atPre = nextCommand();
		
		checkInvariants();
		super.setCommand(m);
		checkInvariants();
		
		if(!(nextCommand().equals(m))) {
			if(nextCommand().equals(m_atPre))
				throw new PostconditionError("La commande " + m + " n'as pas été enregistrée.");
			else
				throw new PostconditionError("La commande enregistrée est " + nextCommand() + " mais devrait être " + m + ".");
		}
	}
	
	public void step(){
		int score_atPre = getScore();
		int number_of_treasures_atPre = getTreasures().size();
		checkInvariants();
		super.step();
		checkInvariants();
		
		if(!(getScore() + getTreasures().size() == score_atPre + number_of_treasures_atPre))
			throw new PostconditionError("Calcul du score incorrect.");
	}
	
	
}
