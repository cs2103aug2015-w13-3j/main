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

    static LogicClass theOne =null;
	// constructor
	private LogicClass(Storage storage) {
		this.storage = storage;
		taskList = storage.Read();
	}
    
    public static LogicClass getInstance(Storage storage){
        assert storage!=null;
        if(theOne==null){
            theOne= new LogicClass(storage);
        }
        return theOne;
    }

	public static ArrayList<Task> getTaskList() {
		return (ArrayList<Task>) taskList.clone();
	}
	
	public static ArrayList<Task> getTodayTasks(){
		return (ArrayList<Task>) todayTasks.clone();
	}
	

	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH, EDIT, REDO, UNDO
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
		case SORT:
			sort(commandPackage);
			Storage.write(taskList);
			break;
		case SEARCH:
			search(commandPackage);
			break;
		case EXIT:
			

			System.exit(0);
		default:
			invalid();

		}
	}

	private String invalid() {
		return "The command is invalid, please key in the valid command.";
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
		Task task =null;
		if(isInteger(string, 10)){ //delete by index
			int index = Integer.parseInt(string);
			task=taskList.remove(index-1);
			
		}else{//delete by name
			for (int i = 0; i < taskList.size(); i++) {
				task = taskList.get(i);
				if (task.getName().equals(string)) {
					taskList.remove(i);
					break;
				}
			}
		}
		PriorityTaskList.deleteFromPL(task);
		TimeLine.deleteFromTL(task);
		return task;
	}
	
	private boolean isInteger(String s, int radix){
		if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

	
	public Task addTask(CommandPackage commandInfo) {

		Task task = new Task(commandInfo.getPhrase());
		if (commandInfo.startingTime() != null) {
			task.setStartTime(commandInfo.startingTime());
		}
		if (commandInfo.endingTime() != null) {
			task.setEndTime(commandInfo.endingTime());
		}
		String pri = commandInfo.getPriority().trim();
		//System.out.println("priority: " + pri);
		if (pri != null && pri != "") {
			task.setPriority(pri);
		}

		taskList.add(task);
		PriorityTaskList.addToPL(task);
		TimeLine.addToTL(task);

		for (Task task1 : taskList) {
			System.out.println("sdfadfsdf" + task1.toString());
		}

		return task;

	}

	public String sort(CommandPackage commandPackage) {
		if(commandPackage.getPhrase()=="name"){
			Collections.sort(taskList);
			return "sorted by name";
		}else if(commandPackage.getPhrase()=="date"){
			
			return "sorted by date";
		}else if(commandPackage.getPhrase()=="priority"){
			taskList= new ArrayList<Task>(PriorityTaskList.getP1());
			taskList.
			
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
		//System.out.println("priority: " + pri);
		if (pri != null && pri != "") {
			task.setPriority(pri);
		}
		
		Searcher searcher = new Searcher();
		return searcher.search(task);
		
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

		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

}
