package logic;

import java.util.ArrayList;

//@@author A0133948W
public class PriorityTaskList {
    
	private ArrayList<Task> p1 = new ArrayList<Task>();
	private ArrayList<Task> p2 = new ArrayList<Task>();
	private ArrayList<Task> p3 = new ArrayList<Task>();
	private ArrayList<Task> p4 = new ArrayList<Task>();
	private static PriorityTaskList priorityList = null;
	
	private PriorityTaskList(){
		this.p1 = new ArrayList<Task>();
		this.p2 = new ArrayList<Task>();
		this.p3 = new ArrayList<Task>();
		this.p4 = new ArrayList<Task>();
	}
	
	public static PriorityTaskList getInstance(){
		if(priorityList == null){
			priorityList = new PriorityTaskList();
		}
		return priorityList;
	}
	
	public void addToPL(Task t){
		if(t.getPriority() == null){
			p4.add(t);
		}else if(t.getPriority() == 1){
			p1.add(t);
		}else if(t.getPriority() == 2){
			p2.add(t);
		}else{
			p3.add(t);
		}
	}
	
	public void deleteFromPL(Task t){
	    if(t.getPriority() == null){
	    	for (int i = 0; i<p4.size(); i++){
				if(p4.get(i).getName().equalsIgnoreCase(t.getName())){
					p4.remove(i);
					break;
				}
			}
	    }else if(t.getPriority() == 1){
			for (int i = 0; i<p1.size(); i++){
				if(p1.get(i).getName().equalsIgnoreCase(t.getName())){
					p1.remove(i);
					break;
				}
			}
		}else if(t.getPriority() == 2){
			for (int i = 0; i<p2.size(); i++){
				if(p2.get(i).getName().equalsIgnoreCase(t.getName())){
					p2.remove(i);
					break;
				}
			}
		}else{
			for (int i = 0; i<p3.size(); i++){
				if(p3.get(i).getName().equalsIgnoreCase(t.getName())){
					p3.remove(i);
					break;
				}
			}
		}
	}
	
	public void clear(){
		p1.clear();
		p2.clear();
		p3.clear();
		p4.clear();
	}

	public ArrayList<Task> getP1() {
		return p1;
	}
	
	public ArrayList<Task> getP2() {
		return p2;
	}
	
	public ArrayList<Task> getP3() {
		return p3;
	}
	
	public ArrayList<Task> getP4() {
		return p4;
	}
}
