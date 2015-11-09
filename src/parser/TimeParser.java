package parser;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
// @@author A0122061B

public class TimeParser {

    /*
     * ====================================================================
     * Magic Constants
     * 
     * Listed below are the formats for that accepts the dates.
     * Here are the legends for the mentioned formats (TIME_FORMAT_0 to
     * TIME_FORMAT_9.
     * 
     * <HH> = Hours in 24 hours format
     * <hh> = Hours in 12 hours format (aa will be use, mentioned below)
     * <mm> = Minutes of the day
     * <aa> = If clock format is in 12 hours, aa will indicate am or pm
     * 
     * The dates are seperated by < : >, < . > or nothing
     * ====================================================================
     */

    private final String TIME_FORMAT_0 = "HH:mm";
    private final String TIME_FORMAT_1 = "HH.mm";
    private final String TIME_FORMAT_2 = "HHmm";
    private final String TIME_FORMAT_3 = "hh:mmaa";
    private final String TIME_FORMAT_4 = "hh.mmaa";
    private final String TIME_FORMAT_5 = "hhmmaa";
    private final String TIME_FORMAT_6 = "hhaa";
    private final String TIME_FORMAT_7 = "haa";
    private final String TIME_FORMAT_8 = "HH:";
    private final String TIME_FORMAT_9 = "HH.";
    private final int FORMAT_SIZE = 10;

    private ArrayList<DateTimeFormatter> timeFormats =
	    new ArrayList<DateTimeFormatter>(Arrays.asList(DateTimeFormat.forPattern(TIME_FORMAT_0),
							   DateTimeFormat.forPattern(TIME_FORMAT_1),
							   DateTimeFormat.forPattern(TIME_FORMAT_2),
							   DateTimeFormat.forPattern(TIME_FORMAT_3),
							   DateTimeFormat.forPattern(TIME_FORMAT_4),
							   DateTimeFormat.forPattern(TIME_FORMAT_5),
							   DateTimeFormat.forPattern(TIME_FORMAT_6),
							   DateTimeFormat.forPattern(TIME_FORMAT_7),
							   DateTimeFormat.forPattern(TIME_FORMAT_8),
							   DateTimeFormat.forPattern(TIME_FORMAT_9)));
    /*
     * ====================================================================
     * Execute methods for TimeParser
     * ====================================================================
     */
    
    
    /**
     * Sets the hours and minutes of an existing date
     *
     * @param date
     *            An existing date with no specified timing (only date is
     *            specified)
     * @param input
     *            the string of hours and minute
     * @return the actual date with the time of the task
     */
    protected DateTime setTime(DateTime date, String input) {
	if (isValidFormat(input)) {
	    date = parseTimeFormat(date, input);
	    return date;
	} else {
	    return date;
	}
    }

    /**
     * Checks if the following word input is an accepted time format
     * 
     * @param input
     *            the string word to be tested
     * @return a boolean (true/false) if the tested input is a valid format
     */
    private boolean isValidFormat(String input) {
	DateTime date;
	int i = 0;
	for (; i < timeFormats.size(); i++) {
	    try {
		date = timeFormats.get(i).parseDateTime(input);
		break;
	    } catch (IllegalArgumentException e) {
		continue;
	    } catch (NullPointerException e) {
		continue;
	    }
	}

	if (i < FORMAT_SIZE) {
	    if (i == 2 && input.length() == 3) { // to bypass strings that is
						 // indicated as "123".
		return false;
	    }
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Checks if the following word input is an accepted search time format
     * (TIME_FORMAT_6 to TIME_FORMAT_9)
     * 
     * @param input
     *            the string word to be tested
     * @return a boolean (true/false) if the tested input is a valid format
     */
    protected boolean isValidSearchFormat(String input) {
	DateTime date;
	int i = 6;
	for (; i < timeFormats.size(); i++) {
	    try {
		date = timeFormats.get(i).parseDateTime(input);
		break;
	    } catch (IllegalArgumentException e) {
		continue;
	    } catch (NullPointerException e) {
		continue;
	    }
	}

	if (i < FORMAT_SIZE) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Checks if the following word input is an accepted search time format
     * (TIME_FORMAT_6 to TIME_FORMAT_9)
     * 
     * @param input
     *            the string word to be tested
     * @return the range of time for Logic Component to search
     */
    protected ArrayList<DateTime> searchTime(String input) {
	DateTime currentTime = new DateTime();
	DateTime time = new DateTime();
	;
	ArrayList<DateTime> searchArea = new ArrayList<DateTime>();
	;
	if (isValidSearchFormat(input)) {
	    for (int i = 6; i < FORMAT_SIZE; i++) {
		try {
		    time = timeFormats.get(i).parseDateTime(input);
		    System.out.println(time);
		} catch (IllegalArgumentException e) {
		    continue;
		}
	    }
	    time = new DateTime(time.getYear(),
				time.getMonthOfYear(),
				time.getDayOfMonth(),
				time.getHourOfDay(),
				0);

	    if (!currentTime.isBefore(time)) {
		time = time.plusDays(1);
	    }
	}
	searchArea.add(time);
	searchArea.add(time.plusHours(1).plusMillis(-1));
	return searchArea;
    }

    /**
     * A wrapper method for isValidFormat
     * 
     * @param input
     *            the string word to be tested
     * @return a boolean (true/false) if the tested input is a valid format
     */
    protected boolean isTime(String input) {
	return isValidFormat(input);
    }

    /**
     * Sets the hours and minutes of an existing date
     *
     * @param date
     *            An existing date with no specified timing (only date is
     *            specified)
     * @param input
     *            the string of hours and minute
     * @return the actual date with the time of the task
     */
    private DateTime parseTimeFormat(DateTime date, String input) {
	DateTime time = null;

	for (int i = 0; i < FORMAT_SIZE; i++) {
	    try {
		time = timeFormats.get(i).parseDateTime(input);
		System.out.println(time);
	    } catch (IllegalArgumentException e) {
		continue;
	    }
	}
	if (date == null) {
	    date = new DateTime();

	}
	time = new DateTime(date.getYear(),
			    date.getMonthOfYear(),
			    date.getDayOfMonth(),
			    time.getHourOfDay(),
			    time.getMinuteOfHour());
	if (date.isBefore(time)) {
	    return time;
	} else {
	    return time.plusDays(1);
	}
    }

}
