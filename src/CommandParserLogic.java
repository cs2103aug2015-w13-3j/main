
public class CommandParserLogic {
	public String input;
	public CommandPackage inputData;

	public CommandParserLogic(String command) {
		input = command;
		inputData=prosessCommand(input);
		
	}
	
	public CommandPackage getInput() {
		
		return inputData;
	}
	
	public static CommandPackage prosessCommand(String userCommand) {
		
		CommandPackage commandInfo= new CommandPackage(userCommand);
		
		String commandTypeString = getFirstWord(userCommand);
	    
		userCommand=removeFirstWord(userCommand);
		
		if(commandTypeString.equalsIgnoreCase("add")){
			
			String taskName = userCommand.substring(userCommand.indexOf("\"")+1, userCommand.indexOf("\"",userCommand.indexOf("\"")+1) );	
			userCommand.replace(taskName, "").trim();
			
			//tbc
			
			commandInfo.setTaskName(taskName);
			commandInfo.setCommandType(commandTypeString);
			
		}else if(commandTypeString.equalsIgnoreCase("sort")||
				commandTypeString.equalsIgnoreCase("clear")||
				commandTypeString.equalsIgnoreCase("display")){
			commandInfo.setCommandType(commandTypeString);
		}else if(commandTypeString.equalsIgnoreCase("delete")){
			String taskName = userCommand.substring(userCommand.indexOf("\"")+1, userCommand.indexOf("\"",userCommand.indexOf("\"")+1) );	
			userCommand.replace(taskName, "").trim();
			
			//tbc
			
			commandInfo.setTaskName(taskName);
			commandInfo.setCommandType(commandTypeString);
		}else if(commandTypeString.equalsIgnoreCase("search")){
			commandInfo.setKeywordForSearch(userCommand);
		}
		
		
		
		
		
		return commandInfo;
	}
	
	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}

	private static String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}
}


