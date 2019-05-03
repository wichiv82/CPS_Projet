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
		this.checkInvarinats();
		this.getDelegate().init(id, nature, x, y);
		this.checkInvarinats();
		
		if(!(this.getDelegate().getId() == id))
			if(!(this.getDelegate().getNature().equals(nature)))
				if(!(this.getDelegate().getColumn() == x))
					if(!(this.getDelegate().getHeight() == y))
						throw new PostconditionError("le trésor s'est mal initialisé.");
		
	}
}
