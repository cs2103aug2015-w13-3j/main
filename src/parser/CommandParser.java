package parser;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;

//@@author A0122061B

public class CommandParser {

    public String NOT_FOUND = "invalid input";
    public String INVALID_PACKAGE = "invalid package";
    public String input;
    public ArrayList<String> inputArr;
    public CommandPackage inputData;
    private String START_TIME_LONG = "`startTime";
    private String START_TIME_SHORT = "`st";
    private String END_TIME_LONG = "`endTime";
    private String END_TIME_SHORT = "`et";
    private DateParser parserForDate = new DateParser();
    private TimeParser parserForTime = new TimeParser();
    private ActionLibrary libraryForAction = new ActionLibrary();
    private TimeLibrary libraryForTime = new TimeLibrary();
    
    
    
    

    public CommandParser() {

    }

    public CommandParser(String command) {
	input = command;
	String[] arr = input.split(" ");
	inputArr = new ArrayList<String>();
	for (int i = 0; i < arr.length; i++) {
	    inputArr.add(arr[i]);
	}
    }

    public CommandPackage getCommandPackage(String command) {

	input = command;
	inputData = new CommandPackage();
	String[] arr = input.split(" ");
	inputArr = new ArrayList<String>();
	for (int i = 0; i < arr.length; i++) {
	    inputArr.add(arr[i]);
	}
	String commandName = findAction();

	System.out.println("command name " + commandName);

	inputData.setCommand(commandName);

	if (!isValidCommand(commandName) && (inputArr.size() == 0)) {

	    return null;

	}
	if (commandName.equals("update")) {
	    return updateInput();
	} else {

	    // System.out.println("Phrase : " + getPhrase());

	    String priority = findPriority();
	    inputData.setPriority(priority);
	}

	if (commandName.equals("create")) {
	    System.out.println("creating now");
	    addDateTime();
	    if (inputArr.size() == 0) {
		return null;
	    }
	}
	if (commandName.equals("search")) {
	    String sample;
	    for (int i = 0; i < inputArr.size(); i++) {
		sample = inputArr.get(i);
		if (parserForTime.isValidSearchFormat(sample) || parserForDate.isDate(sample)) {
		    inputData.setDates(extractSearchDate());
		}
	    }
	}
	System.out.println(inputArr);
	inputData.setPhrase(getPhrase());
	System.out.println();

	return inputData;
    }

    /*
     * private int searchDates() { int numberOfDates = countDate(); int
     * numberOfTime = countTime(); if (numberOfDates == 1) { ArrayList<DateTime>
     * dateArr = extractSearchDate(); } if (numberOfTime == 1) {
     * parserForTime.searchTime();
     * 
     * }
     * 
     * return 0;
     * 
     * }
     */
    private CommandPackage updateInput() {
	String sequence = "";
	String word;
	ArrayList<DateTime> dateArr;
	for (int i = 0; i < inputArr.size(); i++) {
	    word = inputArr.get(i);
	    if (word.startsWith("`")) {
		if (word.equalsIgnoreCase(START_TIME_LONG) || word.equalsIgnoreCase(START_TIME_SHORT)) {
		    dateArr = extractDate();
		    inputData.setDates(dateArr, "start");
		} else {
		    if (word.equalsIgnoreCase(END_TIME_LONG) || word.equalsIgnoreCase(END_TIME_SHORT)) {
			dateArr = extractDate();
			inputData.setDates(dateArr, "end");
		    }
		}
		System.out.println(sequence + "+1");
		inputData.addUpdateSequence(sequence);
		sequence = "";
		sequence = word.substring(1);
	    } else {
		sequence = sequence + " " + word;
	    }
	    // System.out.println(i+". "+word);
	}
	System.out.println(sequence + "+1");
	inputData.addUpdateSequence(sequence);
	if (inputData.getUpdateSequence().size() < 4) {
	    System.out.println("last sequence is " + inputData.getDate().toString());
	    inputData.addUpdateSequence(inputData.getDate().toString());
	}
	System.out.println(inputData.getUpdateSequence().size());
	System.out.println("sequence is: " + inputData.getUpdateSequence().get(1) + ", "
			   + inputData.getUpdateSequence().get(2) + ", "
			   + inputData.getUpdateSequence().get(3));
	return inputData;
    }

    private void addDateTime() {
	System.out.println("Adding datetime");
	int numberOfDates = countDate();
	int numberOfTime = countTime();
	System.out.println("number of Time = " + numberOfTime);
	ArrayList<DateTime> dateArr;
	if (isOne(numberOfDates)) {
	    System.out.println("++++++++");
	    dateArr = extractDate();
	    if (numberOfTime == 0) {
		System.out.println(dateArr.size() + "<<<");
		for (int i = 0; i < inputArr.size(); i++) {
		    if (inputArr.get(i).equalsIgnoreCase("start")) {
			inputData.setDates(dateArr, "start");
			inputArr.remove(i);
		    } else if (inputArr.get(i).equalsIgnoreCase("end")) {
			inputData.setDates(dateArr, "end");
			inputArr.remove(i);
		    } else {
			inputData.setDates(dateArr, "end");
		    }
		}
	    } else if (isOne(numberOfDates)) {
		dateArr = extractTime(dateArr);
		System.out.println(dateArr.size() + "<<<");
		for (int i = 0; i < inputArr.size(); i++) {
		    if (libraryForTime.isStart(inputArr.get(i))) {
			inputData.setDates(dateArr, "start");
			inputArr.remove(i);
		    } else if (libraryForTime.isEnd(inputArr.get(i))) {
			inputData.setDates(dateArr, "end");
			inputArr.remove(i);
		    } else {
			inputData.setDates(dateArr, "end");
		    }
		}
	    }
	} else {
	    System.out.println(" <<><><><><<><<<><>< ");
	    if (isOne(numberOfTime)) {
		System.out.println("theres 1!");
		dateArr = extractTime();
		for (int i = 0; i < inputArr.size(); i++) {
		    System.out.println("date is  " + dateArr.get(0));
		    if (libraryForTime.isStart(inputArr.get(i))) {
			inputData.setDates(dateArr, "start");
			inputArr.remove(i);
		    } else if (libraryForTime.isEnd(inputArr.get(i))) {
			inputData.setDates(dateArr, "end");
			inputArr.remove(i);
		    } else {
			inputData.setDates(dateArr, "end");
		    }
		}
	    }
	    System.out.println(" =========== ");
	    if (isTwo(numberOfDates)) {
		System.out.println(inputArr);
		dateArr = extractDate();
		System.out.println(inputArr);
		System.out.println("two days = " + dateArr.size());
		if (isTwo(numberOfTime)) {
		    dateArr = extractTime(dateArr);
		}
		System.out.println("two days and Time = " + dateArr.size());
		inputData.setDates(dateArr);
	    } else if (isTwo(numberOfTime)) {
		ArrayList<DateTime> timeArr = extractTime();
		System.out.println(timeArr.size());
		inputData.setDates(timeArr);

	    }

	}

    }

    private boolean isTwo(int numberOfDates) {
	return numberOfDates == 2;
    }

    private boolean isOne(int numberOfTime) {
	return numberOfTime == 1;
    }

    private ArrayList<DateTime> extractSearchDate() {
	String date;
	for (int i = 0; i < inputArr.size(); i++) {
	    System.out.println("searching index " + i);
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

    private ArrayList<DateTime> extractDate() {
	ArrayList<DateTime> dateArr = new ArrayList<DateTime>();
	ArrayList<Integer> index = new ArrayList<Integer>();
	String test;
	for (int i = 0; i < inputArr.size(); i++) {
	    test = inputArr.get(i);
	    System.out.println("Testing " + test);
	    if (test.contains("`")) {
		test = test.substring(1);
	    }
	    if (parserForDate.isDate(test)) {
		String date = test;
		index.add(i);
		System.out.println("DATE IS " + date);
		dateArr.add(parserForDate.setDate(date));
	    }
	}
	for (int i = index.size() - 1; 0 <= i; i--) {
	    System.out.println("index at " + i);
	    System.out.println("index " + index.get(i));
	    int indexToRemove = index.get(i);
	    System.out.println(inputArr);
	    inputArr.remove(indexToRemove);
	    System.out.println(inputArr);
	}
	System.out.println(dateArr.size());
	return dateArr;
    }

    private ArrayList<DateTime> extractTime(ArrayList<DateTime> dateArr) {
	int count = 0;
	ArrayList<DateTime> newDateArr = new ArrayList<DateTime>();
	ArrayList<Integer> index = new ArrayList<Integer>();
	for (int i = 0; i < inputArr.size(); i++) {
	    System.out.println(i + ". TESTING IS " + inputArr.get(i));
	    if (parserForTime.isTime(inputArr.get(i))) {
		System.out.println("TIME IS " + inputArr.get(i));
		String time = inputArr.get(i);
		DateTime oldDate = dateArr.get(count);
		System.out.println("TIME IS " + time);
		System.out.println("DATE IS  " + oldDate);
		index.add(i);
		DateTime newDate = parserForTime.setTime(oldDate, time);
		System.out.println("NEW DATE IS  " + newDate);
		newDateArr.add(newDate);
		count++;
	    }
	}
	for (int i = index.size() - 1; 0 <= i; i--) {
	    System.out.println("index " + index.get(i));
	    int indexToRemove = index.get(0);
	    inputArr.remove(indexToRemove);
	}
	return newDateArr;
    }

    private ArrayList<DateTime> extractTime() {
	ArrayList<DateTime> dateArr = new ArrayList<DateTime>();
	ArrayList<Integer> index = new ArrayList<Integer>();
	int count = 0;
	for (int i = 0; i < inputArr.size(); i++) {
	    if (parserForTime.isTime(inputArr.get(i))) {
		String time = inputArr.get(i);
		index.add(i);
		dateArr.add(parserForTime.setTime(null, time));
		count++;
	    }
	}
	for (int i = index.size() - 1; 0 <= i; i--) {
	    System.out.println("index " + index.get(i));
	    int indexToRemove = index.get(0);
	    inputArr.remove(indexToRemove);
	}
	return dateArr;
    }

    private int countDate() {
	int count = 0;
	for (int i = 0; i < inputArr.size(); i++) {
	    if (parserForDate.isDate(inputArr.get(i))) {
		count++;
	    }
	}
	return count;
    }

    private int countTime() {
	int count = 0;
	for (int i = 0; i < inputArr.size(); i++) {
	    if (parserForTime.isTime(inputArr.get(i))) {
		count++;
	    }
	}
	return count;
    }

    public CommandPackage getInput() {
	return inputData;
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
	System.out.println("Priority = " + priority);
	return priority;
    }

    private String callAction(String string) {
	String result = libraryForAction.find(string);
	return result;
    }

    public boolean isValidCommand(String command) {
	return command != NOT_FOUND;
    }

    public boolean remove(String word) {
	for (int i = 0; i < inputArr.size(); i++) {
	    if (inputArr.get(i).equalsIgnoreCase(word)) {
		remove(i);
	    }
	}
	return true;
    }

    public boolean remove(int index) {
	inputArr.remove(index);
	return true;
    }

    public String getPhrase() {
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

}
