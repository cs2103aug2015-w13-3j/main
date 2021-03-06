package parser;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

//@@author A0122061B

/**
 * This class stores all the possible date formats that users can input to be
 * used
 */

public class DateParser {

    /*
     * ====================================================================
     * Magic Constants
     * ====================================================================
     */
    
    private static DateParser theOne = null;
    // format -1 : invalid format
    private final int INVALID_FORMAT_TYPE = -1;

    // format 0 : full date, with year
    private final int FORMAT_TYPE_0 = 0;
    private final String DATE_FORMAT_0 = "dd/MM/YYYY";
    private final String DATE_FORMAT_1 = "dd-MM-YYYY";
    private final String DATE_FORMAT_2 = "dd.MM.YYYY";
    private final String DATE_FORMAT_3 = "dd/MMM/YYYY";
    private final String DATE_FORMAT_4 = "dd-MMM-YYYY";
    private final String DATE_FORMAT_5 = "dd.MMM.YYYY";

    // format 1 : with day and month only
    private final int FORMAT_TYPE_1 = 1;
    private final String DATE_FORMAT_6 = "dd/MM";
    private final String DATE_FORMAT_7 = "dd-MM";
    private final String DATE_FORMAT_8 = "dd/MMM";
    private final String DATE_FORMAT_9 = "dd-MMM";
    private final String DATE_FORMAT_10 = "dd.MMM";

    // format 2 : day of the week, Friday, etc.
    private final int FORMAT_TYPE_2 = 2;
    private final String DATE_FORMAT_11 = "E";

    // format 3 : just day, month, year mutually exclusively
    private final int FORMAT_TYPE_3 = 3;
    private final String DATE_FORMAT_12 = "dd/";
    private final String DATE_FORMAT_13 = "dd-";
    private final String DATE_FORMAT_14 = "dd.";
    private final String DATE_FORMAT_15 = "MMM";
    private final String DATE_FORMAT_16 = "/MM/";
    private final String DATE_FORMAT_17 = "-MM-";
    private final String DATE_FORMAT_18 = ".MM.";
    private final String DATE_FORMAT_19 = "YYYY";

    // format 4 : Referral days with respect to current date
    private final int FORMAT_TYPE_4 = 4;
    private final String DATE_TODAY = "today";
    private final String DATE_TOMORROW = "tomorrow";
    private final String DATE_TMRW = "tmrw";
    private final String DATE_TMR = "tmr";
    private final String DATE_YESTERDAY = "yesterday";

    // Indexes for ArrayList<DateTimeFormatter> dateFormats
    private final int FORMAT_TYPE_0_INDEX = 6;
    private final int FORMAT_TYPE_1_INDEX = 11;
    private final int FORMAT_TYPE_2_INDEX = 12;
    private final int FORMAT_TYPE_3_INDEX = 20;

    private final ArrayList<DateTimeFormatter> dateFormats =
	    new ArrayList<DateTimeFormatter>(Arrays.asList(DateTimeFormat.forPattern(DATE_FORMAT_0),
							   DateTimeFormat.forPattern(DATE_FORMAT_1),
							   DateTimeFormat.forPattern(DATE_FORMAT_2),
							   DateTimeFormat.forPattern(DATE_FORMAT_3),
							   DateTimeFormat.forPattern(DATE_FORMAT_4),
							   DateTimeFormat.forPattern(DATE_FORMAT_5),
							   DateTimeFormat.forPattern(DATE_FORMAT_6),
							   DateTimeFormat.forPattern(DATE_FORMAT_7),
							   DateTimeFormat.forPattern(DATE_FORMAT_8),
							   DateTimeFormat.forPattern(DATE_FORMAT_9),
							   DateTimeFormat.forPattern(DATE_FORMAT_10),
							   DateTimeFormat.forPattern(DATE_FORMAT_11),
							   DateTimeFormat.forPattern(DATE_FORMAT_12),
							   DateTimeFormat.forPattern(DATE_FORMAT_13),
							   DateTimeFormat.forPattern(DATE_FORMAT_14),
							   DateTimeFormat.forPattern(DATE_FORMAT_15),
							   DateTimeFormat.forPattern(DATE_FORMAT_15),
							   DateTimeFormat.forPattern(DATE_FORMAT_16),
							   DateTimeFormat.forPattern(DATE_FORMAT_17),
							   DateTimeFormat.forPattern(DATE_FORMAT_18),
							   DateTimeFormat.forPattern(DATE_FORMAT_19)));
    /*
     * ====================================================================
     * Constructors
     * ====================================================================
     */
    
    private DateParser() {
    }
    public static DateParser getInstance() {

	if (theOne == null) {
	    theOne = new DateParser();
	}
	return theOne;
    }

  
    /*
     * ====================================================================
     * Methods to execute DateParser
     * ====================================================================
     */

    /**
     * Sets the date to the one from the input, assuming if input is a valid
     * date format.
     * 
     * @param input
     *            the assumed string of valid date format
     * @return a DateTime object with the date to the input
     */
    protected DateTime setDate(String input) {
	int formatTypeNumber = getDateFormatType(input);
	DateTime date = new DateTime();

	if (formatTypeNumber == 0) {
	    date = parseDateFormat0(input);
	} else if (formatTypeNumber == FORMAT_TYPE_1) {
	    date = parseDateFormat1(input, date);
	} else if (formatTypeNumber == FORMAT_TYPE_2) {
	    date = parseDateFormat2(input, date);
	} else if (formatTypeNumber == FORMAT_TYPE_4) {
	    date = parseDateFormat4(input);
	}

	return date;
    }

    protected DateTime setDate() {
	return new DateTime();
    }

    /**
     * Get the date format type of the input.
     * 
     * @param input
     *            the time to be tested
     * @return the format of the input date with the following options
     *         returns 0 : contains day, month and year
     *         returns 1 : date contains day and month only
     *         returns 2 : date is written in english (Tuesday, wed etc)
     *         returns 3 : date is yesterday/today/tomorrow
     *         returns 4 : date is only specified
     *         returns -1 : invalid
     */
    protected int getDateFormatType(String input) {
	DateTime date;
	int i = 0;
	for (; i < dateFormats.size(); i++) {
	    try {
		date = dateFormats.get(i).parseDateTime(input);
		break;
	    } catch (IllegalArgumentException e) {
		continue;
	    }
	}

	if (i < FORMAT_TYPE_0_INDEX) {
	    return 0;
	} else if (i < FORMAT_TYPE_1_INDEX) {
	    return FORMAT_TYPE_1;
	} else if (i < FORMAT_TYPE_2_INDEX) {
	    return FORMAT_TYPE_2;
	} else if (i < FORMAT_TYPE_3_INDEX) {
	    return i;
	} else if (isFormat4(input)) {
	    return FORMAT_TYPE_4;
	}

	return INVALID_FORMAT_TYPE;
    }

    protected boolean isDate(String input) {
	int formatTypeNumber = getDateFormatType(input);
	assert (formatTypeNumber >= INVALID_FORMAT_TYPE
		&& (formatTypeNumber <= FORMAT_TYPE_4
		    || (FORMAT_TYPE_2_INDEX <= formatTypeNumber && formatTypeNumber < FORMAT_TYPE_3_INDEX)));

	if (formatTypeNumber == INVALID_FORMAT_TYPE) {
	    return false;
	} else {
	    return true;
	}
    }

    protected boolean isSameDate(DateTime date1, DateTime date2) {
	return (date1.getYear() == date2.getYear()) && (date1.getDayOfYear() == date2.getDayOfYear());
    }

    private DateTime parseDateFormat0(String input) {
	DateTime date = null;

	for (int i = 0; i < FORMAT_TYPE_0_INDEX; i++) {
	    try {
		date = dateFormats.get(i).parseDateTime(input);
	    } catch (IllegalArgumentException e) {
		continue;
	    }
	}

	return date;
    }

    private DateTime parseDateFormat1(String input, DateTime date) {
	int[] dateTokens = splitDayMonth(input);
	int targetDay = dateTokens[0];
	int targetMonth = dateTokens[1];
	int currentDay = date.getDayOfMonth();
	int currentMonth = date.getMonthOfYear();

	if (targetMonth < currentMonth || (targetMonth == currentMonth && targetDay < currentDay)) {
	    date = new DateTime(date.getYear() + 1, targetMonth, targetDay, 0, 0);
	} else {
	    date = new DateTime(date.getYear(), targetMonth, targetDay, 0, 0);
	}
	return date;
    }

    private DateTime parseDateFormat2(String input, DateTime date) {
	DateTime tempDate = dateFormats.get(FORMAT_TYPE_2_INDEX - 1).parseDateTime(input);
	int targetDayOfWeek = tempDate.getDayOfWeek();
	int currentDayOfWeek = date.getDayOfWeek();

	if (targetDayOfWeek < currentDayOfWeek) {
	    date = date.plusWeeks(1);
	}

	date = date.withDayOfWeek(targetDayOfWeek);
	return date;
    }

    private DateTime parseDateFormat4(String input) {
	DateTime today = new DateTime();

	if (input.equalsIgnoreCase(DATE_YESTERDAY)) {
	    return today.minusDays(1);
	} else if (input.equalsIgnoreCase(DATE_TOMORROW) || (input.equalsIgnoreCase(DATE_TMRW))
		   || (input.equalsIgnoreCase(DATE_TMR))) {
	    return today.plusDays(1);
	}

	return today;
    }

    private DateTime parseDateFormat5(String input, DateTime date) {
	int[] dateTokens = splitDayMonth(input);
	int targetDay = dateTokens[0];
	int currentDay = date.getDayOfMonth();
	int currentMonth = date.getMonthOfYear();

	if (targetDay < currentDay) {
	    date = new DateTime(date.getYear(), currentMonth + 1, targetDay, 0, 0);
	} else {
	    date = new DateTime(date.getYear(), currentMonth, targetDay, 0, 0);
	}
	return date;
    }

    private DateTime parseDateFormat6(String input, DateTime date) {
	int[] dateTokens = splitDayMonth(input);
	int targetMonth = dateTokens[0];
	int currentMonth = date.getMonthOfYear();

	if (targetMonth < currentMonth) {
	    date = new DateTime(date.getYear() + 1, targetMonth, 0, 0, 0);
	} else {
	    date = new DateTime(date.getYear(), targetMonth, 0, 0, 0);
	}
	return date;
    }

    private DateTime parseDateFormat7(String input, DateTime date) {
	int year = Integer.parseInt(input);
	date = new DateTime(year, 0, 0, 0, 0);
	return date;
    }

    private int[] splitDayMonth(String date) {
	String[] tokens = new String[2];
	if (date.contains("/")) {
	    tokens = date.split("/");
	} else if (date.contains("-")) {
	    tokens = date.split("-");
	}

	// System.out.println(tokens.length);
	int[] intTokens = new int[tokens.length];
	for (int i = 0; i < tokens.length; i++) {
	    try {
		intTokens[i] = Integer.parseInt(tokens[i]);
	    } catch (NumberFormatException e) {
		DateTime tempDate = DateTimeFormat.forPattern("MMM").parseDateTime(tokens[i]);
		intTokens[i] = tempDate.getMonthOfYear();
	    }
	}

	return intTokens;
    }

    protected ArrayList<DateTime> searchDate(String input) {
	ArrayList<DateTime> searchArea = new ArrayList<DateTime>();
	int formatTypeNumber = getDateFormatType(input);
	DateTime date = new DateTime();
	if (formatTypeNumber == 0 || formatTypeNumber == 1 || formatTypeNumber == 2
	    || formatTypeNumber == 4) {
	    if (formatTypeNumber == 0) {
		date = parseDateFormat0(input);
	    } else if (formatTypeNumber == 1) {
		date = parseDateFormat1(input, date);
	    } else if (formatTypeNumber == 2) {
		date = parseDateFormat2(input, date);
	    } else if (formatTypeNumber == 4) {
		date = parseDateFormat4(input);
	    }

	    searchArea.add(date.withTime(0, 0, 0, 0));
	    searchArea.add(date.withTime(23, 59, 59, 999));
	} else {
	    if (formatTypeNumber < 15) { // only date given
		date = parseDateFormat5(input, date);
		searchArea.add(date.withTime(0, 0, 0, 0));
		searchArea.add(date.withTime(23, 59, 59, 999));
	    } else if (formatTypeNumber < 19) {// only month is given
		date = parseDateFormat6(input, date);
		searchArea.add(date);
		searchArea.add(date.plusMonths(1).plusMillis(-1));
	    } else if (formatTypeNumber == 19) {
		date = parseDateFormat7(input, date);
		searchArea.add(date);
		searchArea.add(date.plusYears(1).plusMillis(-1));
	    }
	}
	return searchArea;
    }

    private boolean isFormat4(String input) {
	return input.equalsIgnoreCase(DATE_TODAY) || input.equalsIgnoreCase(DATE_TOMORROW)
	       || input.equalsIgnoreCase(DATE_YESTERDAY) || input.equalsIgnoreCase(DATE_TMRW)
	       || input.equalsIgnoreCase(DATE_TMR);
    }

}
