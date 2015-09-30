import java.util.Date;

//This program is the output class from the parsing program for Daxuan to use to execute CRUD

//this is another conflict

//This is a conflict
public class CommandPackage {
	private String command;
	private DatePackage dates;
	private String task;
	private String priority;

	// Constructors
	public CommandPackage(String cmd, String taskName) {
		command = cmd;
		task = taskName;
		DatePackage dates = new DatePackage();
		priority = null;
	}

	public void setDates(Date[] time) {
		dates.setDate(time[0]);
		if (time.length == 2) {
			dates.setDate(time[1]);
		}

	}

	// Accessors
	public Date startingTime() {
		return dates.getStart();
	}

	public Date endingTime() {
		return dates.getEnd();
	}

	public String getCommand() {
		return command;
	}

	public String getTaskName() {
		return task;
	}

	public String getPriority() {
		return priority;
	}

}

class DatePackage {
	private Date start;
	private Date end;

	// Constructor
	public DatePackage() {
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
