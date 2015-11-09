package parser;

import java.util.ArrayList;

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
    
    protected static BooleanChecker getInstance() {

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
    protected boolean isValidCommand(String command) {
	return !command.equalsIgnoreCase(NOT_FOUND);
    }

    protected boolean isOneCommandFormat(String commandName) {
	return commandName.equalsIgnoreCase("undo") || commandName.equalsIgnoreCase("exit")
	       || commandName.equalsIgnoreCase("redo");
    }

    protected boolean isSearchCommand(String commandName) {
	return commandName.equals("search");
    }

    protected boolean isUpdateCommand(String commandName) {
	return commandName.equals("update");
    }

    protected boolean isCreateCommand(String commandName) {
	return commandName.equals("create");
    }

    protected boolean isClearAllCommand(String commandName, ArrayList<String> inputArr) {
	return commandName.equals("clear") && inputArr.size() == 0;
    }

    protected boolean isArrayEmptyAndInvalid(ArrayList<String> inputArr) {
	return inputArr.size() == 0;
    }

    protected boolean remove(int index, ArrayList<String> inputArr) {
	inputArr.remove(index);
	return true;
    }

    protected boolean isDateUpdateSequence(CommandPackage inputData) {
	return inputData.getUpdateSequence().size() < 4;
    }

    protected boolean isZero(int numberOfTime) {
	return numberOfTime == 0;
    }

    protected boolean isTwo(int numberOfDates) {
	return numberOfDates == 2;
    }

    protected boolean isOne(int numberOfTime) {
	return numberOfTime == 1;
    }

}
