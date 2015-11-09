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
    private final String START_TERM_0 = "starts";
    private final String START_TERM_1 = "from";
    private final String START_TERM_2 = "begins";

    private final String END_TERM_0 = "ends by";
    private final String END_TERM_1 = "to";
    private final String END_TERM_2 = "until";

    private ArrayList<String> startList = new ArrayList<String>(
	    Arrays.asList(START_TERM_0, START_TERM_1, START_TERM_2));

    private ArrayList<String> endList = new ArrayList<String>(
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
    protected boolean isStart(String input) {
	return startList.contains(input.toLowerCase());
    }
    
    
    /**
     * Searches if input given matches a specified end referral
     * 
     * @param input
     *            the word to be tested with TimeLibrary
     * @return a boolean if it matches a end referral
     */
    protected boolean isEnd(String input) {
	return endList.contains(input.toLowerCase());
    }
}
