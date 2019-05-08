package test;

import contracts.ContractError;

public abstract class MyTest {

	String NameClassTest;
	
	public void message(String method, ContractError e) {
		String[] parse = e.toString().split(": ");
		parse[1] = parse[1].replace(" ", "").replace("failed", "Failed");
		System.out.println( NameClassTest + "/" + method + " : [" + parse[1] + "] " + parse[2] + ".");
	}
	
}
