package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.joda.time.DateTime;
//@author A0122061B

/**
 * This class stores all the refering time that users can input to be processed
 * as a start of end.
 */
public class TimeLibrary {

    /*
     * ====================================================================
     * Magic Constants
     * ====================================================================
     */
    private static final String START_TERM_0 = "starts";
    private static final String START_TERM_1 = "from";
    private static final String START_TERM_2 = "begins";

    private static final String END_TERM_0 = "ends by";
    private static final String END_TERM_1 = "to";
    private static final String END_TERM_2 = "until";

    private static ArrayList<String> startList = new ArrayList<String>(
	    Arrays.asList(START_TERM_0, START_TERM_1, START_TERM_2));

    private static ArrayList<String> endList = new ArrayList<String>(
	    Arrays.asList(END_TERM_0, END_TERM_1, END_TERM_2));

    /*
     * ====================================================================
     * Execute methods for TimeLibrary
     * ====================================================================
     */

    /**
     * Searches if input given matches a specified start referral
     * 
     * @param input
     *            the word to be tested with TimeLibrary
     * @return	a boolean if it matches a start referral
     */
    public static boolean isStart(String input) {
	return startList.contains(input.toLowerCase());
    }
    
    
    /**
     * Searches if input given matches a specified end referral
     * 
     * @param input
     *            the word to be tested with TimeLibrary
     * @return	a boolean if it matches a end referral
     */
    public static boolean isEnd(String input) {
	return endList.contains(input.toLowerCase());
    }
}
