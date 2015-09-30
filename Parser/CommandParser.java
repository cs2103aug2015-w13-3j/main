public class CommandParser {

	String NOT_FOUND = "invalid input";
	public String input;
	public String[] inputArr;
	public CommandPackage inputData;
	public ActionLibrary actionLib;

	public CommandParser(String command) {
		input = command;
		inputArr = input.split(" ");
		actionLib = new ActionLibrary();
	}

	public CommandPackage getInput() {
		return inputData;
	}

	public String findAction() {

		if (!callAction(inputArr[0]).equals(NOT_FOUND)) {
			return callAction(inputArr[0]);
		} else if (!callAction(inputArr[inputArr.length - 1]).equals(NOT_FOUND)) {
			return callAction(inputArr[inputArr.length - 1]);
		}

		return "";
	}

	private String callAction(String string) {
		String result = actionLib.find(string);
		return result;
	}
}
