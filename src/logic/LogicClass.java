package logic;

//@@author A0133949U
import storage.Storage;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import logic.command.AddCommand;
import logic.command.ClearCommand;
import logic.command.DeleteCommand;
import logic.command.RedoCommand;
import logic.command.SearchCommand;
import logic.command.SortCommand;
import logic.command.UndoCommand;
import logic.command.UpdateCommand;
import parser.CommandPackage;
import parser.DateParser;

public class LogicClass implements LogicInterface{
	// This array will be used to store the messages

	private static ArrayList<Task> archivedList = new ArrayList<Task>();
	private static boolean isSearchOp = false;
	Storage storage = Storage.getInstance();
	
	Manager mgr= Manager.getInstance();
	
	private String path = "";

	static LogicClass theOne = null;

	// constructor
	private LogicClass() {
		mgr.setTaskList(storage.read().get(0));
		mgr.setArchivedList(storage.read().get(0));
		
	}

	public static LogicClass getInstance() {

		if (theOne == null) {
			theOne = new LogicClass();
		}
		return theOne;
	}

	public ArrayList<Task> getTaskList(){

		if(isSearchOp==true)
			return mgr.getSearchList();
		
		return mgr.getTaskList();
	}
	
	public ArrayList<Task> getTaskListForSearcher(){
		
		return mgr.getTaskList();
	}
	
	

	//@author A0133949U
	public static ArrayList<String> getTodayTasks() {
		ArrayList<String> todayTasks = new ArrayList<String>();
		Task task = null;
		String taskString = "";

		for (int i = 0; i < taskList.size(); i++) {
			task = taskList.get(i);
			if (isTodayTask(task) == true) {
				taskString += task.getName() + " ";

				if (task.getStartTime() != null) {
					taskString += "from " + task.getStartTime();
				}
				if (task.getEndTime() != null) {
					taskString += "to " + task.getEndTime();
				}
				todayTasks.add(taskString);
				// System.out.println("taskString"+taskString);
			}
		}
		return todayTasks;
	}
	

	//@author A0133949U
	public void executeCommand(CommandPackage commandPackage) {
		isSearchOp = false;
		
		CommandType commandType = CommandType.valueOf(commandPackage.getCommand());
		
		Command cmd = null;

		switch (commandType) {
		case CREATE:
			cmd = new AddCommand(commandType,mgr,commandPackage);
			break;
		case UPDATE:
			cmd = new UpdateCommand(commandType,mgr,commandPackage);
			break;
		case DELETE:
			cmd = new DeleteCommand(commandType,mgr,commandPackage.getPhrase());
			break;
		case CLEAR:
			cmd = new ClearCommand(commandType,mgr);
			break;
		case SORT:
			cmd = new SortCommand(commandType,mgr,commandPackage);
			break;
		case SEARCH:
			isSearchOp = true;
			cmd = new SearchCommand(commandType,mgr,commandPackage);
			break;	
		case REDO:
			cmd = new RedoCommand(commandType,mgr);
			break;
		case UNDO:
			cmd = new UndoCommand(commandType,mgr);
			break;
		case MARK:
			cmd = new MarkCommand(commandType,mgr,commandPackage.getPhrase());
			break;
		case SET:
			setPath(commandPackage.getPhrase());
			break;
		case EXIT:
			System.exit(0);
		default:
			invalid();

		}
		
		ReturnPackage returnPackage = cmd.execute();
		storage.write(taskList);
		// undoRedo.addStateToUndo((ArrayList<Task>) taskList.clone());
	}

	//@author A0133949U
	private String invalid() {
		return "The command is invalid, please key in the valid command.";
	}

	//@author A0133949U
	public boolean setPath(String path) {
		return storage.setPath(path);
	}


	//@author A0133949U

	public boolean setPathFirstTime(){
		return storage.setPath(this.path);
	}

	//@author A0133949U
	public static boolean isTodayTask(Task t) {
		if (t.getEndTime() == null && t.getStartTime() == null) {
			return false;
		}

		DateTime now = new DateTime();
		LocalDate today = now.toLocalDate();
		LocalDate tomorrow = today.plusDays(1);

		DateTime startOfToday = today.toDateTimeAtStartOfDay(now.getZone());
		DateTime startOfTomorrow = tomorrow.toDateTimeAtStartOfDay(now.getZone());

		if (t.getEndTime() == null && t.getStartTime() == null) {
			return false;
		}

		if (t.getEndTime() != null) {
			if (t.getEndTime().isBefore(startOfToday)) {
				return false;
			}
		}

		if (t.getStartTime() != null) {
			if (t.getEndTime().isBefore(startOfToday)) {
				return false;
			}
		}

		if (t.getStartTime() != null && t.getEndTime() != null && t.getStartTime().isAfter(startOfToday)
				&& t.getEndTime().isBefore(startOfTomorrow)) {
			return true;
		}
		return false;
	}



}
