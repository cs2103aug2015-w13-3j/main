package parser;

import org.joda.time.DateTime;

//@author A0122061B

public class DatePackage {
    /*
     * ====================================================================
     * ATTRIBUTES
     * ====================================================================
     */
    private DateTime start;
    boolean hasStartTime = false, hasStartMinute = false;
    boolean hasEndTime = false, hasEndMinute = false;
    boolean[] startTimeFormat = { hasStartTime, hasStartMinute };
    boolean[] endTimeFormat = { hasEndTime, hasEndMinute };
    private DateTime end;

    /*
     * ====================================================================
     * Constructors
     * ====================================================================
     */
    public DatePackage() {
	start = null;
	end = null;
    }

    /**
     * sets the day to the first attribute
     *
     * @param front
     *            a DateTime to be added as a start.
     * @return the actual date with the time of the task
     */
    public DateTime setDate(DateTime front) {
	start = front;
	end = null;
	return start;
    }

    protected DateTime setDate(DateTime front, DateTime back) {
	start = front;
	end = back;
	return end;
    }

    protected int setTimeFormat(boolean[] startFmt) {
	startTimeFormat = startFmt;
	return startFmt.length;
    }

    protected int setTimeFormat(boolean[] startFmt, boolean[] endFmt) {
	startTimeFormat = startFmt;
	endTimeFormat = endFmt;
	return endFmt.length;
    }

    /*
     * ====================================================================
     * Accessors
     * ====================================================================
     */
    public DateTime startingTime() {
	return start;
    }

    public DateTime endingTime() {
	return end;
    }

    public DateTime getDeadline() {
	return end;
    }

    public String toString() {
	return "Start : " + start + " End : " + end;
    }

}