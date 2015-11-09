package parser;


//@@author A0122061B


public class CommandParserDemo {

	public static void main(String[] args) throws Exception {
		CommandParser cmd = CommandParser.getInstance();
		CommandPackage CPK;
		System.out.println(cmd.getCommandPackage("").endingTime());
		CPK = cmd.getCommandPackage("update `asd `starttime `delete");
		System.out.println("Starting time " + CPK.getUpdateSequence().get(2));
		System.out.println();
		System.out.println(cmd.getCommandPackage("").getPhrase().equals(""));
		CPK = cmd.getCommandPackage("plus something 10-oct");
		String test = "11/22";
		String test2 = "/33";
		String test3 = "44/";
		String[] test4 = test3.split("/");
		System.out.println(test4.length);
		//System.out.println(test.contains("as"));
	}
}




