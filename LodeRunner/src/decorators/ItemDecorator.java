package decorators;

import services.ItemService;
import services.ItemType;

public class ItemDecorator implements ItemService{

	protected ItemService delegate;
	
	public ItemDecorator(ItemService delegate) {
		// TODO Auto-generated constructor stub
		this.delegate = delegate;
	}
	
	public ItemService getDelegate() {
		return delegate;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return delegate.getId();
	}

	@Override
	public ItemType getNature() {
		// TODO Auto-generated method stub
		return delegate.getNature();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return delegate.getHeight();
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return delegate.getColumn();
	}

	@Override
	public void init(int id, ItemType nature, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
