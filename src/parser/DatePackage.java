package parser;

import org.joda.time.DateTime;

public class DatePackage {
	private DateTime start;
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