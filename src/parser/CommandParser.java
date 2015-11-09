package parser;

import java.util.ArrayList;

import org.joda.time.DateTime;

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

    private ActionLibrary libraryForAction;
    private DateParser parserForDate;
    private TimeLibrary libraryForTime;
    private TimeParser parserForTime;
    private BooleanChecker checker;

    /*
     * ====================================================================
     * Magic Constants
     * ====================================================================
     */

    private final String NOT_FOUND = "invalid input";
    private final String INVALID_PACKAGE = "invalid package";

    private final String START_TIME_LONG = "`startTime";
    private final String START_TIME_SHORT = "`st";
    private final String END_TIME_LONG = "`endTime";
    private final String END_TIME_SHORT = "`et";

    /*
     * ====================================================================
     * Constructors
     * ====================================================================
     */

    private CommandParser() {
	libraryForAction = ActionLibrary.getInstance();
	libraryForTime = TimeLibrary.getInstance();
	parserForTime = TimeParser.getInstance();
	checker = BooleanChecker.getInstance();
	parserForDate = DateParser.getInstance();

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
	 // try {
	    inputData = new CommandPackage();
	    String[] arr = input.split(" ");
	    arrToArrayList(arr);
	    String commandName = findAction();

	    inputData.setCommand(commandName);
	    System.out.println("name " + commandName);
	    System.out.println((!checker.isValidCommand(commandName)));
	    System.out.println();
	    if (!checker.isValidCommand(commandName)) {
		return null;
	    } else if (checker.isOneCommandFormat(commandName)
		       || checker.isClearAllCommand(commandName, inputArr)) {
		return inputData;
	    } else if (checker.isArrayEmptyAndInvalid(inputArr)) {
		return null;
	    } else if (checker.isUpdateCommand(commandName)) {
		return updateInput();
	    } else {
		String priority = findPriority();
		inputData.setPriority(priority);
	    }
	    if (checker.isCreateCommand(commandName)) {
		addDateTime();
		if (checker.isArrayEmptyAndInvalid(inputArr)) {
		    return null;
		}
	    }
	    if (checker.isSearchCommand(commandName)) {
		searchInput();
	    }
	    inputData.setPhrase(getPhrase());
	    return inputData;
/*-
	} catch (NullPointerException e1) {
	    System.out.println("NULL POINTER ERRORS");
	    return null;

	} catch (StringIndexOutOfBoundsException e2) {
	    System.out.println("StringIndexOutOfBoundsException");
	    return null;
	    
	}
	*/
	    
    }
    /*
     * ====================================================================
     * Parser For Add
     * ====================================================================
     */

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
    private void addDateTime() {
	int numberOfDates = countDate();
	int numberOfTime = countTime();
	ArrayList<DateTime> dateArr;
	if (checker.isOne(numberOfDates)) {
	    dateArr = extractDate();
	    if (checker.isZero(numberOfTime)) {
		for (int i = 0; i < inputArr.size(); i++) {
		    dateProcess1(dateArr, i);
		}
	    } else if (checker.isOne(numberOfTime)) {
		dateArr = extractTime(dateArr);
		for (int i = 0; i < inputArr.size(); i++) {
		    dateProcess2(dateArr, i);
		}
	    } else if (checker.isTwo(numberOfTime)) {
		dateArr.add(dateArr.get(0));
		dateArr = extractTime(dateArr);
		inputData.setDates(dateArr);
	    }

	} else {
	    if (checker.isOne(numberOfTime)) {
		dateArr = extractTime();
		for (int i = 0; i < inputArr.size(); i++) {
		    dateProcess2(dateArr, i);
		}
	    }
	    if (checker.isTwo(numberOfDates)) {
		dateArr = extractTwoDate();
		if (checker.isTwo(numberOfTime)) {
		    dateArr = extractTime(dateArr);
		}
		inputData.setDates(dateArr);
	    } else if (checker.isTwo(numberOfTime)) {
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
		if (i < inputArr.size() - 1) {

		}
		dateArr.add(parserForDate.setDate(date));
	    }
	}
	for (int i = index.size() - 1; 0 <= i; i--) {
	    int indexToRemove = index.get(i);
	    inputArr.remove(indexToRemove);
	}
	return dateArr;
    }

    private ArrayList<DateTime> extractTwoDate() {
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
		if (i >= 0) {
		    if ((libraryForTime.isEnd(inputArr.get(i - 1))
			 || libraryForTime.isStart(inputArr.get(i - 1)))
			&& !(index.contains(i - 1))) {
			index.add(i - 1);
			System.out.println("before " + inputArr.get(i - 1));
		    }
		}
		System.out.println("date" + inputArr.get(i));
		index.add(i);

		if (i < inputArr.size() - 1) {
		    if ((libraryForTime.isEnd(inputArr.get(i + 1))
			 || libraryForTime.isStart(inputArr.get(i + 1)))
			&& !(index.contains(i + 1))) {
			index.add(i + 1);
			System.out.println("after " + inputArr.get(i + 1));
		    }
		}

		dateArr.add(parserForDate.setDate(date));
	    }
	}
	for (

	int i = index.size() - 1; 0 <= i; i--)

	{
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

    /*
     * ====================================================================
     * Parser For Update
     * ====================================================================
     */
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
			sequence = updateStartDateParsing(numberOfDates, numberOfTime, i);
		    } else {
			if (word.equalsIgnoreCase(END_TIME_LONG) || word.equalsIgnoreCase(END_TIME_SHORT)) {
			    sequence = updateEndDateParsing(numberOfDates, numberOfTime, i);
			}
		    }
		}
	    } else {
		sequence = sequence + " " + word;
	    }
	}
	inputData.addUpdateSequence(sequence);
	if (checker.isDateUpdateSequence(inputData)) {
	    inputData.addUpdateSequence(inputData.getDate().toString());
	}
	return inputData;
    }

    private String updateEndDateParsing(int numberOfDates, int numberOfTime, int i) {
	String sequence;
	ArrayList<DateTime> dateArr;
	sequence = "endTime";
	if (checker.isOne(numberOfDates)) {
	    dateArr = extractDate();
	    if (checker.isOne(numberOfTime)) {
		dateArr = extractTime(dateArr);
	    }
	} else if (checker.isOne(numberOfTime)) {
	    dateArr = extractTime();
	} else if (inputArr.get(i + 1).equals("`delete") || inputArr.get(i + 1).equals("`remove")) {
	    System.out.println("deleteing");
	    dateArr = null;
	    inputData.addUpdateSequence(sequence);
	    inputData.addUpdateSequence("delete");
	} else {
	    dateArr = null;
	}
	inputData.setDates(dateArr, "end");
	return sequence;
    }

    private String updateStartDateParsing(int numberOfDates, int numberOfTime, int i) {
	String sequence;
	ArrayList<DateTime> dateArr;
	sequence = "startTime";
	if (checker.isOne(numberOfDates)) {
	    dateArr = extractDate();
	    if (checker.isOne(numberOfTime)) {
		dateArr = extractTime(dateArr);
	    }
	} else if (checker.isOne(numberOfTime)) {
	    System.out.println("extracing start time");
	    dateArr = extractTime();
	} else if (inputArr.get(i + 1).equals("`delete") || inputArr.get(i + 1).equals("`remove")) {
	    System.out.println("deleteing");
	    dateArr = null;
	    inputData.addUpdateSequence(sequence);
	    inputData.addUpdateSequence("delete");
	} else {
	    dateArr = null;
	}
	inputData.setDates(dateArr, "start");
	return sequence;
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
		inputArr.remove(i);
	    }
	}
	return true;
    }

    /*
     * ====================================================================
     * Boolean operations
     * ====================================================================
     */

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
	String result;
	System.out.println("string");
	result = libraryForAction.find(string);
	return result;
    }

    public CommandPackage getInput() {
	return inputData;
    }
}
