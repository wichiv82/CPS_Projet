package test;

import org.junit.Test;

import contracts.ItemContract;
import impl.ItemImpl;
import services.ItemService;
import services.ItemType;

public class ItemTest extends MyTest {

	ItemService item = new ItemContract(new ItemImpl());
	
	public ItemTest() {
		NameClassTest = "ItemTest";
	}
	
	/* Un item ne voit rien, il n'y a donc pas grand chose a tester */
	@Test
	public void init_valide() {
		item.init(0, ItemType.TREASURE, 0, 0);
	}
}
