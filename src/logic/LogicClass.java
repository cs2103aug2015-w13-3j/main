package logic;

import storage.Storage;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import parser.CommandPackage;
import parser.DateParser;

public class LogicClass {
	// This array will be used to store the messages
	private static ArrayList<Task> taskList = new ArrayList<Task>();

	private static ArrayList<Task> searchTaskList = new ArrayList<Task>();
	private static ArrayList<Task> archivedList = new ArrayList<Task>();
	private static boolean isSearchOp = false;
	Storage storage = Storage.getInstance();
	PriorityTaskList priorityList = PriorityTaskList.getInstance();
	TimeLine timeline = TimeLine.getInstance();
	Searcher seacher = Searcher.getInstance();
	UndoRedoOp undoRedo = new UndoRedoOp(taskList);

	private String path = null;

	static LogicClass theOne = null;

	// constructor
	//@author A0133949U

	private LogicClass() {
		taskList = storage.read();

		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			priorityList.addToPL(task);
			timeline.addToTL(task);
		}
		undoRedo = new UndoRedoOp(new ArrayList<Task>(taskList));
	}


	//@author A0133949U

	public static LogicClass getInstance() {

		if (theOne == null) {
			theOne = new LogicClass();
		}
		return theOne;
	}

	//@author A0133949U
	public static ArrayList<Task> getTaskList() {
		if (isSearchOp == true) {
			isSearchOp = false;
			return searchTaskList;
		}

		return new ArrayList<Task>(taskList);
	}

	//@author A0133949U
	public static ArrayList<Task> getTaskListForSearcher() {

		return new ArrayList<Task>(taskList);
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

		// System.out.("Get the command type string: " +
		// commandPackage.getCommand());
		String commandTypeString = commandPackage.getCommand();

		commandTypeString = commandTypeString.toUpperCase();

		CommandType commandType = CommandType.valueOf(commandTypeString);

		switch (commandType) {
		case CREATE:
			addTask(commandPackage);
			// System.out.println("Adding task.");
			storage.write(taskList);
			break;
		case UPDATE:
			edit(commandPackage);
			storage.write(taskList);
			break;
		case DELETE:
			// System.out.print("here"+commandInfo.getPhrase());
			delete(commandPackage.getPhrase());
			storage.write(taskList);
			break;
		case CLEAR:
			clear();
			// storage.write(taskList);
			break;
		case SORT:
			sort(commandPackage);
			storage.write(taskList);
			break;
		case SEARCH:
			isSearchOp = true;
			search(commandPackage);
			break;
		case REDO:
			taskList = undoRedo.redo();
			priorityList.clear();
			timeline.clear();
			for (int i = 0; i < taskList.size(); i++) {
				Task task = taskList.get(i);
				priorityList.addToPL(task);
				timeline.addToTL(task);
			}
			storage.write(taskList);
			break;
		case UNDO:
			taskList = new ArrayList<Task>(undoRedo.undo());

			priorityList.clear();
			timeline.clear();

			for (int i = 0; i < taskList.size(); i++) {
				Task task = taskList.get(i);
				priorityList.addToPL(task);
				timeline.addToTL(task);
			}

			storage.write(taskList);
			break;

		case MARK:
			mark(commandPackage.getPhrase());
			break;
		case SET:
			setPath(commandPackage.getPhrase());
			break;
		case EXIT:
			System.exit(0);
		default:
			invalid();

		}
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
    
	public Task edit(CommandPackage commandInfo) {

		ArrayList<String> update = commandInfo.getUpdateSequence();
		String target = update.get(1);
		Task task;
		Task newTask;

		for (int i = 0; i < taskList.size(); i++) {
			task = taskList.get(i);
			if (task.getName().equals(target)) {

				if (update.get(2).equalsIgnoreCase("name")) {
					if (update.get(3) != null) {
						task.setTaskName(update.get(3));
					}

				} else if (update.get(2).equals("#")) {
					task.setPriority(update.get(3));
				} else if (update.get(2).equalsIgnoreCase("startTime")) {
					// System.out.println("parse date" +
					// DateParser.setDate(update.get(3)));
<<<<<<< HEAD
					task.setStartTime(commandInfo.startingTime());
				} else if (update.get(2).equalsIgnoreCase("endTime")) {
					task.setEndTime(commandInfo.endingTime());
=======
					//task.setEndTime(DateParser.setDate(update.get(3)));
>>>>>>> 109c75bfcf379dab8d62504aaee12b30d5b639e9
				}

				newTask = new Task(task.getName());
				newTask.setStartTime(task.getStartTime());
				newTask.setEndTime(task.getEndTime());
				if (task.getPriority() != null) {
					newTask.setPriority(task.getPriority().toString());
				}

				taskList.remove(i);
				taskList.add(i, newTask);
				break;
			}

		}
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		return null;

	}

	// To clear all content
	//@author A0133949U
	public void clear() {
		taskList.clear();
		priorityList.clear();
		timeline.clear();
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		storage.write(taskList);
	}

	// To delete certain message
	//@author A0133949U
	public Task delete(String string) {
		Task task = null;

		if (isInteger(string, 10)) { // delete by index
			int index = Integer.parseInt(string);
			task = taskList.remove(index - 1);

		} else {// delete by name
			for (int i = 0; i < taskList.size(); i++) {
				task = taskList.get(i);
				if (task.getName().equals(string)) {
					taskList.remove(i);
					break;
				}
			}
		}
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		priorityList.deleteFromPL(task);
		timeline.deleteFromTL(task);
		return task;
	}

	//@author A0133949U
	public Task mark(String string) {
		Task task = null;
		if (isInteger(string, 10)) { // delete by index
			int index = Integer.parseInt(string);
			task = taskList.remove(index - 1);
			archivedList.add(task);

		} else {// delete by name
			for (int i = 0; i < taskList.size(); i++) {
				task = taskList.get(i);
				if (task.getName().equals(string)) {
					taskList.remove(i);
					break;
				}
			}
			archivedList.add(task);
		}

		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		priorityList.deleteFromPL(task);
		timeline.deleteFromTL(task);
		return task;
	}

	//@author A0133949U
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

	//@author A0133949U
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

		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		priorityList.addToPL(task);
		timeline.addToTL(task);
		return task;

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

	//@author A0133949U
	public String sort(CommandPackage commandPackage) {
		if (commandPackage.getPhrase().equals("name")) {
			Collections.sort(taskList);
			undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
			return "sorted by name";
		} else if (commandPackage.getPhrase().equals("time")) {
			taskList = new ArrayList<Task>(timeline.getStarttimeLine());
			taskList.addAll(timeline.getEndtimeLine());
			taskList.addAll(timeline.getFloattimeLine());

			undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
			return "sorted by date";
		} else if (commandPackage.getPhrase().equals("priority")) {
			taskList = new ArrayList<Task>(priorityList.getP1());
			taskList.addAll(priorityList.getP2());
			taskList.addAll(priorityList.getP3());
			taskList.addAll(priorityList.getP4());

			undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
			return "sorted by priority";
		} else {
			return "invalid sorting type";
		}

	}

	//@author A0133949U
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

		// System.out.println("task name searcher" + task.getName());

		searchTaskList = new ArrayList<Task>(seacher.search(task));
		return searchTaskList;

	}



}
