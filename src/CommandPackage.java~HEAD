import java.util.ArrayList;

import org.joda.time.DateTime;

//This program is the output class from the parsing program for Daxuan to use to execute CRUD

public class CommandPackage {
	private String command;
	private DatePackage dates;
	private String phrase;
	private int priority;
	private ArrayList<String> updateSequence;

	// Constructors
	public CommandPackage(String cmd, String taskName) {
		command = cmd;
		phrase = taskName;
		dates = new DatePackage();
		priority = 3;
	}

	public CommandPackage() {
		command = "";
		phrase = "";
		dates = new DatePackage();
		priority = 3;
		updateSequence = new ArrayList<String>();
	}

	public void setCommand(String cmd) {
		command = cmd;
	}

	public void setPhrase(String text) {
		phrase = text;
	}

	public void setDates(ArrayList<DateTime> time, String condition) {
		if (condition.equalsIgnoreCase("start")) {
			dates.setDate(time.get(0), null);
		} else {
			dates.setDate(null, time.get(0));
		}

	}

	public void setDates(ArrayList<DateTime> time) {
		dates.setDate(time.get(0), time.get(1));

	}

	public void setPriority(int priority2) {
		priority = priority2;

	}

	// Accessors
	public DateTime startingTime() {
		return dates.getStart();
	}

	public DateTime endingTime() {
		return dates.getEnd();
	}

	public String getPhrase() {
		return phrase;
	}

	public String getCommand() {
		return command;
	}

	public int getPriority() {
		return priority;
	}

	public ArrayList<String> addUpdateSequence(String sequence) {
		updateSequence.add(sequence);
		return updateSequence;
		
	}
	public ArrayList<String>  getUpdateSequence() {
		return updateSequence;
	}

}
