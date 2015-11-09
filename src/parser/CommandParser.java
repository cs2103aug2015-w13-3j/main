package parser;

import java.util.ArrayList;

import org.joda.time.DateTime;

import logic.LogicClass;

// @@author A0122061B

public class CommandParser {

    /*
     * ====================================================================
     * Attributes/Variables
     * ====================================================================
     */
    private static CommandParser theOne = null;

    public ArrayList<String> inputArr;
    public CommandPackage inputData;
    public String input;

    private ActionLibrary libraryForAction = new ActionLibrary();
    private DateParser parserForDate = new DateParser();
    private TimeLibrary libraryForTime = new TimeLibrary();
    private TimeParser parserForTime = new TimeParser();

    /*
     * ====================================================================
     * Magic Constants
     * ====================================================================
     */

    public String NOT_FOUND = "invalid input";
    public String INVALID_PACKAGE = "invalid package";

    private String START_TIME_LONG = "`startTime";
    private String START_TIME_SHORT = "`st";
    private String END_TIME_LONG = "`endTime";
    private String END_TIME_SHORT = "`et";

    /*
     * ====================================================================
     * Constructors
     * ====================================================================
     */

    private CommandParser() {

    }

    private CommandParser(String command) {
	input = command;
	String[] arr = input.split(" ");
	arrToArrayList(arr);
    }

    public static CommandParser getInstance() {

	if (theOne == null) {
	    theOne = new CommandParser();
	}
	return theOne;
    }

    /*
     * ====================================================================
     * Method for CommandParser
     * ====================================================================
     */

    /**
     * The main method that MainApp will call to parse the string given by users
     * and process the intructions that will be processed by Logic component.
     * 
     * @param command
     *            the set of words that users have typed into the command bar
     * @return a CommandPackage for Logic component to process
     */
    public CommandPackage getCommandPackage(String command) {
	input = command;
	try {
	    inputData = new CommandPackage();
	    String[] arr = input.split(" ");
	    arrToArrayList(arr);
	    String commandName = findAction();

	    inputData.setCommand(commandName);

	    if (!isValidCommand(commandName) && isArrayEmptyAndInvalid()) {
		return null;
	    } else if (isOneCommandFormat(commandName)) {
		return inputData;
	    }
	    if (isArrayEmptyAndInvalid()) {
		return null;
	    }
	    if (isUpdateCommand(commandName)) {
		return updateInput();
	    } else {
		String priority = findPriority();
		inputData.setPriority(priority);
	    }
	    if (isCreateCommand(commandName)) {
		addDateTime();
		if (isArrayEmptyAndInvalid()) {
		    return null;
		}
	    }
	    if (isSearchCommand(commandName)) {
		searchInput();
	    }
	    inputData.setPhrase(getPhrase());
	    return inputData;
	} catch (NullPointerException e1) {
	    System.out.println("NULL POINTER ERRORS");
	    return null;
	} catch (StringIndexOutOfBoundsException e2) {
	    System.out.println("StringIndexOutOfBoundsException");
	    return null;
	}
    }
    /*
     * ====================================================================
     * Parser For Add
     * ====================================================================
     */

    private void addDateTime() {
	int numberOfDates = countDate();
	int numberOfTime = countTime();
	ArrayList<DateTime> dateArr;
	if (isOne(numberOfDates)) {
	    dateArr = extractDate();
	    if (isZero(numberOfTime)) {
		for (int i = 0; i < inputArr.size(); i++) {
		    dateProcess1(dateArr, i);
		}
	    } else if (isOne(numberOfTime)) {
		dateArr = extractTime(dateArr);
		for (int i = 0; i < inputArr.size(); i++) {
		    dateProcess2(dateArr, i);
		}
	    } else if (isTwo(numberOfTime)) {
		dateArr.add(dateArr.get(0));
		dateArr = extractTime(dateArr);
		inputData.setDates(dateArr);
	    }

	} else {
	    if (isOne(numberOfTime)) {
		dateArr = extractTime();
		for (int i = 0; i < inputArr.size(); i++) {
		    dateProcess2(dateArr, i);
		}
	    }
	    if (isTwo(numberOfDates)) {
		dateArr = extractDate();
		if (isTwo(numberOfTime)) {
		    dateArr = extractTime(dateArr);
		}
		inputData.setDates(dateArr);
	    } else if (isTwo(numberOfTime)) {
		ArrayList<DateTime> timeArr = extractTime();
		inputData.setDates(timeArr);

	    }

	}

    }

    /**
     * The main method processes input with only 1 date input and 0 time input
     * and input into the CommandPackage
     * 
     * @param dateArr
     *            an array of existing date format
     * @param i
     *            the index that is going to process the dateArr element
     * @return the updated inputArr
     */
    private ArrayList<String> dateProcess1(ArrayList<DateTime> dateArr, int i) {
	if (inputArr.get(i).equalsIgnoreCase("start")) {
	    inputData.setDates(dateArr, "start");
	    inputArr.remove(i);
	} else if (inputArr.get(i).equalsIgnoreCase("end")) {
	    inputData.setDates(dateArr, "end");
	    inputArr.remove(i);
	} else {
	    inputData.setDates(dateArr, "end");
	}
	return inputArr;
    }

    /**
     * The main method processes input with only 1 date input and 1 time input
     * and input into the CommandPackage
     * 
     * @param command
     *            the set of words that users have typed into the command bar
     * @param i
     *            the index that is going to process the dateArr element
     * @return the updated inputArr
     */
    private ArrayList<String> dateProcess2(ArrayList<DateTime> dateArr, int i) {
	if (libraryForTime.isStart(inputArr.get(i))) {
	    inputData.setDates(dateArr, "start");
	    inputArr.remove(i);
	} else if (libraryForTime.isEnd(inputArr.get(i))) {
	    inputData.setDates(dateArr, "end");
	    inputArr.remove(i);
	} else {
	    inputData.setDates(dateArr, "end");
	}
	return inputArr;
    }

    private ArrayList<DateTime> extractDate() {
	ArrayList<DateTime> dateArr = new ArrayList<DateTime>();
	ArrayList<Integer> index = new ArrayList<Integer>();
	String test;
	for (int i = 0; i < inputArr.size(); i++) {
	    test = inputArr.get(i);
	    if (test.contains("`")) {
		test = test.substring(1);
	    }
	    if (parserForDate.isDate(test)) {
		String date = test;
		index.add(i);
		dateArr.add(parserForDate.setDate(date));
	    }
	}
	for (int i = index.size() - 1; 0 <= i; i--) {
	    int indexToRemove = index.get(i);
	    inputArr.remove(indexToRemove);
	}
	return dateArr;
    }

    private ArrayList<DateTime> extractTime(ArrayList<DateTime> dateArr) {
	int count = 0;
	ArrayList<DateTime> newDateArr = new ArrayList<DateTime>();
	ArrayList<Integer> index = new ArrayList<Integer>();
	String test;
	for (int i = 0; i < inputArr.size(); i++) {
	    test = inputArr.get(i);
	    if (test.contains("`")) {
		test = test.substring(1);
	    }
	    if (parserForTime.isTime(inputArr.get(i))) {

		DateTime oldDate = dateArr.get(count);
		index.add(i);
		DateTime newDate = parserForTime.setTime(oldDate, test);
		newDateArr.add(newDate);
		count++;
	    }
	}
	for (int i = index.size() - 1; 0 <= i; i--) {
	    int indexToRemove = index.get(0);
	    inputArr.remove(indexToRemove);
	}
	return newDateArr;
    }

    private ArrayList<DateTime> extractTime() {
	ArrayList<DateTime> dateArr = new ArrayList<DateTime>();
	ArrayList<Integer> index = new ArrayList<Integer>();
	String test;
	for (int i = 0; i < inputArr.size(); i++) {
	    test = inputArr.get(i);
	    if (test.contains("`")) {
		test = test.substring(1);
	    }
	    if (parserForTime.isTime(test)) {
		index.add(i);
		dateArr.add(parserForTime.setTime(null, test));
	    }
	}
	for (int i = index.size() - 1; 0 <= i; i--) {
	    int indexToRemove = index.get(0);
	    inputArr.remove(indexToRemove);
	}
	return dateArr;
    }

    /*
     * ====================================================================
     * Parser For Search
     * ====================================================================
     */
    private void searchInput() {
	String sample;
	for (int i = 0; i < inputArr.size(); i++) {
	    sample = inputArr.get(i);
	    if (parserForTime.isValidSearchFormat(sample) || parserForDate.isDate(sample)) {
		inputData.setDates(extractSearchDate());
	    }
	}
    }

    private ArrayList<DateTime> extractSearchDate() {
	String date;
	for (int i = 0; i < inputArr.size(); i++) {
	    if (parserForDate.isDate(inputArr.get(i))) {
		date = inputArr.remove(i);
		return parserForDate.searchDate(date);
	    } else if (parserForTime.isValidSearchFormat(inputArr.get(i))) {
		date = inputArr.remove(i);
		return parserForTime.searchTime(date);
	    }
	}
	return null;
    }

    private CommandPackage updateInput() {
	String sequence = "";
	String word;
	ArrayList<DateTime> dateArr;
	int numberOfDates = countDate();
	int numberOfTime = countTime();
	System.out.println(numberOfTime);
	for (int i = 0; i < inputArr.size(); i++) {
	    word = inputArr.get(i);
	    if (word.startsWith("`")) {
		if (word.length() == 1) {
		    inputArr.set(i, word + inputArr.get(i + 1));
		} else {
		    inputData.addUpdateSequence(sequence);
		    sequence = "";
		    sequence = word.substring(1);
		    if (word.equalsIgnoreCase(START_TIME_LONG) || word.equalsIgnoreCase(START_TIME_SHORT)) {
			sequence = "startTime";
			if (isOne(numberOfDates)) {
			    dateArr = extractDate();
			    if (isOne(numberOfTime)) {
				dateArr = extractTime(dateArr);
			    }
			} else if (isOne(numberOfTime)) {
			    System.out.println("extracing start time");
			    dateArr = extractTime();
			} else if (inputArr.get(i + 1).equals("`delete")) {
			    dateArr = null;
			    inputData.addUpdateSequence("delete");
			} else {
			    dateArr = null;
			}
			inputData.setDates(dateArr, "start");
		    } else {
			if (word.equalsIgnoreCase(END_TIME_LONG) || word.equalsIgnoreCase(END_TIME_SHORT)) {
			    sequence = "endTime";
			    if (isOne(numberOfDates)) {
				dateArr = extractDate();
				if (isOne(numberOfTime)) {
				    dateArr = extractTime(dateArr);
				}
			    } else if (isOne(numberOfTime)) {
				dateArr = extractTime();
			    } else if (inputArr.get(i + 1).equals("`delete")) {
				dateArr = null;
				inputData.addUpdateSequence("delete");
			    } else {
				dateArr = null;
			    }
			    inputData.setDates(dateArr, "end");
			}
		    }
		}
	    } else {
		sequence = sequence + " " + word;
	    }
	}
	inputData.addUpdateSequence(sequence);
	if (isDateUpdateSequence()) {
	    inputData.addUpdateSequence(inputData.getDate().toString());
	}
	System.out.println("UPDATE" + inputData.getUpdateSequence().get(2));
	return inputData;
    }

    /*
     * ====================================================================
     * inputArr manipulators
     * ====================================================================
     */

    private ArrayList<String> arrToArrayList(String[] arr) {
	inputArr = new ArrayList<String>();
	for (int i = 0; i < arr.length; i++) {
	    inputArr.add(arr[i]);
	}
	return inputArr;
    }

    private int countDate() {
	System.out.println("counting");
	int count = 0;
	for (int i = 0; i < inputArr.size(); i++) {
	    System.out.println("checking " + inputArr.get(i));
	    if (parserForDate.isDate(inputArr.get(i)) || parserForDate.isDate(inputArr.get(i).substring(1))) {
		count++;
	    }
	}
	return count;
    }

    private int countTime() {
	int count = 0;
	for (int i = 0; i < inputArr.size(); i++) {
	    if (parserForTime.isTime(inputArr.get(i)) || parserForTime.isTime(inputArr.get(i).substring(1))) {
		count++;
	    }
	}
	return count;
    }

    public String findAction() {
	String command;

	if (!callAction(inputArr.get(0)).equals(NOT_FOUND)) {
	    command = callAction(inputArr.get(0));
	    remove(inputArr.get(0));
	} else if (!callAction(inputArr.get(inputArr.size() - 1)).equals(NOT_FOUND)) {
	    command = callAction(inputArr.get(inputArr.size() - 1));
	    remove(inputArr.get(inputArr.size() - 1));
	} else {
	    command = NOT_FOUND;
	}
	return command;
    }

    public String findPriority() {
	String priority = "";
	String test;
	for (int i = 0; i < inputArr.size(); i++) {
	    test = inputArr.get(i);
	    if (test.startsWith("#")) {
		priority = test.substring(1);
		remove(test);
	    }
	}
	return priority;
    }

    private boolean remove(String word) {
	for (int i = 0; i < inputArr.size(); i++) {
	    if (inputArr.get(i).equalsIgnoreCase(word)) {
		remove(i);
	    }
	}
	return true;
    }

    /*
     * ====================================================================
     * Boolean operations
     * ====================================================================
     */

    private boolean isValidCommand(String command) {
	return command != NOT_FOUND;
    }

    private boolean isSearchCommand(String commandName) {
	return commandName.equals("search");
    }

    private boolean isUpdateCommand(String commandName) {
	return commandName.equals("update");
    }

    private boolean isCreateCommand(String commandName) {
	return commandName.equals("create");
    }

    private boolean isArrayEmptyAndInvalid() {
	return inputArr.size() == 0;
    }

    protected boolean remove(int index) {
	inputArr.remove(index);
	return true;
    }

    private boolean isDateUpdateSequence() {
	return inputData.getUpdateSequence().size() < 4;
    }

    private boolean isZero(int numberOfTime) {
	return numberOfTime == 0;
    }

    private boolean isTwo(int numberOfDates) {
	return numberOfDates == 2;
    }

    private boolean isOne(int numberOfTime) {
	return numberOfTime == 1;
    }

    private boolean isOneCommandFormat(String commandName) {
	return commandName.equalsIgnoreCase("undo") || commandName.equalsIgnoreCase("clear")
	       || commandName.equalsIgnoreCase("exit") || commandName.equalsIgnoreCase("redo");
    }

    /*
     * ====================================================================
     * Accessors
     * ====================================================================
     */
    protected String getPhrase() {
	if (inputArr.isEmpty()) {
	    return "";
	} else {
	    String phrase = inputArr.get(0);
	    ;
	    for (int i = 1; i < inputArr.size(); i++) {
		phrase = phrase + " " + inputArr.get(i);
	    }
	    return phrase;
	}

    }

    private String callAction(String string) {
	String result = libraryForAction.find(string);
	return result;
    }

    public CommandPackage getInput() {
	return inputData;
    }
}
