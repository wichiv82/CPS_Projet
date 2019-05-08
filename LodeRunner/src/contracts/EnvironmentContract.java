package contracts;

import java.util.ArrayList;

import decorators.EnvironmentDecorator;
import services.Cell;
import services.CharacterService;
import services.EditableScreenService;
import services.EnvironmentService;
import services.ItemService;
import services.Paire;

public class EnvironmentContract extends EnvironmentDecorator{

	public EnvironmentContract(EnvironmentService delegate) {
		super(delegate);
	}

	public void checkInvariants() {
		for(int i = 0; i < getWidth(); i ++)
			for(int j = 0; j < getHeight(); j++) {
				if(cellContent(i,j) == null)
					throw new InvariantError("le contenu de la cellule "+ i +"," + j + " n'est pas initialisé.");
				if(cellContent(i, j).getCharacter() != null && !(cellContent(i, j).getCharacter().equals(cellContent(i, j).getCharacter())))
					throw new InvariantError("Environment::cellContent n'est pas déterministe.");
				switch(cellNature(i, j)) {
					case MTL:
					case PLT:
						if(!(cellContent(i, j).getCharacter() == null && cellContent(i, j).getItem() == null))
							throw new InvariantError("Un joueur ou un objet est sur un emplacement indisponible");
					default:
				
				}
				if(!(cellContent(i, j).getItem() == null))
					switch(cellNature(i, j - 1)) {
						case PLT:
						case MTL:
						case LAD:
						case HOL:
							if(cellNature(i, j) == Cell.EMP) break;
						default:
							throw new InvariantError("Un trésor est dans le vide ou sur un emplacement indisponible.");
					}
			}
	}
	
	public void init(EditableScreenService edit){
		super.init(edit);
		checkInvariants();
		
		if(!(edit.getWidth() == getWidth()))
			throw new PostconditionError("La largeur s'est mal initialisée et est de " + getWidth() 
					+ " au lieu de " + edit.getWidth() + ".");
		if(!(edit.getHeight() == getHeight()))
			throw new PostconditionError("La hauteur s'est mal initialisée et est de " + getHeight()
					+ " au lieu de " + edit.getHeight() + ".");
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++) {
				if(!(cellContent(i,j).getCharacter() == null))
					throw new InvariantError("Un Character à été créé dans la cellule ("+ i +"," + j + ") sans raison.");
				if(!(cellContent(i,j).getItem() == null))
					throw new InvariantError("Un Item à été créé dans la cellule ("+ i +"," + j + ") sans raison.");
				if(!(cellNature(i, j) == edit.cellNature(i, j)))
					throw new PostconditionError("La cellule (" + i + ", " + j + ") est" + cellNature(i, j) 
							+ " mais devrait être " + edit.cellNature(i, j) + ".");
			}
	}
	
	public void setCellContent(int x, int y, Paire p) {
		ArrayList<ArrayList<CharacterService>> character_atPre = new ArrayList<>();
		ArrayList<ArrayList<ItemService>> item_atPre = new ArrayList<>();
		
		for(int i = 0; i < getWidth(); i++) {
			character_atPre.add(new ArrayList<CharacterService>());
			item_atPre.add(new ArrayList<ItemService>());			
			for(int j = 0; j < getHeight(); j++) {
				character_atPre.get(i).add(cellContent(i, j).getCharacter());
				item_atPre.get(i).add(cellContent(i, j).getItem());
			}
		}
		
		checkInvariants();
		super.setCellContent(x, y, p);
		checkInvariants();
		
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++) {
				if(i == x && j == y) {
					if(cellContent(i,j).getCharacter() == null && !(p.getCharacter() == null))
						throw new PostconditionError("Le Character n'a pas été placé.");
					if(!(cellContent(i,j).getCharacter() == null) && p.getCharacter() == null)
						throw new PostconditionError("Un Character a été placé sans raison.");
//					if(!(cellContent(i,j).getCharacter().equals(p.getCharacter()))) {
//						if(cellContent(i,j).getCharacter().equals(character_atPre.get(i).get(j)))
//							throw new PostconditionError("Le Character n'as pas été modifié en (" + i + "," + j + ").");
//						else
//							throw new PostconditionError("Le Character en (" + i + "," + j + ") n'as pas été modifié comme voulu.");
//					}
					if(cellContent(i,j).getItem() == null && !(p.getItem() == null))
						throw new PostconditionError("L'Item n'a pas été placé.");
					if(!(cellContent(i,j).getItem() == null) && p.getItem() == null)
						throw new PostconditionError("Un Item a été placé sans raison.");
//					if(!(cellContent(i,j).getItem().equals(p.getItem()))) {
//						if(cellContent(i,j).getItem().equals(item_atPre.get(i).get(j)))
//							throw new PostconditionError("L'Item n'as pas été modifié en (" + i + "," + j + ").");
//						else
//							throw new PostconditionError("L'Item en (" + i + "," + j + ") n'as pas été modifié comme voulu.");
				}
				else {
					if(cellContent(i,j).getCharacter() == null && !(character_atPre.get(i).get(j) == null))
						throw new PostconditionError("Un Character a été supprimé sans raison.");
					if(!(cellContent(i,j).getCharacter() == null) && character_atPre.get(i).get(j) == null)
						throw new PostconditionError("Un Character a été créé sans raison.");
//					if(!(cellContent(i,j).getCharacter().equals(character_atPre.get(i).get(j))))
//						throw new PostconditionError("La présence d'un character à été modifié dans la cellule (" + i + "," + j + ")"
//								+ " alors que la cellule cible est (" + x + "," + y + ").");
					if(cellContent(i,j).getItem() == null && !(item_atPre.get(i).get(j) == null))
						throw new PostconditionError("Un Item a été supprimé sans raison.");
					if(!(cellContent(i,j).getItem() == null) && item_atPre.get(i).get(j) == null)
						throw new PostconditionError("Un Item a été créé sans raison.");
//					if(!(cellContent(i,j).getItem().equals(item_atPre.get(i).get(j))))
//						throw new PostconditionError("La présence d'un item à été modifié dans la cellule (" + i + "," + j + ")"
//								+ " alors que la cellule cible est (" + x + "," + y +").");
				}
			}
	}
}
