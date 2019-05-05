package contracts;

import decorators.ItemDecorator;

import services.ItemService;
import services.ItemType;

public class ItemContract extends ItemDecorator{

	public ItemContract(ItemService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	public void checkInvarinats() {
	}
	
	public void init(int id, ItemType nature, int x, int y) {
		this.getDelegate().init(id, nature, x, y);
		this.checkInvarinats();
		
		if(!(getId() == id))
			throw new PostconditionError("Le trésor enregistré a l'ID n°" + getId() + " Au lieu du n°" + id + ".");
		if(!(getNature() == nature))
			throw new PostconditionError("Le trésor enregistré est de nature " + getNature() + " et non de nature " + nature + ".");
		if(!(getColumn() == x && getHeight() == y))
			throw new PostconditionError("Le trésor est sur la cellule (" + getColumn() + "," + getHeight() + ")"
					+ " au lieu d'être sur la cellule (" + x + "," + y + ").");
	}
}
