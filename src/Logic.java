import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Logic {
	// This array will be used to store the messages
	private ArrayList<Task> taskList = new ArrayList<Task>();
	
	//constructor
	public Logic(){
		taskList = Storage.Read();
	}
	
	public ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORTBYNAME, SEARCH, EDIT, REDO, UNDO,
		SORTBYSTARTTIME,SORTBYDEADLINE;
	};
	
	public void executeCommand(CommandPackage commandInfo) {
		int taskIndex;
		String commandTypeString = commandInfo.getCommand();
		System.out.println("Get the command type string: " + commandTypeString);
		COMMAND_TYPE commandType = determineCommandType(commandTypeString);

		switch (commandType) {
			case ADD:
				addTask(commandInfo);
				System.out.println("Adding task.");
				Storage.write(taskList);
				break;
			case SORTBYSTARTTIME:
			    sortByStartTime();
			    break;
			case SORTBYDEADLINE:
			    sortByDeadline();
			    break;
			case EDIT:
				edit(commandInfo);
				Storage.write(taskList);
				break;
			case DELETE:
				delete(commandInfo.getPhrase());
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
				search(commandInfo.getPhrase());
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
		String target = commandInfo.getPhrase();
		for (Task task : taskList){
			if(task.getName().equals(target)){
				
				if(commandInfo.startingTime()!=null){
					task.setStartTime(commandInfo.startingTime());
				}
				if(commandInfo.endingTime()!=null){
					task.setDeadline(commandInfo.endingTime());
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
		for (Task task : taskList){
			if(task.getName().equals(string)){
				taskList.remove(task);
			}
		}
		return null;
	}

	// To add message to ArrayList text
	public Task addTask(CommandPackage commandInfo) {
			
		Task task= new Task(commandInfo.getPhrase());
		if(commandInfo.startingTime()!=null){
			task.setStartTime(commandInfo.startingTime());
		}
		if(commandInfo.endingTime()!=null){
			task.setDeadline(commandInfo.endingTime());
		}
	    task.setPriority(commandInfo.getPriority());
					
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

		if (commandTypeString.equalsIgnoreCase("create")) {
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
