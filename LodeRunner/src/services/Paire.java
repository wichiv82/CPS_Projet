package services;

public class Paire {
	private CharacterService character;
	private ItemService item;
	
	public Paire(CharacterService c, ItemService i) {
		this.character = c;
		this.item = i;
	}
	
	public CharacterService getCharacter() {
		return character;
	}
	
	public void setCharacter(CharacterService c) {
		character = c;
	}
	
	public ItemService getItem() {
		return item;
	}
	
	public void setItem(ItemService i) {
		this.item = i;
	}
	
	public void removeCharacter() {
		character = null;
	}
	
	public void removeItem() {
		item = null;
	}
	
}
