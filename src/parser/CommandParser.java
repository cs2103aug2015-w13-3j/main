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
			if (inputArr.size() == 0) {
				return null;

			}
			System.out.println("Priority : " + priority);
	
			setDate();
			if (inputArr.size() == 0) {
				return null;

			}
			System.out.println(inputArr);
			inputData.setPhrase(getPhrase());
			System.out.println();
	
	
			return inputData;
		}
	}

	private CommandPackage updateInput() {
		String sequence = "";
		String word;
		for (int i =0; i < inputArr.size() ; i ++) {
			word = inputArr.get(i);
			if (word.startsWith("~")) {
				System.out.println(sequence);
				inputData.addUpdateSequence(sequence);
				sequence = "";
				sequence = word.substring(1);
			} else {
				sequence = sequence + " " + word;
			}
		}
		System.out.println(sequence);
		inputData.addUpdateSequence(sequence);
		return inputData;
	}

	private int setDate() {
		int numberOfDates = countDate();
		if (numberOfDates == 0) {
			return 0;
		} else if (numberOfDates == 1) {
			//TODO add start OR end condition
			for (int i = 0; i < inputArr.size(); i++) {
				if (inputArr.get(i).equalsIgnoreCase("start")) {
					inputData.setDates(extractDate(), "start");
					remove("start");
				} else if (inputArr.get(i).equalsIgnoreCase("end")) {
					inputData.setDates(extractDate(), "end");
					remove("end");
				} else {
					inputData.setDates(extractDate(), "end");
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

	private ArrayList<DateTime> extractDate() {
		ArrayList<DateTime> dateArr = new ArrayList<DateTime>();
		for (int i = 0; i< inputArr.size(); i++) {
			if (DateParser.isDate(inputArr.get(i))) {
				String date = inputArr.remove(i);
				dateArr.add(DateParser.setDate(date));
			}
		}
		return dateArr;
	}

	private int countDate() {
		int count = 0;
		for (int i = 0; i< inputArr.size(); i++) {
			if (DateParser.isDate(inputArr.get(i))) {
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
		for (int i = 0; i < inputArr.size(); i ++) {
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
