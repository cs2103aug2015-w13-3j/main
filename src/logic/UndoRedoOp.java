package logic;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.undo.UndoManager;


public class UndoRedoOp {

	private Stack<ArrayList<Task>> undoStack = null;
	private Stack<ArrayList<Task>> redoStack = null;
	private ArrayList<Task> initialState = null;
	
	public UndoRedoOp(ArrayList<Task> initialState){
		undoStack = new Stack<ArrayList<Task>>();
		//undoStack.push(initialState);
		redoStack = new Stack<ArrayList<Task>>();
		this.initialState = initialState;
	}
	
	public ArrayList<Task> undo(){
		
		if(!undoStack.isEmpty()){
			ArrayList<Task> currentState = undoStack.pop();
			System.out.println("after pop: "+ undoStack.toString());
			redoStack.push(currentState);
			System.out.println("current state: "+ currentState);
			
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
			undoStack.push(previousState);
			return previousState;
		}
		return initialState;
	}
	
	public ArrayList<Task> addStateToUndo(ArrayList<Task> recentState){
		undoStack.push(recentState);
		System.out.println("after adding state: "+ undoStack.toString());
		return recentState;
	}
}
