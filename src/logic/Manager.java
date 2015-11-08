//@@author A0133949U
package logic;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

import parser.CommandPackage;

public class Manager {
    ArrayList<Task> taskList;
    UndoRedoOp undoRedo = null;
    TimeLine timeline;
    PriorityTaskList ptl;
    Searcher seacher;
	ArrayList<Task> searchTaskList;
	static Manager manager = null;
	ArrayList<Task> archivedList;
    
	//constructor
    public Manager(){
    	taskList = new ArrayList<Task>();
    	timeline = TimeLine.getInstance();
    	 ptl = PriorityTaskList.getInstance();
    	 seacher = Searcher.getInstance();
    	 searchTaskList = new ArrayList<Task>();
    	 archivedList = new ArrayList<Task>();
    }
    
    public static Manager getInstance(){
		if(manager == null){
			manager = new Manager();
		}
		return manager;
	}
    
    //accessor
    
    public ArrayList<Task> getTaskList(){
    	return taskList;
    	
    }
    
    public ArrayList<Task> getSearchList(){
    	return searchTaskList;
    	
    }
    
    //functions
    public Task addToTaskList(Task task) {   	
		taskList.add(task);
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		PriorityTaskList.addToPL(task);
		timeline.addToTL(task);
		return task;

	}
    
    public Task delete(int index) { 
    	Task t = taskList.remove(index);    	
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		PriorityTaskList.deleteFromPL(t);;
		timeline.deleteFromTL(t);
		return t;

	}
    
    public void clear(){
    	taskList.clear();
		PriorityTaskList.clear();
		timeline.clear();
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));

    }
    
    public void sort(String type) {
    	switch(type){
    	case "name":
    		Collections.sort(taskList);
    	case "time":
    		taskList = new ArrayList<Task>(timeline.getStarttimeLine());
			taskList.addAll(timeline.getEndtimeLine());
			taskList.addAll(timeline.getFloattimeLine());
    	case "priority":
    		taskList = new ArrayList<Task>(PriorityTaskList.getP1());
			taskList.addAll(PriorityTaskList.getP2());
			taskList.addAll(PriorityTaskList.getP3());
			taskList.addAll(PriorityTaskList.getP4());
    	}
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));

	}
    
    public void search(Task task){
    	searchTaskList = new ArrayList<Task>(seacher.search(task));
  	
    }
    
    public void redo(){
    	taskList = undoRedo.redo();
		PriorityTaskList.clear();
		timeline.clear();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			PriorityTaskList.addToPL(task);
			timeline.addToTL(task);
		}
  	
    }
    
    public void undo(){
    	taskList = new ArrayList<Task>(undoRedo.undo());
		PriorityTaskList.clear();
		timeline.clear();
		setPriorityTaskListAndTimeLine(taskList);
  	
    }
    
    public Task mark(int index) { 
    	Task t = taskList.remove(index); 
    	archivedList.add(t);
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
		PriorityTaskList.deleteFromPL(t);;
		timeline.deleteFromTL(t);
		return t;
	}
    
    

    
    public void setTaskList(ArrayList<Task> tl){
    	taskList = tl;
    	undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
    	
    }
    
    public void setArchivedList(ArrayList<Task> al){
    	archivedList = al;
    	undoRedo.addStateToUndo(new ArrayList<Task>(taskList));
    	
    }
    
    public void setPriorityTaskListAndTimeLine(ArrayList<Task> tl){
    	for (int i = 0; i < tl.size(); i++) {
			Task task = tl.get(i);
			PriorityTaskList.addToPL(task);
			timeline.addToTL(task);
		}
    	
    }
    
    
}
