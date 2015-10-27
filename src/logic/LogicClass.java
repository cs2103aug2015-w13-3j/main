package logic;

import storage.Storage;
import java.util.ArrayList;
import java.util.Collections;

import parser.CommandPackage;

public class LogicClass {
	// This array will be used to store the messages
	private static ArrayList<Task> taskList = new ArrayList<Task>();
	private static ArrayList<Task> todayTasks = new ArrayList<Task>();
	private static ArrayList<Task> searchTaskList = new ArrayList<Task>();
	private static boolean isSearchOp = false;
	Storage storage = new Storage();
	UndoRedoOp undoRedo = null;

    static LogicClass theOne =null;
    
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
		if(isSearchOp == true){
			return searchTaskList;
		}
		
		return new ArrayList<Task>(taskList);
	}
	
	public static ArrayList<Task> getTaskListForSearcher() {
		
		return new ArrayList<Task>(taskList);
	}

	public static ArrayList<Task> getTodayTasks() {
		return (ArrayList<Task>) todayTasks.clone();
	}

	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH, EDIT, REDO, UNDO, SORTBYSTARTTIME, SORTBYDEADLINE
	};

	public void executeCommand(CommandPackage commandPackage) {
		// int taskIndex;
		isSearchOp=false;
		
		System.out.println("Get the command type string: " + commandPackage.getCommand());
		String commandTypeString = commandPackage.getCommand();
		

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
		case SORT:
			sort(commandPackage);
			Storage.write(taskList);
			break;
		case SEARCH:
			isSearchOp=true;
			search(commandPackage);
			break;
		case REDO:
			taskList = undoRedo.redo();
			break;
		case UNDO:
			taskList = undoRedo.undo();
			break;
		case EXIT:
			System.exit(0);
		default:
			invalid();

		}
		// undoRedo.addStateToUndo((ArrayList<Task>) taskList.clone());
	}

	private String invalid() {
		return "The command is invalid, please key in the valid command.";
	}


	public Task edit(CommandPackage commandInfo) {
		
		 
		ArrayList<String> update = commandInfo.getUpdateSequence();
		System.out.println("getupdatesequence 0="+ update.get(3));
		String target = update.get(1);

		for (Task task : taskList) {
			
			if (task.getName().equals(target)) {
				
				if(update.get(2).equals("task name")){
					if (update.get(3) != null) {
						task.setTaskName(update.get(3));
						System.out.println(task.getName());
					}
					
				}else if(update.get(2).equals("priority")){
					task.setPriority(update.get(3));
				}
				
				

				if (commandInfo.startingTime() != null) {
					task.setStartTime(commandInfo.startingTime());
				}
				if (commandInfo.endingTime() != null) {
					task.setEndTime(commandInfo.endingTime());
				}
				
			}
		}
		return null;

	}

	// To clear all content
	public void clear() {
		taskList.clear();
	}

	// To delete certain message
	public static Task delete(String string) {
		Task task = null;
		if (isInteger(string, 10)) { // delete by index
			int index = Integer.parseInt(string);
			task=taskList.remove(index-1);
			
		}else{//delete by name
			for (int i = 0; i < taskList.size(); i++) {
				task = taskList.get(i);
				if (task.getName().equals(string)) {
					taskList.remove(i);
					//break;
				}
			}
		}
		PriorityTaskList.deleteFromPL(task);
		//TimeLine.deleteFromTL(task);
		return task;
	}

	private static boolean isInteger(String s, int radix) {
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

	
	public static Task addTask(CommandPackage commandInfo) {

		Task task = new Task(commandInfo.getPhrase());
		if (commandInfo.startingTime() != null) {
			task.setStartTime(commandInfo.startingTime());
		}
		if (commandInfo.endingTime() != null) {
			task.setEndTime(commandInfo.endingTime());
		}
		if (commandInfo.getPriority() != null) {
			String pri = commandInfo.getPriority().trim();
			if (pri != null && pri != "") {
				task.setPriority(pri);
			}
		}
		
		// System.out.println("priority: " + pri);
		

		taskList.add(task);
		PriorityTaskList.addToPL(task);
		//TimeLine.addToTL(task);

		for (Task task1 : taskList) {
			System.out.println("sdfadfsdf" + task1.toString());
		}

		return task;

	}

	public String sort(CommandPackage commandPackage) {
		if(commandPackage.getPhrase().equals("name")){
			Collections.sort(taskList);
			return "sorted by name";
		}else if(commandPackage.getPhrase()=="date"){
			
			return "sorted by date";
		}else if(commandPackage.getPhrase()=="priority"){
			taskList= new ArrayList<Task>(PriorityTaskList.getP1());
			taskList.addAll(PriorityTaskList.getP2());
			taskList.addAll(PriorityTaskList.getP3());
			
			return "sorted by priority";
		}else{
			return "invalid sorting type";
		}
		
		
	}

	public ArrayList<Task> search(CommandPackage commandInfo) {
		Task task = new Task(commandInfo.getPhrase());
		if (commandInfo.startingTime() != null) {
			task.setStartTime(commandInfo.startingTime());
		}
		
		if (commandInfo.endingTime() != null) {
			task.setEndTime(commandInfo.endingTime());
		}
		
		String pri = commandInfo.getPriority().trim();
		
		if (pri != null && pri != "") {
			task.setPriority(pri);
		}
		
		//System.out.println("task name searcher" + task.getName());
		
		searchTaskList = new ArrayList<Task>(Searcher.search(task) );
		return searchTaskList;
		
		/**
		String taskWithKeyword = "";
		ArrayList<Task> taskContainKeyword = new ArrayList<Task>();
		for (Task task : taskList) {
			if (task.containKeyword(taskWithKeyword)) {
				taskContainKeyword.add(task);
			}
		}
		return taskWithKeyword.toString();
		*/
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
		} else if (commandTypeString.equalsIgnoreCase("sort")) {
			return COMMAND_TYPE.SORT;
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
