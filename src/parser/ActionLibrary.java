package parser;

import java.util.TreeMap;

//@author A0122061B

/**
 * This class stores all the possible actions that users can input to be
 * processed as a possible logic command.
 */
public class ActionLibrary {

    /*
     * ====================================================================
     * ATTRIBUTES
     * ====================================================================
     */

    private static boolean isPreProcessed = false;
    private static TreeMap<String, String> actionTree;

    /*
     * ====================================================================
     * Magic Constants
     * ====================================================================
     */
    private static final String NOT_FOUND = "invalid input";

    private static final String[] CREATE_LIST =
	{ "create", "add", "`a", "`c", "create", "`n", "new", "plus", "`p" };

    private static final String[] UNDO_LIST =
	{ "undo", "undo", "`un" };

    private static final String[] REDO_LIST =
	{ "redo", "redo", "`re" };

    private static final String[] UPDATE_LIST =
	{ "update", "`c", "change", "`e", "edit", "`u", "update" };

    private static final String[] DELETE_LIST =
	{ "delete", "bomb", "`b", "`del", "delete", "`rmv", "remove" };

    private static final String[] SEARCH_LIST =
	{ "search", "`s", "search", "`f", "find", "`dis", "display", "`rd", "read", "`s", "show" };

    private static final String[] SORT_LIST =
	{ "sort", "sort", "`st" };

    private static final String[] SET_PATH_LIST =
	{ "set", "set", "setPath", "`se" };

    private static final String[] MARK_LIST =
	{ "mark", "m" };

    private static final String[] CLEAR_LIST =
	{ "clear", "clear", "`clr", "reset", "`rst" };

    private static final String[] EXIT_LIST =
	{ "exit", "exit" };

    private static final String[][] ACTION_ARRAY =
	{ CREATE_LIST, REDO_LIST, UNDO_LIST, UPDATE_LIST, DELETE_LIST, SEARCH_LIST, SORT_LIST, SET_PATH_LIST,
		MARK_LIST, CLEAR_LIST, EXIT_LIST };

    /*
     * ====================================================================
     * Execute methods for ActionLibrary
     * ====================================================================
     */

    /**
     * If actionTree is not processed, adds the elements of ACTION_ARRAY into
     * it.
     * 
     * @return if ActionLibrary have been processed
     */
    protected static boolean preProcess() {
	if (!isPreProcessed) {
	    actionTree = new TreeMap<String, String>();
	    for (int i = 0; i < ACTION_ARRAY.length; i++) {
		String actionValue = ACTION_ARRAY[i][0];
		for (int j = 1; j < ACTION_ARRAY[i].length; j++) {
		    actionTree.put(ACTION_ARRAY[i][j], actionValue);
		}
	    }
	    isPreProcessed = true;
	}
	return isPreProcessed;
    }

    /**
     * Searches if input given matches an existing action command from the
     * library
     * 
     * @param input
     *            the word to be tested with ActionLibrary
     * @return the compiled word for the action, if not found, return NOT_FOUND
     */
    protected static String find(String input) {
	preProcess();
	String result = actionTree.get(input.toLowerCase());
	if (result == null) {
	    return NOT_FOUND;
	} else {
	    return result;
	}
    }
}
