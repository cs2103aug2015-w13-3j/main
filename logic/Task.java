import java.util.Date;



public class Task implements Comparable<Task>{
    public String name;
    public Date startTime;
    public Date endTime;
    public Date deadline;
    public String location;
    public String description;
    
    
    public Task(String name){
    	this.name=name;
    }
    
    public void setStartTime(Date startTime){
    	this.startTime=startTime;
    }
    
    public void setEndTime(Date endTime){
    	this.endTime=endTime;
    }
    
    public void setDeadline(Date deadline){
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
