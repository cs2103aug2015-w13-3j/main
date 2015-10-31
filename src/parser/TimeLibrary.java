//package parser;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.TreeMap;
//
//import org.joda.time.DateTime;
//
//public class TimeLibrary {
//	private static final String START_TERM_0 = "starts";
//	private static final String START_TERM_1 = "from";
//	private static final String START_TERM_2 = "begins";
//	
//	private static final String END_TERM_0 = "ends by";
//	private static final String END_TERM_1 = "to";
//	private static final String END_TERM_2 = "until";
//	private static final int FORMAT_SIZE = 8;
//
//	private static ArrayList<String> startList = new ArrayList<String>(Arrays.asList(START_TERM_0, START_TERM_1,
//			START_TERM_2, START_TERM_3, START_TERM_4, START_TERM_5, START_TERM_6, START_TERM_7));
//
//	private static ArrayList<String> endList = new ArrayList<String>(Arrays.asList(END_TERM_0, END_TERM_1,
//			END_TERM_2, END_TERM_3, END_TERM_4, END_TERM_5, END_TERM_6, END_TERM_7));
//	public static boolean isStart(String string) {
//		String result = actionTree.get(string.toLowerCase());
//		if (result == null) {
//			return NOT_FOUND;
//		} else {
//			return result;
//		}
//	}
//}
