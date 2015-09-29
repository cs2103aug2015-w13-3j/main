import java.util.Date;


public class Task {
    private String name;
    private Date startTime;
    private Date endTime;
    private Date deadline;
    private String location;
    private String description;
    private Status status;
    
    enum Status{
    	DONE, UNDONE, ARCHIVE
    }
    
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
	
	public void setStatus(Status status){
		this.status = status;
	}
	
	public String getTaskName(){
		return this.name;
	}
	
	public Date getStartTime(){
		return this.startTime;
	}
	
	public Date getEndTime(){
		return this.endTime;
	}
	
	public Date getDeadline(){
		return this.deadline;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public Status getstatus(){
		return this.status;
	}
}
