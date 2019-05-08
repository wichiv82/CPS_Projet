package contracts;

import java.awt.Point;

import java.util.ArrayList;

import decorators.EngineDecorator;
import services.Command;
import services.EditableScreenService;
import services.EngineService;
import services.GuardService;

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
				if (!(getTreasures().size() == 0))
					throw new InvariantError("La partie est gagné alors qu'il reste " + getTreasures().size() + " trésors.");
				if (!(getGuards().size() == 0))
					throw new InvariantError("La partie est gagné alors qu'il reste " + getGuards().size()  + " Guard en vie.");
				break;
			case LOSS:
				if (alive)
					throw new InvariantError("La partie est perdu alors que le joueur est en vie.");
				break;
			case PLAYING:
				if (getTreasures().size() == 0 && getGuards().size() == 0)
					throw new InvariantError("La partie continue alors que tous les trésors ont été ramassé et tous les gardes tué.");
				if (!alive)
					throw new InvariantError("La partie continue alors que le joueur est mort.");
				break;

			default:
		}
	}
	
	public void init(EditableScreenService e, Point player, ArrayList<Point> guards, ArrayList<Point> treasures) {
		for(Point treasure : treasures)
			if(!(treasure.getX() >= 0 && treasure.getX() < e.getWidth())
			|| !(treasure.getY() >= 0 && treasure.getY() < e.getHeight()))
				throw new PreconditionError("Position Item (" + treasure.getX() + "," + treasure.getY() + ") hors jeu");
		
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
		
		if(!(getShadow().getWidth() == getPlayer().getWidth()
			&& getShadow().getHeight() == getPlayer().getHeight()))
			throw new PostconditionError("Shadow est initialisé en "
					+ "(" + getShadow().getWidth() + "," + getShadow().getHeight() + ") au lieu de"
					+ "(" + getPlayer().getWidth() + "," + getPlayer().getHeight() + ").");
		
		if(getEnvi() == null)
			throw new PostconditionError("EditableScreen ne s'est pas enregistré.");
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
		int number_of_guards_atPre = getGuards().size();
		checkInvariants();
		super.step();
		checkInvariants();
		
		if(!(getScore() + getTreasures().size() == score_atPre + number_of_treasures_atPre))
			throw new PostconditionError("Calcul du score incorrect.");
		
		if(getTreasures().size() == number_of_treasures_atPre - 1 && !getShadow().isAlive())
			throw new PostconditionError("La Shadow n'a pas été crée.");
		
		if(getGuards().size() == number_of_guards_atPre - 1 && getShadow().isAlive())
			throw new PostconditionError("La Shadow n'a pas disparue après avoir tué un Guard.");
	}
}
