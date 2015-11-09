package parser;

import java.util.ArrayList;

import logic.CommandType;

import org.joda.time.DateTime;
//@@author A0122061B

//This program is the output class from the parsing program for Daxuan to use to execute CRUD

public class CommandPackage {

    /*
     * ====================================================================
     * ATTRIBUTES
     * ====================================================================
     */
    private String command;
    private DatePackage dates;
    private String phrase;
    private String priority;
    private ArrayList<String> updateSequence;

    /*
     * ====================================================================
     * Constructors
     * ====================================================================
     */
    public CommandPackage(String cmd, String taskName) {
	command = cmd;
	phrase = taskName;
	dates = new DatePackage();
	priority = null;
    }

    public CommandPackage() {
	command = "";
	phrase = "";
	dates = new DatePackage();
	priority = null;
	updateSequence = new ArrayList<String>();
    }

    public String setCommand(String cmd) {
	command = cmd;
	return command;
    }

    public String setPhrase(String text) {
	phrase = text;
	return phrase;
    }

    public DatePackage setDates(ArrayList<DateTime> time, String condition) {
	if (time == null) {
	    return null;
	}
	if (condition.equalsIgnoreCase("start")) {
	    dates.setDate(time.get(0), null);
	} else {
	    dates.setDate(null, time.get(0));
	}
	return dates;

    }

    public DatePackage setDates(ArrayList<DateTime> time) {
	if (time.get(0).compareTo(time.get(1)) < 0) {
	    dates.setDate(time.get(0), time.get(1));
	} else {
	    dates.setDate(time.get(1), time.get(0));
	}
	return dates;
    }

    public String setPriority(String priority) {
	this.priority = priority;
	return priority;
    }

    public ArrayList<String> addUpdateSequence(String sequence) {
	updateSequence.add(sequence);
	return updateSequence;

    }

    // Accessors
    public DatePackage getDate() {
	return dates;
    }

    public DateTime startingTime() {
	return dates.startingTime();
    }

    public DateTime endingTime() {
	return dates.endingTime();
    }

    public String getPhrase() {
	return phrase;
    }

    public String getCommand() {
	return command;
    }

    public String getPriority() {
	return priority;
    }

    public ArrayList<String> getUpdateSequence() {
	return updateSequence;
    }

}
