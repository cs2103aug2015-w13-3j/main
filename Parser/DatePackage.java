import org.joda.time.DateTime;

public class DatePackage {
	private DateTime start;
	private DateTime end;
	private DateTime deadline; //class not initiated yet

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
	public DateTime getStart() {
		return start;
	}

	public DateTime getEnd() {
		return end;
	}

}