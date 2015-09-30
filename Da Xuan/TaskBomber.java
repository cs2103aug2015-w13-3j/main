
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.runner.manipulation.Sortable;

/**
 * This class is used to add, delete and display messages in the file you
 * created. The command format is given by the example interaction below:
 * 
 * Welcome to TextBuddy. mytextfile.txt is ready for use 
 * command: add little brown fox 
 * added to mytextfile.txt: “little brown fox” 
 * command: display 
 * 1.little brown fox 
 * command: add jumped over the moon 
 * added to mytextfile.txt: “jumped over the moon” 
 * command: display 
 * 1. little brown fox 
 * 2. jumped over the moon 
 * command: delete 2 
 * deleted from mytextfile.txt: “jumped over the moon” 
 * command: display 
 * 1. little brown fox 
 * command: clear 
 * all content deleted from mytextfile.txt 
 * command: display 
 * mytextfile.txt is empty 
 * command: exit
 * 
 * @author Da Xuan
 */

public class TaskBomber {

	private static final String MESSAGE_ADDED = "Task “%1$s” is added";
	private static final String MESSAGE_WELCOME = "Welcome to TaskBomber. Please specify a folder to store data";
	private static final String MESSAGE_DELETED = "Task %1$s deleted from";
	private static final String MESSAGE_CLEARED = "All tasks deleted";
	private static final String MESSAGE_EMPTY = "Your task list is empty";
	private static final String MESSAGE_SORTED = "all lines alphabetically sorted";
	private static final String MESSAGE_INVALID_CONTENT = "Content cannot be null";
	private static final String MESSAGE_INVALID_TYPE = "Command invalid";
	private static final String MESSAGE_INVALID_DELETE = "No such task, use display command to show all tasks";

	public static Scanner scanner = new Scanner(System.in);

	// This array will be used to store the messages
	public static ArrayList<Task> taskList = new ArrayList<Task>();

	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH, EDIT
	};

	public static String fileName;
	private static BufferedWriter bufferedWriter;
	private static BufferedReader bufferedReader;

	// This method is to pass the file name from terminal to the string fileName
	public static void setFileName(String filename) {
		fileName = filename;
	}

	public static void main(String[] args) {
		showToUser(MESSAGE_WELCOME);
		
		setFileName(args[0]);
		
	    getFileReady();	
		
		// Run the program until exit command was entered
		while (true) {
			String userCommand = readUserCommand();
			CommandParser commandParser= new CommandParser(userCommand);
			CommandPackage commandPackage=commandParser.getInput();
			//System.out.println(commandParser.getInput());
			String feedback = executeCommand(commandPackage);
			showToUser(feedback);
		}

	}

	private static String readUserCommand() {
		System.out.print("command:");
		String userCommand = scanner.nextLine();
		return userCommand;
	}

	private static void getFileReady() {
		try {
			File file = new File(fileName);
			file.createNewFile();

			//Initialize the Reader
			bufferedReader = new BufferedReader(new FileReader(fileName));
			
//tbc			// If the file already exits and has content, this is to read the
			// old messages into ArrayList text		
			//String line = null;
			//while ((line = bufferedReader.readLine()) != null) {
			//	text.add(removeFirstWord(line));
			//}

			// Initialize the writer
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			bufferedWriter = new BufferedWriter(fileWriter);	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public static String executeCommand(CommandPackage commandInfo) {

		String commandTypeString = commandInfo.getCommandType();

		COMMAND_TYPE commandType = determineCommandType(commandTypeString);

		switch (commandType) {
			case ADD:
				return addTask(commandInfo);
			case DISPLAY:
				return display();
			case EDIT:
				return edit(commandInfo);
			case DELETE:
				return delete(commandInfo.getTaskName());
			case CLEAR:
				return clear();
			case INVALID:
				return MESSAGE_INVALID_TYPE;
			case SORT:
				return sort();
			case SEARCH:
				return search(commandInfo.getKeywordForSearch());
			case EXIT:
				// Write to file only when the program exits.
				writeToFile();
				System.exit(0);
		}
		return null;
	}

	private static String edit(CommandPackage commandInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void writeToFile() {
		try {
			for (int index = 0; index < taskList.size(); index++) {
				bufferedWriter.write((index + 1) + ". " + taskList.get(index)
						+ "\n");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	// To clear all content
	private static String clear() {
		taskList.clear();
		return MESSAGE_CLEARED;
	}

	// To delete certain message
	private static String delete(String taskName) {

		//maybe can change to hashmap
		for (Task task : taskList){
			if(task.name.equalsIgnoreCase(taskName)){
				taskList.remove(task);
				return String.format(MESSAGE_DELETED,task.name);
			}
		}
		return MESSAGE_INVALID_DELETE;
	}

	// To display all the messages
	private static String display() {
        int i=1;
		if (taskList.isEmpty()) {
			return String.format(MESSAGE_EMPTY);
		} else {
			for (Task task : taskList){
				System.out.println(i+". "+task.toString());
				i++;
			}
			return "";
		}

	}

	// To add message to ArrayList text
	private static String addTask(CommandPackage commandInfo) {
			
		Task task= new Task(commandInfo.getTaskName());
		//task.setStartTime(commandInfo.getStartingTime());
		//task.setEndTime(commandInfo.getEndingTime());
		//task.setLocation(commandInfo.getLocation());
		taskList.add(task);
		return String.format(MESSAGE_ADDED, task.name);
		
	}
	
	private static String sort(){
		Collections.sort(taskList);
		return MESSAGE_SORTED;
	}

	private static String search(String keyword){ 
		String taskWithKeyword = "";
		ArrayList<Task> taskContainKeyword = new ArrayList<Task>();
		for (Task task : taskList){
			if(task.containKeyword(taskWithKeyword)){
				taskContainKeyword.add(task);
			}
		}
		return taskWithKeyword.toString();
	}
	/**
	 * This operation determines which supported command type to perform
	 * 
	 * @param commandTypeString
	 *            is the first word of the user command
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		if (commandTypeString == null)
			throw new Error("command type string cannot be null!");

		if (commandTypeString.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("edit")) {
			return COMMAND_TYPE.EDIT;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return COMMAND_TYPE.EXIT;
		} else if (commandTypeString.equalsIgnoreCase("sort")) {
			return COMMAND_TYPE.SORT;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
			return COMMAND_TYPE.SEARCH;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

	private static void showToUser(String text) {
		System.out.println(text);
	}

	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}

	private static String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}

}
