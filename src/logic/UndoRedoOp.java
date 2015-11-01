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
		//undoStack.push(initialState);
		redoStack = new Stack<ArrayList<Task>>();
		this.initialState = new ArrayList<Task>(initialState);
		this.currentState = new ArrayList<Task>(initialState);
	}
	
	public ArrayList<Task> undo(){
		if(!undoStack.isEmpty()){
			System.out.println("Before pop: undoStack-- "+ undoStack.toString());
			ArrayList<Task> current = undoStack.pop();
			System.out.println("after pop: undoStack-- "+ undoStack.toString());
			redoStack.push(new ArrayList<Task>(current));
			System.out.println("current state: "+ current.toString());
			System.out.println("after push: redoStack-- "+ redoStack.toString());
			
			if(!undoStack.isEmpty()){
				System.out.println(undoStack.peek());
				return undoStack.peek();
			}else{
				return initialState;
			}
		}
		return initialState;
	}
	
	public ArrayList<Task> redo(){
		if(!redoStack.isEmpty()){
			System.out.println("before pop: redoStack-- "+ redoStack.toString());
			ArrayList<Task> previousState = redoStack.pop();
			System.out.println("after pop: redoStack-- "+ redoStack.toString());
			undoStack.push(new ArrayList<Task>(previousState));
			System.out.println("previous state: "+ previousState.toString());
			System.out.println("after push: undoStack-- "+ undoStack.toString());
			return previousState;
		}
		return currentState;
	}
	
	public ArrayList<Task> addStateToUndo(ArrayList<Task> recentState){
		System.out.println(recentState.toString());
		System.out.println("BEFORE adding state: "+ undoStack.toString());
		undoStack.push(new ArrayList<Task>(recentState));
		System.out.println("after adding state: "+ undoStack.toString());
		currentState = new ArrayList<Task>(recentState);
		return currentState;
	}
}
