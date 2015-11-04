package logic;

import java.util.ArrayList;
import org.joda.time.DateTime;
//@author A0133948W
public class Searcher {
	
	public static ArrayList<Task> search(Task target){
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
				result = searchKeyword(target.getName(), LogicClass.getTaskListForSearcher());
			}
		}
		return result;

	}
	
	public static ArrayList<Task> searchKeyword(String key, ArrayList<Task> sample){
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

	public static ArrayList<Task> searchPriority(int priority){
		ArrayList<Task> result = new ArrayList<Task>();
		if(priority == 1){
			result.addAll(PriorityTaskList.getP1());
		}else if(priority == 2){
			result.addAll(PriorityTaskList.getP2());
		}else if(priority == 3){
			result.addAll(PriorityTaskList.getP3());
		}
		return result;
	}
	
	public static ArrayList<Task> searchPriorityFrom(int priority, ArrayList<Task> sample){
		int i;
		Task target;
		for(i = 0; i < sample.size(); i++){
		    target = sample.get(i);
			if(target.getPriority() != priority){
		    	sample.remove(i);
		    	i--;
		    }					
		}
		return sample;		
	}
	/*
	public static ArrayList<Task> searchDate(DateTime date){
		ArrayList<Task> result = new ArrayList<Task>();
		searchFromStart(TimeLine.getStarttimeLine(), date, result);
		searchFromEnd(TimeLine.getEndtimeLine(), date, result);
		return result;
	}*/
	public static ArrayList<Task> searchDate(DateTime start, DateTime end){
		ArrayList<Task> result1 = new ArrayList<Task>(TimeLine.getStarttimeLine());
		ArrayList<Task> result2 = new ArrayList<Task>(TimeLine.getEndtimeLine());
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
		    if((t.getEndTime().compareTo(start) > 0) && (t.getEndTime().compareTo(end) < 0)){
		    	result1.add(t);
		    }
		}
		return result1;
	}
	/*
	private static void searchFromStart(ArrayList<Task> timeLine, DateTime time, ArrayList<Task> result){
		int index1 = TimeLine.findPositionStart(timeLine, time, 0, timeLine.size());
		
		if(index1 == 0){
		}else{
			index1 --;
			int index2 = index1 + 1;
			while((index1 >= 0)&&(timeLine.get(index1).getStartTime().compareTo(time) == 0)){
				result.add(timeLine.get(index1));
				index1--;
			}
			while((index2 < timeLine.size())&&(timeLine.get(index2).getStartTime().compareTo(time) == 0)){
				result.add(timeLine.get(index2));
				index2++;
			}
		}
	}
	
	private static void searchFromEnd(ArrayList<Task> timeLine, DateTime time, ArrayList<Task> result){
		int index1 = TimeLine.findPositionEnd(timeLine, time, 0, timeLine.size());
		
		if(index1 == 0){
		}else{
			index1 --;
			int index2 = index1 + 1;
			while((index1 >= 0)&&(timeLine.get(index1).getStartTime().compareTo(time) == 0)){
				result.add(timeLine.get(index1));
				index1--;
			}
			while((index2 < timeLine.size())&&(timeLine.get(index2).getStartTime().compareTo(time) == 0)){
				result.add(timeLine.get(index2));
				index2++;
			}
		}
	}*/
	
}
