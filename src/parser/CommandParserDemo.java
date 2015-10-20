package parser;

public class CommandParserDemo {

	public static void main(String[] args) throws Exception {
		CommandParser cmd = new CommandParser();
		cmd.getCommandPackage("add i love teemo");
		System.out.println(cmd.getCommandPackage("teemo hello 9pm add").endingTime());
		cmd.getCommandPackage("teemo loves chicken #1 search");
		cmd.getCommandPackage("delete 1");
		cmd.getCommandPackage("update ~eat bread ~task name ~eat chicken");
		cmd.getCommandPackage("undo");
		System.out.println();
		System.out.println(cmd.getCommandPackage("").getPhrase().equals(""));
	}
}
