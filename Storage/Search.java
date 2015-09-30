import java.util.Date;

public class Search {
	
	
	public static ArrayList<Task> result = new ArrayList<Task>();
	
	public static ArrayList<Task> search(//tbd){
		result.clear();
		//tbd
		return result;
	}
	
	public static void searchKeyword(String key){
		int count;
		Task target;
		for(count = 0; count < fileContent.size(); count++){
		    target = taskArray.get(count);
			if(target.getTaskName().contains(keyWord) || target.getTaskDescription().contains(keyWord)){
		    	result.add(target);
		    }					
		}
	}

	public static void searchPriority(String key){
		if(key = "p1"){
			result.addAll(PriorityTaskList.p1);
		}else if(key = "p2"){
			result.addAll(PriorityTaskList.p2);
		}else if(key = "p3"){
			result.addAll(PriorityTaskList.p3);
		}
	}
	
	public static void searchDate(Date date){
		long keyTime = date.getTime();
		searchOnTree(keyTime);
	}
	
	public static void searchOnTree(long keyTime){
		//todo
	}
}
