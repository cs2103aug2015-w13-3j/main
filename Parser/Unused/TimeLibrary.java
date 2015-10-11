package parser;

import java.util.TreeMap;

public class TimeLibrary {
	String NOT_FOUND = "invalid input";
	String[] dayLong = { "monday", "tuesday", "wednesday", "thursday", "friday"};
	String[] dayShort = { "mon", "tue", "wed", "thurs", "fri"};
	String[] dayReferLong = {"today", "tomorrow"};
	String[] dayReferShort = {"tod", "tmrw"};
	String[] nextLong = {"week" , "month", "year"};
	String[] nextShort = {"wk" , "mnth", "yr"};
	String[][] timeArr = { dayLong, dayShort, dayReferLong, dayReferShort, nextLong, nextShort };
	TreeMap<String, String> timeTree;
	
	
/* Todo
  
 
	public TimeLibrary() {
		for (int i = 0; i < actionArr.length; i++) {
			String actionValue = actionArr[i][0];
			for (int j = 1; j < actionArr[i].length; j++) {
				actionTree.put(actionArr[i][j], actionValue);
			}
		}
	}

	public String find(String string) {
		String result = actionTree.get(string);
		if (result == null) {
			return NOT_FOUND;
		} else {
			return result;
		}
	}
	
	*/
}
