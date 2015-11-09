//@@author A0133915H
package logic;

import java.util.ArrayList;
import java.util.Stack;

/************************************************************************
 * This is a class for undo/redo operation implementation. It maintains 3
 * taskLists' undo and redo stack: taskList, searchTaskList and
 * archivedTaskList.
 * 
 * @author susumei
 *
 ************************************************************************/
public class UndoRedoOp {

	private Stack<ArrayList<Task>> undoStack = null;
	private Stack<ArrayList<Task>> redoStack = null;
	private Stack<ArrayList<Task>> archivedUndoStack = null;
	private Stack<ArrayList<Task>> archivedRedoStack = null;
	private Stack<ArrayList<Task>> searchUndoStack = null;
	private Stack<ArrayList<Task>> searchRedoStack = null;

	private static UndoRedoOp theOne = null;

	/**
	 * Constructor: initial all the stacks.
	 */
	private UndoRedoOp() {
		undoStack = new Stack<ArrayList<Task>>();
		redoStack = new Stack<ArrayList<Task>>();
		archivedUndoStack = new Stack<ArrayList<Task>>();
		archivedRedoStack = new Stack<ArrayList<Task>>();
		searchUndoStack = new Stack<ArrayList<Task>>();
		searchRedoStack = new Stack<ArrayList<Task>>();
	}

	public static UndoRedoOp getInstance() {
		if (theOne == null) {
			theOne = new UndoRedoOp();
		}
		return theOne;
	}

	/**
	 * undo: implement undo operation and maintain three task lists
	 * 
	 * @return ArrayList<ArrayList<Task> the arraylist that contains three task
	 *         lists
	 */
	public ArrayList<ArrayList<Task>> undo() {
		ArrayList<ArrayList<Task>> previousState = new ArrayList<ArrayList<Task>>();

		// undoStack and archivedUndoStack should have the same length
		if (!undoStack.isEmpty() && !archivedUndoStack.isEmpty() && !searchUndoStack.isEmpty()) {

			ArrayList<Task> current = undoStack.pop();
			ArrayList<Task> currentArchived = archivedUndoStack.pop();
			ArrayList<Task> currentSearch = searchUndoStack.pop();

			redoStack.push(new ArrayList<Task>(current));
			archivedRedoStack.push(new ArrayList<Task>(currentArchived));
			searchRedoStack.push(new ArrayList<Task>(currentSearch));

			if (!undoStack.isEmpty() && !archivedUndoStack.isEmpty() && !searchUndoStack.isEmpty()) {
				previousState.add(new ArrayList<Task>(undoStack.peek()));
				previousState.add(new ArrayList<Task>(archivedUndoStack.peek()));
				previousState.add(new ArrayList<Task>(searchUndoStack.peek()));
				return previousState;
			} else {
				return null;
			}
		} else if (undoStack.isEmpty() && archivedUndoStack.isEmpty() && searchUndoStack.isEmpty()) {
			return null;
		} else {
			// there must be something wrong if only some of the stacks are
			// empty.
			undoStack.clear();
			archivedUndoStack.clear();
			searchUndoStack.clear();
		}
		return null;
	}

	/**
	 * redo: implement redo operation and maintain three task lists
	 * 
	 * @return ArrayList<ArrayList<Task> the arraylist that contains three task
	 *         lists
	 */
	public ArrayList<ArrayList<Task>> redo() {
		ArrayList<ArrayList<Task>> previousState = new ArrayList<ArrayList<Task>>();

		if (!redoStack.isEmpty() && !archivedRedoStack.isEmpty() && !searchRedoStack.isEmpty()) {
			ArrayList<Task> previousFromRedo = new ArrayList<Task>(redoStack.pop());
			ArrayList<Task> previousFromAchivedRedo = new ArrayList<Task>(archivedRedoStack.pop());
			ArrayList<Task> previousFromSeachRedo = new ArrayList<Task>(searchRedoStack.pop());
			previousState.add(previousFromRedo);
			previousState.add(previousFromAchivedRedo);
			previousState.add(previousFromSeachRedo);
			undoStack.push(previousFromRedo);
			archivedUndoStack.push(previousFromAchivedRedo);
			searchUndoStack.push(previousFromSeachRedo);
			return previousState;
		} else if (redoStack.isEmpty() && archivedRedoStack.isEmpty() && searchRedoStack.isEmpty()) {
			return null;
		} else {
			// there must be something wrong if only some of the stacks are
			// empty.
			redoStack.clear();
			archivedRedoStack.clear();
			searchRedoStack.clear();
		}
		return null;
	}

	public ArrayList<ArrayList<Task>> addStateToUndo(ArrayList<Task> recentState, ArrayList<Task> archivedList,
			ArrayList<Task> searchList) {
		assert (recentState != null);
		assert (archivedList != null);
		assert (searchList != null);
		undoStack.push(new ArrayList<Task>(recentState));
		archivedUndoStack.push(new ArrayList<Task>(archivedList));
		searchUndoStack.push(new ArrayList<Task>(searchList));
		redoStack.clear();
		archivedRedoStack.clear();
		searchRedoStack.clear();
		ArrayList<ArrayList<Task>> currentList = new ArrayList<ArrayList<Task>>();
		currentList.add(new ArrayList<Task>(recentState));
		currentList.add(new ArrayList<Task>(archivedList));
		currentList.add(new ArrayList<Task>(searchList));
		// return for Junit test
		return currentList;
	}
}
