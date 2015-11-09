//@@author A0133915H
package logic;

import java.util.ArrayList;
import java.util.Stack;

public class UndoRedoOp {

	private Stack<ArrayList<Task>> undoStack = null;
	private Stack<ArrayList<Task>> redoStack = null;
	private Stack<ArrayList<Task>> archivedUndoStack = null;
	private Stack<ArrayList<Task>> archivedRedoStack = null;
	static UndoRedoOp theOne = null;
	
	private UndoRedoOp() {
		undoStack = new Stack<ArrayList<Task>>();
		redoStack = new Stack<ArrayList<Task>>();
		archivedUndoStack = new Stack<ArrayList<Task>>();
		archivedRedoStack = new Stack<ArrayList<Task>>();
	}
	
	public static UndoRedoOp getInstance(){
		if(theOne == null){
			theOne = new UndoRedoOp();
		}
		return theOne;
	}

	public ArrayList<ArrayList<Task>> undo() {
		System.out.println("undodododooddo");
		ArrayList<ArrayList<Task>> previousState = new ArrayList<ArrayList<Task>>();
		
		if(!undoStack.isEmpty() && archivedUndoStack.isEmpty()){
			undoStack.clear();
			archivedUndoStack.clear();
		}else if (undoStack.isEmpty() && !archivedUndoStack.isEmpty()){
			undoStack.clear();
			archivedUndoStack.clear();
		}
		// undoStack and archivedUndoStack should have the same length 
		if (!undoStack.isEmpty() && !archivedUndoStack.isEmpty()) {
			ArrayList<Task> current = undoStack.pop();
			ArrayList<Task> currentArchived = undoStack.pop();

			redoStack.push(new ArrayList<Task>(current));
			archivedRedoStack.push(new ArrayList<Task>(currentArchived));
			
			
			if (!undoStack.isEmpty() && !archivedUndoStack.isEmpty()) {
				previousState.add(new ArrayList<Task>(undoStack.peek()));
				previousState.add(new ArrayList<Task>(archivedUndoStack.peek()));
				return previousState;
			} else {
				return null;
			}
		}
		return null;
	}

	public ArrayList<ArrayList<Task>> redo() {
		ArrayList<ArrayList<Task>> previousState = new ArrayList<ArrayList<Task>>();
		
		if(!redoStack.isEmpty() && archivedRedoStack.isEmpty()){
			redoStack.clear();
			archivedRedoStack.clear();
		}else if (redoStack.isEmpty() && !archivedRedoStack.isEmpty()){
			redoStack.clear();
			archivedRedoStack.clear();
		}
		
		if (!redoStack.isEmpty() && !archivedRedoStack.isEmpty()) {
			ArrayList<Task> previousFromRedo = new ArrayList<Task>(redoStack.pop());
			ArrayList<Task> previousFromAchivedRedo = new ArrayList<Task>(archivedRedoStack.pop());
			previousState.add(previousFromRedo);
			previousState.add(previousFromAchivedRedo);
			undoStack.push(previousFromRedo);
			archivedUndoStack.push(previousFromAchivedRedo);
			return previousState;
		}
		return null;
	}

	public ArrayList<ArrayList<Task>> addStateToUndo(ArrayList<Task> recentState, ArrayList<Task> archivedList) {
		assert (recentState != null);
		assert (archivedList != null);
		undoStack.push(new ArrayList<Task>(recentState));
		archivedUndoStack.push(new ArrayList<Task>(archivedList));
		redoStack.clear();
		archivedRedoStack.clear();
		ArrayList<ArrayList<Task>> currentList = new ArrayList<ArrayList<Task>>();
		currentList.add(new ArrayList<Task>(recentState));
		currentList.add(new ArrayList<Task>(archivedList));
		// return for Junit test
		return currentList;
	}
}
