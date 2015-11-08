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

    // format 0 : full date, with year
    private static final String DATE_FORMAT_0 = "dd/MM/YYYY";
    private static final String DATE_FORMAT_1 = "dd-MM-YYYY";
    private static final String DATE_FORMAT_2 = "dd.MM.YYYY";
    private static final String DATE_FORMAT_3 = "dd/MMM/YYYY";
    private static final String DATE_FORMAT_4 = "dd-MMM-YYYY";
    private static final String DATE_FORMAT_5 = "dd.MMM.YYYY";

    // format 1 : with day and month only
    private static final String DATE_FORMAT_6 = "dd/MM";
    private static final String DATE_FORMAT_7 = "dd-MM";
    private static final String DATE_FORMAT_8 = "dd/MMM";
    private static final String DATE_FORMAT_9 = "dd-MMM";
    private static final String DATE_FORMAT_10 = "dd.MMM";

    // format 2 : day of the week, Friday, etc.
    private static final String DATE_FORMAT_11 = "E";

    // format 3 : just day, month, year mutually exclusively
    private static final String DATE_FORMAT_12 = "dd/";
    private static final String DATE_FORMAT_13 = "dd-";
    private static final String DATE_FORMAT_14 = "dd.";
    private static final String DATE_FORMAT_15 = "MMM";
    private static final String DATE_FORMAT_16 = "/MM/";
    private static final String DATE_FORMAT_17 = "-MM-";
    private static final String DATE_FORMAT_18 = ".MM.";
    private static final String DATE_FORMAT_19 = "YYYY";

    // format 4 : Referral days with respect to current date
    private static final String DATE_TODAY = "today";
    private static final String DATE_TOMORROW = "tomorrow";
    private static final String DATE_TMRW = "tmrw";
    private static final String DATE_TMR = "tmr";
    private static final String DATE_YESTERDAY = "yesterday";

    
    //Indexes for ArrayList<DateTimeFormatter> dateFormats
    private static final int FORMAT_TYPE_0_INDEX = 6;
    private static final int FORMAT_TYPE_1_INDEX = 11;
    private static final int FORMAT_TYPE_2_INDEX = 12;
    private static final int FORMAT_TYPE_3_INDEX = 20;

    private static ArrayList<DateTimeFormatter> dateFormats = new ArrayList<DateTimeFormatter>(
	    Arrays.asList(DateTimeFormat.forPattern(DATE_FORMAT_0), DateTimeFormat.forPattern(DATE_FORMAT_1),
		    DateTimeFormat.forPattern(DATE_FORMAT_2), DateTimeFormat.forPattern(DATE_FORMAT_3),
		    DateTimeFormat.forPattern(DATE_FORMAT_4), DateTimeFormat.forPattern(DATE_FORMAT_5),
		    DateTimeFormat.forPattern(DATE_FORMAT_6), DateTimeFormat.forPattern(DATE_FORMAT_7),
		    DateTimeFormat.forPattern(DATE_FORMAT_8), DateTimeFormat.forPattern(DATE_FORMAT_9),
		    DateTimeFormat.forPattern(DATE_FORMAT_10), DateTimeFormat.forPattern(DATE_FORMAT_11),
		    DateTimeFormat.forPattern(DATE_FORMAT_12), DateTimeFormat.forPattern(DATE_FORMAT_13),
		    DateTimeFormat.forPattern(DATE_FORMAT_14), DateTimeFormat.forPattern(DATE_FORMAT_15),
		    DateTimeFormat.forPattern(DATE_FORMAT_15), DateTimeFormat.forPattern(DATE_FORMAT_16),
		    DateTimeFormat.forPattern(DATE_FORMAT_17), DateTimeFormat.forPattern(DATE_FORMAT_18),
		    DateTimeFormat.forPattern(DATE_FORMAT_19)));

    protected static DateTime setDate(String input) {
	int formatType = getDateFormatType(input);
	DateTime date = new DateTime();

	if (formatType == 0) {
	    date = parseDateFormat0(input);
	} else if (formatType == 1) {
	    date = parseDateFormat1(input, date);
	} else if (formatType == 2) {
	    date = parseDateFormat2(input, date);
	} else if (formatType == 4) {
	    date = parseDateFormat4(input);
	}

	return date;
    }

    protected static DateTime setDate() {
	return new DateTime();
    }

    protected static int getDateFormatType(String input) {
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
	    return 1;
	} else if (i < FORMAT_TYPE_2_INDEX) {
	    return 2;
	} else if (i < FORMAT_TYPE_3_INDEX) {
	    return i;
	} else if (isFormat4(input)) {
	    return 4;
	}

	return -1;
    }

    private static boolean isFormat4(String input) {
	return input.equalsIgnoreCase(DATE_TODAY) || input.equalsIgnoreCase(DATE_TOMORROW)
		|| input.equalsIgnoreCase(DATE_YESTERDAY) || input.equalsIgnoreCase(DATE_TMRW)
		|| input.equalsIgnoreCase(DATE_TMR);
    }

    protected static boolean isDate(String input) {
	int formatType = getDateFormatType(input);
	assert (formatType >= -1 && (formatType <= 4 || (12 <= formatType && formatType <= 19)));

	if (formatType == -1) {
	    return false;
	} else {
	    return true;
	}
    }

    protected static boolean isSameDate(DateTime date1, DateTime date2) {
	return (date1.getYear() == date2.getYear()) && (date1.getDayOfYear() == date2.getDayOfYear());
    }

    private static DateTime parseDateFormat0(String input) {
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

    private static DateTime parseDateFormat1(String input, DateTime date) {
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

    private static DateTime parseDateFormat2(String input, DateTime date) {
	DateTime tempDate = dateFormats.get(FORMAT_TYPE_2_INDEX - 1).parseDateTime(input);
	int targetDayOfWeek = tempDate.getDayOfWeek();
	int currentDayOfWeek = date.getDayOfWeek();

	if (targetDayOfWeek < currentDayOfWeek) {
	    date = date.plusWeeks(1);
	}

	date = date.withDayOfWeek(targetDayOfWeek);
	return date;
    }

    private static DateTime parseDateFormat4(String input) {
	DateTime today = new DateTime();

	if (input.equalsIgnoreCase(DATE_YESTERDAY)) {
	    return today.minusDays(1);
	} else if (input.equalsIgnoreCase(DATE_TOMORROW) || (input.equalsIgnoreCase(DATE_TMRW))
		|| (input.equalsIgnoreCase(DATE_TMR))) {
	    return today.plusDays(1);
	}

	return today;
    }

    private static DateTime parseDateFormat5(String input, DateTime date) {
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

    private static DateTime parseDateFormat6(String input, DateTime date) {
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

    private static DateTime parseDateFormat7(String input, DateTime date) {
	int year = Integer.parseInt(input);
	date = new DateTime(year, 0, 0, 0, 0);
	return date;
    }

    private static int[] splitDayMonth(String date) {
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

    protected static ArrayList<DateTime> searchDate(String input) {
	ArrayList<DateTime> searchArea = new ArrayList<DateTime>();
	int formatType = getDateFormatType(input);
	DateTime date = new DateTime();
	if (formatType == 0 || formatType == 1 || formatType == 2 || formatType == 4) {
	    if (formatType == 0) {
		date = parseDateFormat0(input);
	    } else if (formatType == 1) {
		date = parseDateFormat1(input, date);
	    } else if (formatType == 2) {
		date = parseDateFormat2(input, date);
	    } else if (formatType == 4) {
		date = parseDateFormat4(input);
	    }

	    searchArea.add(date.withTime(0, 0, 0, 0));
	    searchArea.add(date.withTime(23, 59, 59, 999));
	} else {
	    if (formatType < 15) { // only date given
		date = parseDateFormat5(input, date);
		searchArea.add(date.withTime(0, 0, 0, 0));
		searchArea.add(date.withTime(23, 59, 59, 999));
	    } else if (formatType < 19) {// only month is given
		date = parseDateFormat6(input, date);
		searchArea.add(date);
		searchArea.add(date.plusMonths(1).plusMillis(-1));
	    } else if (formatType == 19) {
		date = parseDateFormat7(input, date);
		searchArea.add(date);
		searchArea.add(date.plusYears(1).plusMillis(-1));
	    }
	}
	return searchArea;
    }



}
