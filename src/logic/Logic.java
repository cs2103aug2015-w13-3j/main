package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;


public class Logic {
	// This array will be used to store the messages
	private ArrayList<Task> taskList = new ArrayList<Task>();
	
	//constructor
	public Logic(String ){
		Storage storage = new Storage();
		taskList
	}
	
	public ArrayList<Task> getTaskList() {
		return taskList;
	}



	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORTBYNAME, SEARCH, EDIT, REDO, UNDO,
		SORTBYSTARTTIME,SORTBYDEADLINE;
	};

	private String fileName="taskBomber0.1";

	// This method is to pass the file name from terminal to the string fileName
	public void setFileName(String filename) {
		fileName = filename;
	}


	private void executeCommand(CommandPackage commandInfo) {
		int taskIndex;
		String commandTypeString = commandInfo.getCommand();

		COMMAND_TYPE commandType = determineCommandType(commandTypeString);

		switch (commandType) {
			case ADD:
				addTask(commandInfo);
				break;
			case SORTBYSTARTTIME:
			    sortByStartTime();
			    break;
			case SORTBYDEADLINE:
			    sortByDeadline();
			    break;
			case EDIT:
				edit(commandInfo);
				break;
			case DELETE:
				delete(taskIndex);
				break;
			case CLEAR:
				clear();
				break;
			case SORTBYNAME:
				sortByName();
				break;
			case SEARCH:
				search(commandInfo.getKeywordForSearch());
				break;
			case EXIT:
				// Write to file only when the program exits.
				
				System.exit(0);
		}
	}
    
	public void sortByStartTime() {
		// TODO Auto-generated method stub
		
	}

	//assume edit by index
	public void sortByDeadline() {
		// TODO Auto-generated method stub
		
	}

	public Task edit(CommandPackage commandInfo) {
		Task task = taskList.get(commandInfo.getIndex);
		task
		
		
		return null;
		
	}

    
	// To clear all content
	public void clear() {
		taskList.clear();
	}

	// To delete certain message
	public Task delete(int taskIndex) {
		return taskList.remove(taskIndex);
	}

	// To add message to ArrayList text
	public Task addTask(CommandPackage commandInfo) {
			
		Task task= new Task(commandInfo.getTaskName());
		if(commandInfo.startingTime()!=null){
			task.setStartTime(commandInfo.startingTime());
		}else if(commandInfo.endingTime()!=null){
			task.setDeadline(commandInfo.endingTime());
		}else if(commandInfo.getPriority()!=null){
			task.setPriority(commandInfo.getPriority());
		}
					
		taskList.add(task);
		
		return task;
		
	}
	
	public void sortByName(){
		Collections.sort(taskList);
	}

	public String search(String keyword){ 
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
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("edit")) {
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
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

}
