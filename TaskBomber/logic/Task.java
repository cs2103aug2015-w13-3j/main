import java.util.Date;

import org.joda.time.DateTime;



public class Task implements Comparable<Task>{
    public String name;
    public DateTime startTime;
    public DateTime endTime;
    public DateTime deadline;
    public String location;
    public String description;
    
    
    public Task(String name){
    	this.name=name;
    }
    
    public void setStartTime(DateTime startTime){
    	this.startTime=startTime;
    }
    
    public void setEndTime(DateTime endTime){
    	this.endTime=endTime;
    }
    
	public void setDeadline(DateTime deadline){
    	this.deadline=deadline;
    }
    
    public void setLocation(String location){
    	this.location=location;
    }
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public boolean containKeyword(String keyword){
		if(name.matches(".*\\b"+keyword+"\\b.*")){
			return true;
		}else if(startTime.toString().matches(".*\\b"+keyword+"\\b.*")){
			return true;
		}else if(endTime.toString().matches(".*\\b"+keyword+"\\b.*")){
			return true;
		}else if(deadline.toString().matches(".*\\b"+keyword+"\\b.*")){
			return true;
		}else if(location.matches(".*\\b"+keyword+"\\b.*")){
			return true;
		}else if(description.matches(".*\\b"+keyword+"\\b.*")){
			return true;
		}	
		else{
			return false;
		} 
	}
	
	@Override
	public int compareTo(Task oTask) {
		int result=this.name.compareToIgnoreCase(oTask.name);
		if(result<0)
			return -1;
		else if(result==0)
			return 0;
		else 
			return 1;

	}
	
	@Override
	public String toString(){
		return name;//tbc
	}

}