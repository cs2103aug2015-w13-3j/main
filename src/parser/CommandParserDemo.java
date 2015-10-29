package parser;

public class CommandParserDemo {

	public static void main(String[] args) throws Exception {
		CommandParser cmd = new CommandParser();
		cmd.getCommandPackage("add meeting discussion 10-oct 20-oct #1");
		System.out.println(cmd.getCommandPackage("").endingTime());
		cmd.getCommandPackage("shopping chicken 10pm 20-nov 12pm 25-jan vege #1 add");
		cmd.getCommandPackage("delete 1");
		cmd.getCommandPackage("update ~eat bread ~task name ~eat chicken");
		System.out.println();
		cmd.getCommandPackage("clear");
		System.out.println();
		System.out.println(cmd.getCommandPackage("").getPhrase().equals(""));
	}
}
