package logic;

import java.util.ArrayList;
import org.joda.time.DateTime;
//@@author A0133948W
public class Searcher {
	
	private static Searcher searcher = null;
	
	private Searcher(){
	}
	
	public static Searcher getInstance(){
		if(searcher == null){
			searcher = new Searcher();
		}
		return searcher;	
	}
	
	/**
	* This operation extracts the information and search for matching task
	*
	* @param a task structure which captures search information
	* @return a list of tasks matching search query 
	*/
	public ArrayList<Task> search(Task target){
		ArrayList<Task> result = new ArrayList<Task>();
		if(target.getStartTime() != null){
			result = searchDate(target.getStartTime(), target.getEndTime());
			if(target.getPriority() !=null){
				result = searchPriorityFrom(target.getPriority(), result);
			}
			if(target.getName() != null){
				result = searchKeyword(target.getName(), result);
			}
		}else if(target.getPriority()!=null){
			result = searchPriority(target.getPriority());
			if(target.getName() != null){
				result = searchKeyword(target.getName(), result);
			}
		}else{
			if(target.getName() != null){
				result = searchKeyword(target.getName(), LogicClass.getInstance().getTaskListForSearcher());
			}
		}
		return result;
	}
	
	/**
	* This operation search for keyword on the given sample
	*
	* @param a keyword for sample
	* @param sample for searching on
	* @return a list of tasks matching search query 
	*/
	public ArrayList<Task> searchKeyword(String key, ArrayList<Task> sample){
		int i, j;
		String[] keywords = key.split(" ");
		String keyword;
		Task target;
		ArrayList<Task> sampleCopy = new ArrayList<Task>(sample);
		for(i = 0; i < keywords.length; i++){
			keyword = keywords[i];
			for(j = 0; j < sampleCopy.size(); j++){
			    target = sampleCopy.get(j);
				if(!target.getName().contains(keyword)){
			    	sampleCopy.remove(j);
			    	j--;
			    }					
			}
		}
		return sampleCopy;		
	}

	/**
	* This operation search for tasks with given priority
	* this operation is for search query solely for priority
	*
	* @param priority value to search
	* @return a list of tasks matching search query 
	*/
	public ArrayList<Task> searchPriority(int priority){
		ArrayList<Task> result = new ArrayList<Task>();
		if(priority == 1){
			result.addAll(PriorityTaskList.getInstance().getP1());
		}else if(priority == 2){
			result.addAll(PriorityTaskList.getInstance().getP2());
		}else if(priority == 3){
			result.addAll(PriorityTaskList.getInstance().getP3());
		}
		return result;
	}
	
	/**
	* This operation search for tasks with given priority on a given sample
	* this operation is for compound search query
	*
	* @param priority value to search
	* @param a sample to search on
	* @return a list of tasks matching search query 
	*/
	public ArrayList<Task> searchPriorityFrom(int priority, ArrayList<Task> sample){
		int i;
		Task target;
		for(i = 0; i < sample.size(); i++){
		    target = sample.get(i);
			if((target.getPriority() == null) || (target.getPriority() != priority)){
		    	sample.remove(i);
		    	i--;
		    }					
		}
		return sample;		
	}
	
	/**
	* This operation search for tasks whose dates fall in given range
	*
	* @param start point of searching range
	* @param end point of searching range
	* @return a list of tasks matching search query 
	*/
	public ArrayList<Task> searchDate(DateTime start, DateTime end){
		ArrayList<Task> result1 = new ArrayList<Task>(TimeLine.getInstance().getStarttimeLine());
		ArrayList<Task> result2 = new ArrayList<Task>(TimeLine.getInstance().getEndtimeLine());
		Task t;
		for(int i = 0; i < result1.size(); i++){
			t = result1.get(i);
			if(t.getStartTime().compareTo(end) > 0){
				result1.remove(i);
				i--;
			}else if((t.getEndTime())!=null){
				if(t.getEndTime().compareTo(start) < 0){
					result1.remove(i);
					i--;
				}
			}
		}
		for(int j = 0; j < result2.size(); j++){
		    t = result2.get(j);
		    if((t.getEndTime().compareTo(start) >= 0) && (t.getEndTime().compareTo(end) <= 0)){
		    	result1.add(t);
		    }
		}
		return result1;
	}
}
