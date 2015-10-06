import java.util.ArrayList;
import org.joda.time.DateTime;

public class Searcher {
	

	
	public static ArrayList<Task> search(Task target){
		ArrayList<Task> result = new ArrayList<Task>();
		if(target.getStartTime() != null){
			result = searchDate(target.getStartTime());
			if(target.getPriority() < 4){
				result = searchPriorityFrom(target.getPriority(), result);
			}
			if(target.getName() != null){
				result = searchKeyword(target.getName(), result);
			}
		}else if(target.getPriority() < 4){
			result = searchPriority(target.getPriority());
			if(target.getName() != null){
				result = searchKeyword(target.getName(), result);
			}
		}else{
			if(target.getName() != null){
				result = searchKeyword(target.getName(), Logic.getTaskList());
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
	
	public static ArrayList<Task> searchDate(DateTime date){
		ArrayList<Task> result = new ArrayList<Task>();
		searchFrom(TimeLine.getStarttimeLine(), date, result);
		searchFrom(TimeLine.getEndtimeLine(), date, result);
		return result;
	}
	
	private static void searchFrom(ArrayList<Task> timeLine, DateTime time, ArrayList<Task> result){
		int index1 = TimeLine.findPosition(timeLine, time, 0, timeLine.size());
		
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
	
}
