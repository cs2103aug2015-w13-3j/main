package logic;

import java.util.ArrayList;

import org.joda.time.DateTime;


public class TimeLine {
	private static ArrayList<Task> startTimeLine = new ArrayList<Task>();
	private static ArrayList<Task> endTimeLine = new ArrayList<Task>();
	private static ArrayList<Task> floatTimeLine = new ArrayList<Task>();
	
	public static void addToTL(Task t){
		if(t.getStartTime() != null){
			DateTime time = t.getStartTime();		
			int index = findPositionStart(startTimeLine, time, 0, startTimeLine.size());
			startTimeLine.add(index, t);
			
		}
		else if(t.getEndTime() != null){
			DateTime time = t.getEndTime();
			int index = findPositionEnd(endTimeLine, time, 0, endTimeLine.size());
			endTimeLine.add(index, t);
		}else{
			floatTimeLine.add(t);
		}
	}
	
	public static int findPositionStart(ArrayList<Task> timeLine, DateTime time, int left, int right){
		if(right == 0){
			return 0;
		}
		int mid = (left + right)/2;
		
	    if(timeLine.get(mid).getStartTime().compareTo(time) < 0){
	    	
	    	if(mid == left){
	    		
	    		return left + 1;
	    	}else{
	    		return findPositionStart(timeLine, time, mid, right);
	    	}	
		}else if(timeLine.get(mid).getStartTime().compareTo(time) > 0){
		    if(mid == 0){
		    	return 0;
		    }else{
		    	return findPositionStart(timeLine, time, left, mid);
		    }	
		}else{
			return mid + 1;
		}
	}
	
	public static int findPositionEnd(ArrayList<Task> timeLine, DateTime time, int left, int right){
		if(right == 0){
			return 0;
		}
		int mid = (left + right)/2;
		
	    if(timeLine.get(mid).getEndTime().compareTo(time) < 0){
	    	
	    	if(mid == left){
	    		
	    		return left + 1;
	    	}else{
	    		return findPositionEnd(timeLine, time, mid, right);
	    	}	
		}else if(timeLine.get(mid).getEndTime().compareTo(time) > 0){
		    if(mid == 0){
		    	return 0;
		    }else{
		    	return findPositionEnd(timeLine, time, left, mid);
		    }	
		}else{
			return mid + 1;
		}
	}
	
	

	
	public static void deleteFromTL(Task t){
		if(t.getStartTime() != null){
			DateTime time = t.getStartTime();
			String name = t.getName();	
			removeStart(startTimeLine, time, name);
		}
		
		else if(t.getEndTime() != null){
			DateTime time = t.getEndTime();
			String name = t.getName();	
			removeEnd(endTimeLine, time, name);
		}else{
			floatTimeLine.remove(t);
		}
	}
	public static void removeStart(ArrayList<Task> timeLine, DateTime time, String name){
		int index1 = findPositionStart(timeLine, time, 0, timeLine.size()) - 1;
		int index2 = index1 + 1;
		while((index1 >= 0)&&(timeLine.get(index1).getStartTime().compareTo(time) == 0)
				&&(!name.equals(timeLine.get(index1).getName()))){
			index1 = index1 - 1;
		}
		if(index1 >= 0){
			timeLine.remove(index1);
		}else{
			while((index2 < timeLine.size())&&(timeLine.get(index2).getStartTime().compareTo(time) == 0)
					&&(!name.equals(timeLine.get(index2).getName()))){
				index2 = index2 + 1;
			}
			timeLine.remove(index2);
		}
	}
	public static void removeEnd(ArrayList<Task> timeLine, DateTime time, String name){
		int index1 = findPositionEnd(timeLine, time, 0, timeLine.size()) - 1;
		int index2 = index1 + 1;
		while((index1 >= 0)&&(timeLine.get(index1).getEndTime().compareTo(time) == 0)
				&&(!name.equals(timeLine.get(index1).getName()))){
			index1 = index1 - 1;
		}
		if(index1 >= 0){
			timeLine.remove(index1);
		}else{
			while((index2 < timeLine.size())&&(timeLine.get(index2).getEndTime().compareTo(time) == 0)
					&&(!name.equals(timeLine.get(index2).getName()))){
				index2 = index2 - 1;
			}
			timeLine.remove(index2);
		}
	}
	
	public static void clear(){
		startTimeLine.clear();
		endTimeLine.clear();
		floatTimeLine.clear();
	}

	public static ArrayList<Task> getStarttimeLine(){
		return startTimeLine;
	}

	public static ArrayList<Task> getEndtimeLine() {
		return endTimeLine;
	}
	
	public static ArrayList<Task> getFloattimeLine() {
		return floatTimeLine;
	}
    
}
