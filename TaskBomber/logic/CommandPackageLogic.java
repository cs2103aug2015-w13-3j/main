import java.util.Date;

public class CommandPackageLogic {
	private String userCommand;
	private String commandType;
	private String taskName;
	private DatePackagelogic dates;	
	private String priority="";
	private String location="";
	private String keywordForSearch="";

	// Constructors
	public CommandPackageLogic(String cmd) {
		this.userCommand = cmd;
	}
	
	public void setTaskName(String taskName) {
        this.taskName=taskName;
	}
	
	public void setCommandType(String commandType) {
        this.commandType=commandType;
	}
	
	public void setDates(Date[] time) {
		dates.setDate(time[0]);
		if (time.length == 2) {
			dates.setDate(time[1]);
		}

	}
	
	public void setPriority(String priority) {
        this.priority=priority;
	}
	
	public void setLocation(String location) {
        this.location=location;
	}
	
	public void setKeywordForSearch(String keyword) {
        this.keywordForSearch=keyword;
	}
	

	// Accessors
	public Date getStartingTime() {
		return dates.getStart();
	}

	public Date getEndingTime() {
		return dates.getEnd();
	}

	public String getCommandType() {
		return commandType;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getPriority() {
		return priority;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getKeywordForSearch() {
		return keywordForSearch;
	}

}

class DatePackagelogic {
	private Date start;
	private Date end;

	// Constructor
	public DatePackagelogic() {
		start = null;
		end = null;
	}

	// Set Dates
	public void setDate(Date front) {
		start = front;
		end = null;
	}

	public void setDate(Date front, Date back) {
		start = front;
		end = back;
	}

	// Accessors
	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

}
