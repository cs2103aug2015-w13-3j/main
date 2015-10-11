package parser;

public class CommandParserDemo {

	public static void main(String[] args) throws Exception {
		CommandParser cmd = new CommandParser();
		cmd.getCommandPackage("add i love teemo");
		cmd.getCommandPackage("teemo 12-Oct hello 10-OCT add ");
		cmd.getCommandPackage("teemo loves chicken #1 search");
		cmd.getCommandPackage("delete 1");
		cmd.getCommandPackage("update ~eat bread ~task name ~eat chicken");
	}
}
