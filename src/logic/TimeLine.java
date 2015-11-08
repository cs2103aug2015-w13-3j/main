package logic;

import java.util.ArrayList;

import org.joda.time.DateTime;

//@@author A0133948W
public class TimeLine {
	private ArrayList<Task> startTimeLine;
	private ArrayList<Task> endTimeLine;
	private ArrayList<Task> floatTimeLine;
	private static TimeLine timeline = null;
	
	private TimeLine(){
		this.startTimeLine = new ArrayList<Task>();
		this.endTimeLine = new ArrayList<Task>();
		this.floatTimeLine = new ArrayList<Task>();
	}
	
	public static TimeLine getInstance(){
		if(timeline == null){
			timeline = new TimeLine();
		}
		return timeline;
	}
	
	/**
	* This operation adds the task to its respective timeLine
	* this operation maintain the Chronological order of timeline
	*
	* @param task to be added
	*/
	public void addToTL(Task t){
		//event task and task with only start time will be added into startTimeLine
		if(t.getStartTime() != null){
			DateTime time = t.getStartTime();		
			int index = findPositionStart(startTimeLine, time, 0, startTimeLine.size());
			startTimeLine.add(index, t);			
		}
		//deadline task will be added to endTimeLine
		else if(t.getEndTime() != null){
			DateTime time = t.getEndTime();
			int index = findPositionEnd(endTimeLine, time, 0, endTimeLine.size());
			endTimeLine.add(index, t);
		}else{
			floatTimeLine.add(t);
		}
	}
	
	/**
	* This operation find the position of the task in the startTimeline
	*
	* @param timeline, the time being searched, left index, right index.
	* @return position
	*/
	public int findPositionStart(ArrayList<Task> timeLine, DateTime time, int left, int right){
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
	
	/**
	* This operation find the position of the task in the endTimeline
	*
	* @param timeline, the time being searched, left index, right index.
	* @return position
	*/
	public int findPositionEnd(ArrayList<Task> timeLine, DateTime time, int left, int right){
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
	
	/**
	* This operation delete the specific task in timeline
	*
	* @param task to be deleted
	*/
	public void deleteFromTL(Task t){
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
	
	/* This operation delete the specific task in starttimeline
	*
	* @param timeline, time of the task, task name
	*/
	public void removeStart(ArrayList<Task> timeLine, DateTime time, String name){
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
	
	/* This operation delete the specific task in endtimeline
	*
	* @param timeline, time of the task, task name
	*/
	public void removeEnd(ArrayList<Task> timeLine, DateTime time, String name){
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
	
	/* This operation clear all the timeline
	*
	*/
	public void clear(){
		startTimeLine.clear();
		endTimeLine.clear();
		floatTimeLine.clear();
	}

	public ArrayList<Task> getStarttimeLine(){
		return startTimeLine;
	}

	public ArrayList<Task> getEndtimeLine() {
		return endTimeLine;
	}
	
	public ArrayList<Task> getFloattimeLine() {
		return floatTimeLine;
	}
    
}
