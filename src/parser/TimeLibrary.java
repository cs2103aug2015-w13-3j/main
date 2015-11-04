package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.joda.time.DateTime;
//@author A0122061B


public class TimeLibrary {
	private static final String START_TERM_0 = "starts";
	private static final String START_TERM_1 = "from";
	private static final String START_TERM_2 = "begins";
	
	private static final String END_TERM_0 = "ends by";
	private static final String END_TERM_1 = "to";
	private static final String END_TERM_2 = "until";

	private static ArrayList<String> startList = new ArrayList<String>(Arrays.asList(START_TERM_0, START_TERM_1,
			START_TERM_2));

	private static ArrayList<String> endList = new ArrayList<String>(Arrays.asList(END_TERM_0, END_TERM_1,
			END_TERM_2));
	public static boolean isStart(String string) {
		return startList.contains(string.toLowerCase());
	}
	
	public static boolean isEnd(String string) {
		return endList.contains(string.toLowerCase());
	}
}
