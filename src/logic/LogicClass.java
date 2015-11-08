package logic;

import storage.Storage;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.omg.CORBA.INTERNAL;

import parser.CommandPackage;
import parser.DateParser;

public class LogicClass {
	// This array will be used to store the messages
	private static ArrayList<Task> taskList = new ArrayList<Task>();

	private static ArrayList<Task> searchTaskList = new ArrayList<Task>();
	private static ArrayList<Task> archivedList = new ArrayList<Task>();
	private static boolean isSearchOp = false;
	Storage storage = new Storage();
	UndoRedoOp undoRedo = null;

	static LogicClass theOne = null;

	// constructor
	//@author A0133949U
	private LogicClass(Storage storage) {
		this.storage = storage;
		taskList = Storage.read();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			PriorityTaskList.addToPL(task);
			TimeLine.addToTL(task);
		}
		undoRedo = new UndoRedoOp(new ArrayList<Task>(taskList));
	}

	//@author A0133949U
	public static LogicClass getInstance(Storage storage) {
		assert storage != null;
		if (theOne == null) {
			theOne = new LogicClass(storage);
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
			// Storage.write(taskList);
			break;
		case SORT:
			sort(commandPackage);
			Storage.write(taskList);
			break;
		case SEARCH:
			isSearchOp = true;
			search(commandPackage);
			break;
		case REDO:
			taskList = undoRedo.redo();
			PriorityTaskList.clear();
			TimeLine.clear();
			for (int i = 0; i < taskList.size(); i++) {
				Task task = taskList.get(i);
				PriorityTaskList.addToPL(task);
				TimeLine.addToTL(task);
			}
			Storage.write(taskList);
			break;
		case UNDO:
			taskList = new ArrayList<Task>(undoRedo.undo());

			PriorityTaskList.clear();
			TimeLine.clear();

			for (int i = 0; i < taskList.size(); i++) {
				Task task = taskList.get(i);
				PriorityTaskList.addToPL(task);
				TimeLine.addToTL(task);
			}

			Storage.write(taskList);
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

	//@author A0133949U
	public Task edit(CommandPackage commandInfo) {

		ArrayList<String> update = commandInfo.getUpdateSequence();
		String target = update.get(1);
		Task task;
		Task newTask;

		for (int i = 0; i < taskList.size(); i++) {
			task = taskList.get(i);
			if (task.getName().equals(target)) {

				if (update.get(2).equals("name")) {
					if (update.get(3) != null) {
						task.setTaskName(update.get(3));
					}

				} else if (update.get(2).equals("priority")) {
					task.setPriority(update.get(3));
				} else if (update.get(2).equals("time")) {
					// System.out.println("parsedate" +
					// DateParser.setDate(update.get(3)));
					task.setEndTime(DateParser.setDate(update.get(3)));
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
		PriorityTaskList.clear();
		TimeLine.clear();
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		Storage.write(taskList);
	}

	// To delete certain message
	//@author A0133949U
	public Task delete(String string) {
		Task task = null;
		String todayTaskString;

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
		PriorityTaskList.deleteFromPL(task);
		TimeLine.deleteFromTL(task);
		return task;
	}

	//@author A0133949U
	public Task mark(String string) {
		Task task = null;
		Task todayTask = null;
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
		PriorityTaskList.deleteFromPL(task);
		TimeLine.deleteFromTL(task);
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
		PriorityTaskList.addToPL(task);
		TimeLine.addToTL(task);
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
			taskList = new ArrayList<Task>(TimeLine.getStarttimeLine());
			taskList.addAll(TimeLine.getEndtimeLine());
			taskList.addAll(TimeLine.getFloattimeLine());

			undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
			return "sorted by date";
		} else if (commandPackage.getPhrase().equals("priority")) {
			taskList = new ArrayList<Task>(PriorityTaskList.getP1());
			taskList.addAll(PriorityTaskList.getP2());
			taskList.addAll(PriorityTaskList.getP3());
			taskList.addAll(PriorityTaskList.getP4());

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

		searchTaskList = new ArrayList<Task>(Searcher.search(task));
		return searchTaskList;

	}



}
