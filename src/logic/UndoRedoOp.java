package logic;

import java.util.ArrayList;
import java.util.Stack;


public class UndoRedoOp {

	private Stack<ArrayList<Task>> undoStack = null;
	private Stack<ArrayList<Task>> redoStack = null;
	private ArrayList<Task> initialState = null;
	private ArrayList<Task> currentState = null;
	
	public UndoRedoOp(ArrayList<Task> initialState){
		undoStack = new Stack<ArrayList<Task>>();
		redoStack = new Stack<ArrayList<Task>>();
		this.initialState = new ArrayList<Task>(initialState);
		this.currentState = new ArrayList<Task>(initialState);
		//undoStack.push(this.initialState);
	}
	
	public ArrayList<Task> undo(){
		if(!undoStack.isEmpty()){
			ArrayList<Task> current = undoStack.pop();
			redoStack.push(new ArrayList<Task>(current));
			
			if(!undoStack.isEmpty()){
				return undoStack.peek();
			}else{
				return initialState;
			}
		}
		return initialState;
	}
	
	public ArrayList<Task> redo(){
		if(!redoStack.isEmpty()){
			ArrayList<Task> previousState = redoStack.pop();
			undoStack.push(new ArrayList<Task>(previousState));
			return previousState;
		}
		return currentState;
	}
	
	public ArrayList<Task> addStateToUndo(ArrayList<Task> recentState){
		undoStack.push(new ArrayList<Task>(recentState));
		redoStack.clear();
		currentState = new ArrayList<Task>(recentState);
		return currentState;
	}
}
