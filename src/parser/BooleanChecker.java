package parser;

import java.util.ArrayList;
//@@author A0122061B
public class BooleanChecker {

    /*
     * ====================================================================
     * Magic Constants
     * ====================================================================
     */
    private final String NOT_FOUND = "invalid input";
    private static BooleanChecker theOne = null;
    

    /*
     * ====================================================================
     * Constructors
     * ====================================================================
     */

    private BooleanChecker() {

    }
    
    public static BooleanChecker getInstance() {

	if (theOne == null) {
	    theOne = new BooleanChecker();
	}
	return theOne;
    }
    /*
     * ====================================================================
     * Methods
     * ====================================================================
     */
    public boolean isValidCommand(String command) {
	return !command.equalsIgnoreCase(NOT_FOUND);
    }

    public boolean isOneCommandFormat(String commandName) {
	return commandName.equalsIgnoreCase("undo") || commandName.equalsIgnoreCase("exit")
	       || commandName.equalsIgnoreCase("redo");
    }

    public boolean isSearchCommand(String commandName) {
	return commandName.equals("search");
    }

    public boolean isUpdateCommand(String commandName) {
	return commandName.equals("update");
    }

    public boolean isCreateCommand(String commandName) {
	return commandName.equals("create");
    }

    public boolean isClearAllCommand(String commandName, ArrayList<String> inputArr) {
	return commandName.equals("clear") && inputArr.size() == 0;
    }

    public boolean isArrayEmptyAndInvalid(ArrayList<String> inputArr) {
	return inputArr.size() == 0;
    }


    public boolean isDateUpdateSequence(CommandPackage inputData) {
	return inputData.getUpdateSequence().size() < 4;
    }

    public boolean isZero(int numberOfTime) {
	return numberOfTime == 0;
    }

    public boolean isTwo(int numberOfDates) {
	return numberOfDates == 2;
    }

    public boolean isOne(int numberOfTime) {
	return numberOfTime == 1;
    }

}
