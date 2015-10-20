package parser;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;

public class CommandParser {

	public String NOT_FOUND = "invalid input";
	public String INVALID_PACKAGE = "invalid package";
	public String input;
	public ArrayList<String> inputArr;
	public CommandPackage inputData;
	public ActionLibrary actionLib;

	public CommandParser() {
		actionLib = new ActionLibrary();
		inputData = new CommandPackage();
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
		String[] arr = input.split(" ");
		inputArr = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			inputArr.add(arr[i]);
		}
		String commandName = findAction();

		System.out.println(commandName);

		inputData.setCommand(commandName);
		if (!!isValidCommand(commandName) && (inputArr.size() == 0)) {
			return null;

		}
		if (commandName.equals("update")) {
			return updateInput();
		} else {

			System.out.println("Phrase : " + getPhrase());

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
			//searchDates();
		}
		System.out.println(inputArr);
		inputData.setPhrase(getPhrase());
		System.out.println();

		return inputData;
	}
/*
	private int searchDates() {
		int numberOfDates = countDate();
		int numberOfTime = countTime();
		if (numberOfDates == 1) {
			ArrayList<DateTime> dateArr = extractSearchDate();
		}
		if (numberOfTime == 1) {
			TimeParser.searchTime();
			
		}

		return 0;

	}
*/
	private CommandPackage updateInput() {
		String sequence = "";
		String word;
		for (int i = 0; i < inputArr.size(); i++) {
			word = inputArr.get(i);
			if (word.startsWith("~")) {
				System.out.println(sequence);
				inputData.addUpdateSequence(sequence);
				sequence = "";
				sequence = word.substring(1);
			} else {
				sequence = sequence + " " + word;
			}
			//System.out.println(i+". "+word);
		}
		System.out.println("sequence is: "+sequence);
		inputData.addUpdateSequence(sequence);
		return inputData;
	}

	private int addDateTime() {
		System.out.println("Adding datetime");
		int numberOfDates = countDate();
		int numberOfTime = countTime();
		System.out.println("number of dates = " + numberOfDates);
		ArrayList<DateTime> dateArr;
		if (numberOfDates == 1) {
			dateArr = extractDate();
			System.out.println(dateArr.size() + "<<<");
			for (int i = 0; i < inputArr.size(); i++) {
				if (inputArr.get(i).equalsIgnoreCase("start")) {
					inputData.setDates(dateArr, "start");
					remove("start");
				} else if (inputArr.get(i).equalsIgnoreCase("end")) {
					inputData.setDates(dateArr, "end");
					remove("end");
				} else {
					inputData.setDates(dateArr, "end");
				}
			}
			return 1;

		} else if (numberOfDates == 1 && numberOfTime == 1) {
			System.out.println("theres 1!");
			for (int i = 0; i < inputArr.size(); i++) {
				dateArr = extractDate();
				if (inputArr.get(i).equalsIgnoreCase("start")) {
					inputData.setDates(extractTime(dateArr), "start");
					remove("start");
				} else if (inputArr.get(i).equalsIgnoreCase("end")) {
					inputData.setDates(extractTime(dateArr), "end");
					remove("end");
				} else {
					inputData.setDates(extractTime(dateArr), "end");
				}
			}
			return 1;
		} else if (numberOfDates == 2) {
			inputData.setDates(extractDate());
			return 2;
		} else {
			return -1;
		}

	}

	private ArrayList<DateTime> extractSearchDate() {
		for (int i = 0; i < inputArr.size(); i++) {
			if (DateParser.isDate(inputArr.get(i))) {
				String date = inputArr.remove(i);
				return DateParser.searchDate(date);
			}
		}
		return null;
	}


	private ArrayList<DateTime> extractDate() {
		ArrayList<DateTime> dateArr = new ArrayList<DateTime>();
		for (int i = 0; i < inputArr.size(); i++) {
			System.out.println("Testing" + inputArr.get(i));
			if (DateParser.isDate(inputArr.get(i))) {
				String date = inputArr.remove(i);
				System.out.println("DATE IS " + date);
				dateArr.add(DateParser.setDate(date));
			}
		}
		return dateArr;
	}

	private ArrayList<DateTime> extractTime(ArrayList<DateTime> dateArr) {
		int count = 0;
		for (int i = 0; i < inputArr.size(); i++) {
			if (TimeParser.isTime(inputArr.get(i))) {
				String time = inputArr.remove(i);
				dateArr.add(TimeParser.setTime(dateArr.get(count), time));
				count++;
			}
		}
		return dateArr;
	}

	private ArrayList<DateTime> extractTime() {
		ArrayList<DateTime> dateArr = new ArrayList<DateTime>();
		int count = 0;
		for (int i = 0; i < inputArr.size(); i++) {
			if (TimeParser.isTime(inputArr.get(i))) {
				String time = inputArr.remove(i);
				dateArr.add(TimeParser.setTime(dateArr.get(count), time));
				count++;
			}
		}
		return dateArr;
	}

	private int countDate() {
		int count = 0;
		for (int i = 0; i < inputArr.size(); i++) {
			if (DateParser.isDate(inputArr.get(i))) {
				count++;
			}
		}
		return count;
	}

	private int countTime() {
		int count = 0;
		for (int i = 0; i < inputArr.size(); i++) {
			if (TimeParser.isTime(inputArr.get(i))) {
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
		return priority;
	}

	private String callAction(String string) {
		String result = actionLib.find(string);
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
		String phrase = inputArr.get(0);
		;
		for (int i = 1; i < inputArr.size(); i++) {
			phrase = phrase + " " + inputArr.get(i);
		}
		return phrase;
	}

}
