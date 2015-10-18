package logic;

import java.util.ArrayList;


public class PriorityTaskList {
    
	private static ArrayList<Task> p1 = new ArrayList<Task>();
	private static ArrayList<Task> p2 = new ArrayList<Task>();
	private static ArrayList<Task> p3 = new ArrayList<Task>();
	
	public PriorityTaskList() {
		// TODO Auto-generated constructor stub
	}
	
	public static void addToPL(Task t){
		if(t.getPriority() == 1){
			p1.add(t);
		}else if(t.getPriority() == 2){
			p2.add(t);
		}else{
			p3.add(t);
		}
	}
	
	public static void deleteFromPL(Task t){
		if(t.getPriority() == 1){
			for (Task task : p1){
				if(task.getName().equalsIgnoreCase(t.getName())){
					p1.remove(task);
				}
			}
		}else if(t.getPriority() == 2){
			for (Task task : p2){
				if(task.getName().equalsIgnoreCase(t.getName())){
					p2.remove(task);
				}
			}
		}else{
			for (Task task : p3){
				if(task.getName().equalsIgnoreCase(t.getName())){
					p3.remove(task);
				}
			}
		}
	}

	public static ArrayList<Task> getP1() {
		return p1;
	}
	public static ArrayList<Task> getP2() {
		return p2;
	}
	public static ArrayList<Task> getP3() {
		return p3;
	}

}
