package parser;

import java.util.ArrayList;

//@@author A0122061B


public class CommandParserDemo {

	public static void main(String[] args) throws Exception {
		CommandParser cmd = CommandParser.getInstance();
		CommandPackage CPK;
		
		BooleanChecker asd = BooleanChecker.getInstance();
		System.out.println(asd.isOne(0));
		//System.out.println(cmd.getCommandPackage("").endingTime());
		CPK = cmd.getCommandPackage("add something");
		CommandParser cmdP = CommandParser.getInstance();
		CommandPackage cPK1 = cmdP.getCommandPackage("search meeting 10-oct #1");
		System.out.println(cPK1.startingTime());
		System.out.println("+++++++");
		boolean test1 = cPK1.getCommand().equals("create");
		System.out.println("test is = " + test1);
		System.out.println(cmd.getCommandPackage("") == null);
		CPK = cmd.getCommandPackage("plus something 10-oct");
		String test = "11/22";
		String test2 = "/33";
		String test3 = "44/";
		String[] test4 = test3.split("/");
		System.out.println(test4.length);
		CommandPackage cPK3 = cmdP.getCommandPackage("Invalid Input");
		System.out.println(cPK3);
		


		//System.out.println(test.contains("as"));
	}
}




