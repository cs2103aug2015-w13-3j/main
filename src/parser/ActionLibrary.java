package parser;

import java.util.TreeMap;

//@author A0122061B


public class ActionLibrary {
	String NOT_FOUND = "invalid input";
	String[] createList = { "create", "add", "`a", "`c", "create", "`n", "new", "plus", "`p" };
	String[] undoList = { "undo", "undo", "`un" };
	String[] redoList = { "redo", "redo", "`re" };
	String[] updateList = { "update", "`c", "change", "`e", "edit", "`u", "update" };
	String[] deleteList = { "delete", "bomb", "`b", "`del", "delete", "`rmv", "remove" };
	String[] searchList = { "search", "`s", "search", "`f", "find", "`dis", "display", "`rd", "read", "`s", "show" };
	String[] sortList = { "sort", "sort", "`st" };
	String[] setPathList = { "set", "set", "setPath", "`se" };
	String[] markList = { "mark", "m" };
	String[] clearList = { "clear", "clear", "`clr", "reset" , "`rst"};
	String[] exitList = { "exit", "exit" };
	String[][] actionArr = { createList, redoList, undoList, updateList, 
			deleteList, searchList, sortList, setPathList, markList, clearList, exitList };
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
