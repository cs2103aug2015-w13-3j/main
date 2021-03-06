//@@author A0133949U
package logic;

import java.util.ArrayList;
import java.util.Collections;
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
	
	//constructor
    public Manager(){
    	taskList = new ArrayList<Task>();
    	timeline = TimeLine.getInstance();
    	ptl = PriorityTaskList.getInstance();
    	seacher = Searcher.getInstance();
    	searchTaskList = new ArrayList<Task>();
    	archivedList = new ArrayList<Task>();
    	storage = Storage.getInstance();
    	undoRedo = UndoRedoOp.getInstance();
    }
    
  
    
    public void initialize( ArrayList<Task> tl,  ArrayList<Task> al){
    	taskList = tl;
        setptlAndTimeLine(taskList);
        archivedList = al;
       
    	undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
    			new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
    	storage.write(taskList,archivedList);
    	
    }
    
    //accessor
    
    public ArrayList<Task> getTaskList(){
    	return taskList;
    	
    }
    
    public ArrayList<Task> getSearchList(){
    	return searchTaskList;
    	
    }
    
    public ArrayList<Task> getArichivedList(){
    	return archivedList;
    	
    }
    
    //functions
    public Task addToTaskList(Task task) {   
    	
		taskList.add(0,task);
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
				new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
		ptl.addToPL(task);
		timeline.addToTL(task);
		storage.write(taskList,archivedList);
		
		return task;

	}
    
    public Task delete(int taskIndex,int searchIndex,boolean inSearchStatus) {
    	Task t=null;
    	if(inSearchStatus || taskIndex == -1){
    		t = searchTaskList.remove(searchIndex);
    	}
    	
    	if(taskIndex == -1){ //meaning in view-archived mode
    		t = archivedList.remove(searchIndex);
    		
    		
    	}else{ //taskIndex != -1
    		t = taskList.remove(taskIndex);    	
        	
    		ptl.deleteFromPL(t);;
    		timeline.deleteFromTL(t);
    	}
    	
    	undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
    			new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
    	storage.write(taskList,archivedList);
    	return t;

	}
    
    public void clear(){
    	taskList.clear();
		ptl.clear();
		timeline.clear();
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
    			new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
		storage.write(taskList,archivedList);

    }
    
    public void sort(String type) {
    	switch(type){
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
    	case "#":
    		taskList = new ArrayList<Task>(ptl.getP1());
			taskList.addAll(ptl.getP2());
			taskList.addAll(ptl.getP3());
			taskList.addAll(ptl.getP4());
			break;
    	}
    	undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
    			new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
		storage.write(taskList,archivedList);

	}
    
    public void search(Task task){

    	if(task.getName().equals("done")){
    		searchTaskList = new ArrayList<Task>(archivedList);
    	}else{
    		
    		searchTaskList = new ArrayList<Task>(seacher.search(task));
    	}
    	
    	undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
    			new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
    	
  	
    }
    
    public boolean redo(){
    	ArrayList<ArrayList<Task>> lists = undoRedo.redo();
    	if(lists==null){
    		return false;
    	}
    	assert(lists != null);
    	taskList = lists.get(0);
    	archivedList = lists.get(1);
    	searchTaskList = lists.get(2);
    	
		ptl.clear();
		timeline.clear();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			ptl.addToPL(task);
			timeline.addToTL(task);
		}
		
		storage.write(taskList,archivedList);
  	    return true;
    }
    
    public boolean undo(){
    	ArrayList<ArrayList<Task>> lists = undoRedo.undo();
    	if(lists==null){
    		return false;
    	}
    	assert(lists != null);

    	taskList = new ArrayList<Task>(lists.get(0));
    	archivedList = new ArrayList<Task>(lists.get(1));
    	searchTaskList = lists.get(2);
    	
		ptl.clear();
		timeline.clear();
		setptlAndTimeLine(taskList);
		storage.write(taskList,archivedList);
		return true;
    }
    
    public boolean setPath(String pathInfo){
    	if(storage.setPath(pathInfo)){
			return true;	
		}else{
			return false;
		}
    }
    
    public Task mark(int index) { 
    	Task t = taskList.remove(index); 
    	archivedList.add(t);
    	System.out.println("al"+archivedList.toString());
    	undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
    			new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
		ptl.deleteFromPL(t);;
		timeline.deleteFromTL(t);
		storage.write(taskList,archivedList);
		return t;
		
	}
    
    public void update(int index, Task newTask){ 	
    	Task t=taskList.remove(index);
		taskList.add(index,newTask);
		ptl.deleteFromPL(t);
		timeline.deleteFromTL(t);
		ptl.addToPL(newTask);
		timeline.addToTL(newTask);
		undoRedo.addStateToUndo(new ArrayList<Task>(taskList),
    			new ArrayList<Task>(archivedList),new ArrayList<Task>(searchTaskList));
		storage.write(taskList,archivedList);
    }
    
    public void setptlAndTimeLine(ArrayList<Task> tl){
    	for (int i = 0; i < tl.size(); i++) {
			Task task = tl.get(i);
			ptl.addToPL(task);
			timeline.addToTL(task);
		}
    	storage.write(taskList,archivedList);
    	
    }
    
    // for testing only
    public void addToSearchList(Task task){
    	searchTaskList.add(task);
    	
    }
    
    
}
