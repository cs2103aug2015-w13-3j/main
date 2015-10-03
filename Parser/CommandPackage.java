import org.joda.time.DateTime;

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

	public void setDates(DateTime[] time) {
		dates.setDate(time[0]);
		if (time.length == 2) {
			dates.setDate(time[1]);
		}

	}

	// Accessors
	public DateTime startingTime() {
		return dates.getStart();
	}

	public DateTime endingTime() {
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


