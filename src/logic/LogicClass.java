package logic;

import storage.Storage;
import java.util.ArrayList;
import java.util.Collections;

import parser.CommandPackage;

public class LogicClass {
	// This array will be used to store the messages
	private static ArrayList<Task> taskList = new ArrayList<Task>();
	private static ArrayList<Task> todayTasks = new ArrayList<Task>();
	Storage storage = new Storage();
	UndoRedoOp undoRedo = null;

	static LogicClass theOne = null;

	// constructor
	private LogicClass(Storage storage) {
		this.storage = storage;
		taskList = storage.Read();
		undoRedo = new UndoRedoOp((ArrayList<Task>) taskList.clone());
	}

	public static LogicClass getInstance(Storage storage) {
		assert storage != null;
		if (theOne == null) {
			theOne = new LogicClass(storage);
		}
		return theOne;
	}

	public static ArrayList<Task> getTaskList() {
		return (ArrayList<Task>) taskList.clone();
	}

	public static ArrayList<Task> getTodayTasks() {
		return (ArrayList<Task>) todayTasks.clone();
	}

	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORTBYNAME, SEARCH, EDIT, REDO, UNDO, SORTBYSTARTTIME, SORTBYDEADLINE
	};

	public void executeCommand(CommandPackage commandPackage) {
		// int taskIndex;
		String commandTypeString = commandPackage.getCommand();
		System.out.println("Get the command type string: " + commandTypeString);

		COMMAND_TYPE commandType = determineCommandType(commandTypeString);

		switch (commandType) {
		case ADD:
			addTask(commandPackage);
			System.out.println("Adding task.");
			Storage.write(taskList);
			break;
		case EDIT:
			edit(commandPackage);
			Storage.write(taskList);
			break;
		case DELETE:
			// System.out.print("here"+commandInfo.getPhrase());
			delete(commandPackage.getPhrase());
			Storage.write(taskList);
			break;
		case CLEAR:
			clear();
			Storage.write(taskList);
			break;
		case SORTBYNAME:
			sortByName();
			Storage.write(taskList);
			break;
		case SEARCH:
			search(commandPackage.getPhrase());
			break;
		case REDO:
			taskList = undoRedo.redo();
			break;
		case UNDO:
			taskList = undoRedo.undo();
			break;
		case EXIT:
			// Write to file only when the program exits.
			System.exit(0);
		default:
			invalid();

		}
		// undoRedo.addStateToUndo((ArrayList<Task>) taskList.clone());
	}

	private String invalid() {
		return "The command is invalid, please key in the valid command.";
	}

	public void sortByStartTime() {
		// TODO Auto-generated method stub

	}

	// assume edit by index
	public void sortByDeadline() {
		// TODO Auto-generated method stub

	}

	public Task edit(CommandPackage commandInfo) {
		String target = commandInfo.getPhrase();

		commandInfo.getUpdateSequence();

		for (Task task : taskList) {
			if (task.getName().equals(target)) {
				if (commandInfo.getPhrase() != null) {
					task.setTaskName(commandInfo.getPhrase());
				}

				if (commandInfo.startingTime() != null) {
					task.setStartTime(commandInfo.startingTime());
				}
				if (commandInfo.endingTime() != null) {
					task.setEndTime(commandInfo.endingTime());
				}
				task.setPriority(commandInfo.getPriority());
			}
		}
		return null;

	}

	// To clear all content
	public void clear() {
		taskList.clear();
	}

	// To delete certain message
	public Task delete(String string) {
		Task task = null;
		if (isInteger(string, 10)) { // delete by index
			int index = Integer.parseInt(string);
			task = taskList.remove(index);
		} else {// delete by name
			for (int i = 0; i < taskList.size(); i++) {
				task = taskList.get(i);
				if (task.getName().equals(string)) {
					taskList.remove(i);
					break;
				}
			}
		}
		return task;
	}

	private boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	// To add message to ArrayList text
	public Task addTask(CommandPackage commandInfo) {

		Task task = new Task(commandInfo.getPhrase());
		if (commandInfo.startingTime() != null) {
			task.setStartTime(commandInfo.startingTime());
		}
		if (commandInfo.endingTime() != null) {
			task.setEndTime(commandInfo.endingTime());
		}
		String pri = commandInfo.getPriority().trim();
		// System.out.println("priority: " + pri);
		if (pri != null && pri != "") {
			task.setPriority(pri);
		}

		taskList.add(task);

		for (Task task1 : taskList) {
			System.out.println("sdfadfsdf" + task1.toString());
		}

		return task;

	}

	public void sortByName() {
		Collections.sort(taskList);
	}

	public String search(String keyword) {
		String taskWithKeyword = "";
		ArrayList<Task> taskContainKeyword = new ArrayList<Task>();
		for (Task task : taskList) {
			if (task.containKeyword(taskWithKeyword)) {
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

		if (commandTypeString.equalsIgnoreCase("create")) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("update")) {
			return COMMAND_TYPE.EDIT;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return COMMAND_TYPE.EXIT;
		} else if (commandTypeString.equalsIgnoreCase("sort by name")) {
			return COMMAND_TYPE.SORTBYNAME;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
			return COMMAND_TYPE.SEARCH;
		} else if (commandTypeString.equalsIgnoreCase("sort by start time")) {
			return COMMAND_TYPE.SORTBYSTARTTIME;
		} else if (commandTypeString.equalsIgnoreCase("sort by deadline")) {
			return COMMAND_TYPE.SORTBYDEADLINE;
		} else if (commandTypeString.equalsIgnoreCase("undo")) {
			return COMMAND_TYPE.UNDO;
		} else if (commandTypeString.equalsIgnoreCase("redo")) {
			return COMMAND_TYPE.REDO;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

}
