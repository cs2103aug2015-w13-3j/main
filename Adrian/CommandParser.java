public class CommandParser {
	public String input;
	public CommandPackage inputData;

	public CommandParser(String command) {
		input = command;
	}
	public CommandPackage getInput() {
		return inputData;
	}
}


