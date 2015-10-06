import java.util.TreeMap;

public class ActionLibrary {
	String NOT_FOUND = "invalid input";
	String[] createList = { "create", "add", "~c", "create", "~n", "new" };
	String[] readList = { "read", "~dis", "display", "read", "~s", "show" };
	String[] updateList = { "update", "~e", "edit", "~u", "update" };
	String[] deleteList = { "delete", "~del", "delete", "~rmv", "remove" };
	String[] searchList = { "search", "~s", "search", "~f", "find" };
	String[] sortList = { "st", "sort", "~st" };
	String[][] actionArr = { createList, readList, updateList, deleteList, searchList, sortList };
	TreeMap<String, String> actionTree;

	public ActionLibrary() {
		actionTree = new TreeMap<String, String>();
		for (int i = 0; i < actionArr.length; i++) {
			String actionValue = actionArr[i][0];
			for (int j = 1; j < actionArr[i].length; j++) {
				actionTree.put(actionArr[i][j], actionValue);
			}
		}
	}

	public String find(String string) {
		String result = actionTree.get(string.toLowerCase());
		if (result == null) {
			return NOT_FOUND;
		} else {
			return result;
		}
	}
}
