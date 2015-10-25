package parser;

public class CommandParserDemo {

	public static void main(String[] args) throws Exception {
		CommandParser cmd = new CommandParser();
		cmd.getCommandPackage("add i love teemo 8am 9pm");
		System.out.println(cmd.getCommandPackage("").endingTime());
		cmd.getCommandPackage("teemo loves chicken #1 search");
		cmd.getCommandPackage("delete 1");
		cmd.getCommandPackage("update ~eat bread ~task name ~eat chicken");
		System.out.println();
		cmd.getCommandPackage("undo");
		System.out.println();
		System.out.println(cmd.getCommandPackage("").getPhrase().equals(""));
	}
}
