package parser;

import java.util.ArrayList;
import java.util.Arrays;

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
    private final String START_TERM_0 = "start";
    private final String START_TERM_1 = "from";
    private final String START_TERM_2 = "begin";
    private final String START_TERM_3 = "starts";
    private final String START_TERM_4 = "begins";

    private final String END_TERM_0 = "by";
    private final String END_TERM_1 = "to";
    private final String END_TERM_2 = "until";
    private final String END_TERM_3 = "ends";
    private final String END_TERM_4 = "end";
    private final String END_TERM_5 = "til";

    private ArrayList<String> startList = new ArrayList<String>(
	    Arrays.asList(START_TERM_0, START_TERM_1, START_TERM_2, START_TERM_3, START_TERM_4));

    private ArrayList<String> endList = new ArrayList<String>(
	    Arrays.asList(END_TERM_0, END_TERM_1, END_TERM_2, END_TERM_3, END_TERM_4, END_TERM_5));
   
    private static TimeLibrary theOne = null;
    

    /*
     * ====================================================================
     * Constructors
     * ====================================================================
     */

    private TimeLibrary() {

    }
    
    protected static TimeLibrary getInstance() {

	if (theOne == null) {
	    theOne = new TimeLibrary();
	}
	return theOne;
    }
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
