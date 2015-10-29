package logic;

import storage.Storage;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.omg.CORBA.INTERNAL;

import parser.CommandPackage;

public class LogicClass {
	// This array will be used to store the messages
	private static ArrayList<Task> taskList = new ArrayList<Task>();

	private static ArrayList<Task> searchTaskList = new ArrayList<Task>();
	private static ArrayList<Task> archivedList = new ArrayList<Task>();
	private static boolean isSearchOp = false;
	Storage storage = new Storage();
	UndoRedoOp undoRedo = null;

	static LogicClass theOne =null;
    
	// constructor
	private LogicClass(Storage storage) {
		this.storage = storage;
		taskList = Storage.read();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			PriorityTaskList.addToPL(task);
			TimeLine.addToTL(task);
		}
		undoRedo = new UndoRedoOp(taskList);
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

	public static ArrayList<String> getTodayTasks() {
		ArrayList<String> todayTasks = new ArrayList<String>();
		Task task = null;
		String taskString="";
		
		for (int i = 0; i < taskList.size(); i++) {
			task = taskList.get(i);
			if(isTodayTask(task)==true){
				taskString += task.getName()+" ";
				
				
				if (task.getStartTime() != null) {
					taskString += "from "+ task.getStartTime();
				}
				if (task.getEndTime() != null) {
					taskString += "to "+ task.getEndTime();
				}
                todayTasks.add(taskString);
                System.out.println("taskString"+taskString);
			}
		}
		return todayTasks;
	}

	// These are the possible command types
	enum COMMAND_TYPE {
		CREATE, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH, UPDATE, REDO, 
		UNDO, MARK, SETPATH;
	};

	public void executeCommand(CommandPackage commandPackage) {
		// int taskIndex;
		isSearchOp=false;
		
		System.out.println("Get the command type string: " + commandPackage.getCommand());
		String commandTypeString = commandPackage.getCommand();
		
		commandTypeString= commandTypeString.toUpperCase();
		
		
		COMMAND_TYPE commandType = COMMAND_TYPE.valueOf(commandTypeString);
		//COMMAND_TYPE commandType = determineCommandType(commandTypeString);

		switch (commandType) {
		case CREATE:
			addTask(commandPackage);
			System.out.println("Adding task.");
			Storage.write(taskList);
			break;
		case UPDATE:
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
			//Storage.write(taskList);
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
		case MARK:
			mark(commandPackage.getPhrase());
			break;
		case SETPATH:
			setPath();
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
	
	private setPath() {
		st
		return "The command is invalid, please key in the valid command.";
	}

	public Task edit(CommandPackage commandInfo) {
		
		 
		ArrayList<String> update = commandInfo.getUpdateSequence();
		System.out.println("getupdatesequence 0="+ update.get(2));
		String target = update.get(1);
		Task task ;

		for (int i=0;i<taskList.size();i++) {
			task= taskList.get(i);
			if (task.getName().equals(target)) {
				
				if(update.get(2).equals("name")){
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
			break;
		}
		undoRedo.addStateToUndo(taskList);
		return null;

	}

	// To clear all content
	public void clear() {
		taskList.clear();
		undoRedo.addStateToUndo(taskList);
		Storage.write(taskList);
	}

	// To delete certain message
	public Task delete(String string) {
		Task task = null;
        String	todayTaskString;
        
        if (isInteger(string, 10)) { // delete by index
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
        undoRedo.addStateToUndo(taskList);
		PriorityTaskList.deleteFromPL(task);
		TimeLine.deleteFromTL(task);
		return task;
	}

	public Task mark(String string) {
		Task task = null;
		Task todayTask=null;
		if (isInteger(string, 10)) { // delete by index
			int index = Integer.parseInt(string);
			task=taskList.remove(index-1);
			archivedList.add(task);
			
		}else{//delete by name
			for (int i = 0; i < taskList.size(); i++) {
				task = taskList.get(i);
				if (task.getName().equals(string)) {
					taskList.remove(i);
					break;
				}
			}
			archivedList.add(task);
		}

		undoRedo.addStateToUndo(taskList);
		PriorityTaskList.deleteFromPL(task);
		TimeLine.deleteFromTL(task);
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

	
	public Task addTask(CommandPackage commandInfo) {

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

		undoRedo.addStateToUndo(taskList);
		PriorityTaskList.addToPL(task);
		TimeLine.addToTL(task);

		for (Task task1 : taskList) {
			System.out.println("sdfadfsdf" + task1.toString());
		}

		return task;

	}
	
	public static boolean isTodayTask(Task t){
		if(t.getEndTime()==null && t.getStartTime()==null){
			return false;
		}
		
		DateTime now = new DateTime();
		LocalDate today = now.toLocalDate();
		LocalDate tomorrow = today.plusDays(1);

		DateTime startOfToday = today.toDateTimeAtStartOfDay(now.getZone());
		DateTime startOfTomorrow = tomorrow.toDateTimeAtStartOfDay(now.getZone());
		
		if(t.getEndTime()==null && t.getStartTime()==null){
			return false;
		}
		
		if(t.getEndTime() !=null ){
			if( t.getEndTime().isBefore(startOfToday)){
				return false;		
			}
		}
		
		if(t.getStartTime() !=null ){
			if( t.getEndTime().isBefore(startOfToday)){
				return false;		
			}
		}
		
		System.out.println(startOfToday);
		System.out.println(startOfTomorrow);
		
		if(t.getStartTime().isAfter(startOfToday) && 
				t.getEndTime().isBefore(startOfTomorrow)){
			return true;		
		}
		return false;
	}

	public String sort(CommandPackage commandPackage) {
		if(commandPackage.getPhrase().equals("name")){
			Collections.sort(taskList);
			undoRedo.addStateToUndo(taskList);
			return "sorted by name";
		}else if(commandPackage.getPhrase()=="date"){
			
			undoRedo.addStateToUndo(taskList);
			return "sorted by date";
		}else if(commandPackage.getPhrase().equals("priority")){
			taskList= new ArrayList<Task>(PriorityTaskList.getP1());
			taskList.addAll(PriorityTaskList.getP2());
			taskList.addAll(PriorityTaskList.getP3());
			taskList.addAll(PriorityTaskList.getP4());
			
			undoRedo.addStateToUndo(taskList);
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
	
	/**
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
	*/

}
