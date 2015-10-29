package parser;

import org.joda.time.DateTime;

public class DatePackage {
	private DateTime start;
	private int startDateFormat;
	private int endDateFormat;
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
	
	public void setDate(String front, String back) {
		setStartFormat(front);
		setEndFormat(back);
		start = DateParser.setDate(front);
		end = DateParser.setDate(back);
	}


	private void setEndFormat(String front) {
		// TODO Auto-generated method stub
		
	}

	private void setStartFormat(String front) {
		// TODO Auto-generated method stub
		
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