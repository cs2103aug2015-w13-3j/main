//@@author A0133949U
package logic;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

import parser.CommandPackage;
import storage.Storage;

public class Manager {
	ArrayList<Task> taskList;
	UndoRedoOp undoRedo = null;
	TimeLine timeline;
	PriorityTaskList ptl;
	Searcher seacher;
	ArrayList<Task> searchTaskList;
	static Manager manager = null;
	ArrayList<Task> archivedList;
	Storage storage;

	// constructor
	public Manager() {
		taskList = new ArrayList<Task>();
		timeline = TimeLine.getInstance();
		ptl = ptl.getInstance();
		seacher = Searcher.getInstance();
		searchTaskList = new ArrayList<Task>();
		archivedList = new ArrayList<Task>();
		storage = Storage.getInstance();
		undoRedo = new UndoRedoOp(taskList);
	}

	public static Manager getInstance() {
		if (manager == null) {
			manager = new Manager();
		}
		return manager;
	}

	// accessor

	public ArrayList<Task> getTaskList() {
		return taskList;

	}

	public ArrayList<Task> getSearchList() {
		return searchTaskList;

	}

	public ArrayList<Task> getArichivedList() {
		return archivedList;

	}

	// functions
	public Task addToTaskList(Task task) {
		taskList.add(task);
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		ptl.addToPL(task);
		timeline.addToTL(task);
		storage.write(taskList, archivedList);

		return task;

	}

	public Task delete(int index) {
		Task t = taskList.remove(index);
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		ptl.deleteFromPL(t);
		;
		timeline.deleteFromTL(t);
		storage.write(taskList, archivedList);
		return t;

	}

	public void clear() {
		taskList.clear();
		ptl.clear();
		timeline.clear();
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		storage.write(taskList, archivedList);

	}

	public void sort(String type) {
		switch (type) {
		case "name":
			Collections.sort(taskList);
			break;
		case "time":
			taskList = new ArrayList<Task>(timeline.getStarttimeLine());
			taskList.addAll(timeline.getEndtimeLine());
			taskList.addAll(timeline.getFloattimeLine());
			break;
		case "priority":
			taskList = new ArrayList<Task>(ptl.getP1());
			taskList.addAll(ptl.getP2());
			taskList.addAll(ptl.getP3());
			taskList.addAll(ptl.getP4());
			break;
		}
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		storage.write(taskList, archivedList);

	}

	public void search(Task task) {
		System.out.println("name" + task.getName());
		if (task.getName().equals("done")) {
			searchTaskList = new ArrayList<Task>(archivedList);
		} else {
			searchTaskList = new ArrayList<Task>(seacher.search(task));
		}

	}

	public void redo() {
		taskList = undoRedo.redo();
		ptl.clear();
		timeline.clear();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			ptl.addToPL(task);
			timeline.addToTL(task);
		}
		storage.write(taskList, archivedList);

	}

	public void undo() {
		taskList = new ArrayList<Task>(undoRedo.undo());
		ptl.clear();
		timeline.clear();
		setptlAndTimeLine(taskList);
		storage.write(taskList, archivedList);
	}

	public Task mark(int index) {
		if (!taskList.isEmpty()) {
			Task t = taskList.remove(index);
			archivedList.add(t);
			System.out.println("al" + archivedList.toString());
			undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
			ptl.deleteFromPL(t);
			;
			timeline.deleteFromTL(t);
			storage.write(taskList, archivedList);
			return t;
		}
		return null;

	}

	public void setTaskList(ArrayList<Task> tl) {
		taskList = tl;

		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		// System.out.println(taskList.size());
		setptlAndTimeLine(taskList);

		storage.write(taskList, archivedList);

	}

	public void setArchivedList(ArrayList<Task> al) {
		archivedList = al;
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		storage.write(taskList, archivedList);

	}

	public void setptlAndTimeLine(ArrayList<Task> tl) {
		for (int i = 0; i < tl.size(); i++) {
			Task task = tl.get(i);
			ptl.addToPL(task);
			timeline.addToTL(task);
		}
		storage.write(taskList, archivedList);

	}

}
