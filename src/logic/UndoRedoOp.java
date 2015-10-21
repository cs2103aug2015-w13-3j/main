package logic;

import java.util.ArrayList;
import java.util.Stack;


public class UndoRedoOp {

	private Stack<ArrayList<Task>> undoStack = null;
	private Stack<ArrayList<Task>> redoStack = null;
	private ArrayList<Task> initialState = null;
	
	public UndoRedoOp(ArrayList<Task> initialState){
		undoStack = new Stack<ArrayList<Task>>();
		redoStack = new Stack<ArrayList<Task>>();
		this.initialState = initialState;
	}
	
	public ArrayList<Task> undo(){
		if(!undoStack.isEmpty()){
			ArrayList<Task> currentState = undoStack.pop();
			redoStack.push(currentState);
			return (ArrayList<Task>) undoStack.peek().clone();
		}
		return initialState;
	}
	
	public ArrayList<Task> redo(){
		if(!redoStack.isEmpty()){
			ArrayList<Task> previousState = redoStack.pop();
			undoStack.push(previousState);
			return previousState;
		}
		return (ArrayList<Task>) undoStack.peek().clone();
	}
	
	public void addStateToUndo(ArrayList<Task> recentState){
		undoStack.push(recentState);
	}
}