package parser;

import org.joda.time.DateTime;

public class DatePackage {
	private DateTime start;
	boolean hasStartTime = false, hasStartMinute = false;
	boolean hasEndTime = false, hasEndMinute = false;
	boolean[] startTimeFormat = {hasStartTime, hasStartMinute};
	boolean[] endTimeFormat = {hasEndTime, hasEndMinute};
	private DateTime end;

	// Constructor
	public DatePackage() {
		start = null;
		end = null;
	}

	// Set Dates
	public void setDate(DateTime front) {
		start = front;
		end = null;
	}

	public void setDate(DateTime front, DateTime back) {
		start = front;
		end = back;
	}
	
	public void setTimeFormat(boolean[] startFmt) {
		startTimeFormat = startFmt;
	}
	
	public void setTimeFormat(boolean[] startFmt, boolean[] endFmt) {
		startTimeFormat = startFmt;
		endTimeFormat = endFmt;
	}

	// Accessors
	public DateTime startingTime() {
		return start;
	}

	public DateTime endingTime() {
		return end;
	}
	
	public DateTime getDeadline() {
		return end;
	}

}