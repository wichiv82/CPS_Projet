package contracts;

import decorators.ShadowDecorator;
import services.CharacterService;

public class ShadowContract extends ShadowDecorator{

	public ShadowContract(CharacterService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

}
