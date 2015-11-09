//@@author A0133915H
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import logic.Task;
import logic.UndoRedoOp;

public class UndoRedoOpTest {

	private static ArrayList<Task> initial = new ArrayList<Task>();
	private static ArrayList<Task> initialArchived = new ArrayList<Task>();
	private static ArrayList<Task> initialSearch = new ArrayList<Task>();

	/***************************************************************
	 * Scripted testing & white-box testing This test is only for the undo/redo
	 * function
	 ***************************************************************/

	@BeforeClass
	public static void oneTimeSetUp() {
		initial.clear();
		initialArchived.clear();
		initialSearch.clear();
	}

	@Before
	public void setUp() {
		// stub for testing the undo and redo functions
		initial.add(new Task("Meeting"));
		initial.add(new Task("SEP"));
		initial.add(new Task("Movie with john"));
		initialArchived.add(new Task("Meeting tmr"));
		initialArchived.add(new Task("SEP done"));
		initialArchived.add(new Task("Movie with john yes"));
		initialSearch.add(new Task("Meeting"));
	}

	@After
	public void tearDown() {
		initial.clear();
		initialArchived.clear();
		initialSearch.clear();
	}

	/**
	 * Test for undo function
	 */
	@Test
	public void undoTest() {

		UndoRedoOp op = UndoRedoOp.getInstance();

		op.addStateToUndo(initial, initialArchived, initialSearch);

		ArrayList<Task> status1 = new ArrayList<Task>();
		ArrayList<Task> status1A = new ArrayList<Task>();
		ArrayList<Task> status1S = new ArrayList<Task>();
		ArrayList<Task> status2 = new ArrayList<Task>();
		ArrayList<Task> status2A = new ArrayList<Task>();
		ArrayList<Task> status2S = new ArrayList<Task>();

		status1.add(new Task("Meeting"));
		status1.add(new Task("SEP"));
		status1.add(new Task("Movie with john"));
		status1.add(new Task("Dinner with Tom"));

		status1A.add(new Task("Meeting tmr"));
		status1A.add(new Task("SEP done"));
		status1A.add(new Task("Movie with john yes"));
		status1A.add(new Task("Dinner with Tom"));

		status1S.add(new Task("Meeting"));
		status1S.add(new Task("Internship"));

		status2.add(new Task("Meeting"));
		status2.add(new Task("SEP"));
		status2.add(new Task("Movie with john"));
		status2.add(new Task("Dinner with Tom"));
		status2.add(new Task("Party"));

		status2A.add(new Task("Meeting tmr"));
		status2A.add(new Task("SEP done"));
		status2A.add(new Task("Movie with john yes"));
		status2A.add(new Task("Dinner with Tom"));
		status2A.add(new Task("Party"));

		status2S.add(new Task("Meeting"));
		status2S.add(new Task("Internship"));
		status2S.add(new Task("Family Dinner"));

		ArrayList<ArrayList<Task>> statusOne = new ArrayList<ArrayList<Task>>(
				op.addStateToUndo(status1, status1A, status1S));
		assertEquals(3, statusOne.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), (statusOne.get(0).get(i)).getName());
			assertEquals(status1A.get(i).getName(), (statusOne.get(1).get(i)).getName());
		}

		for (int i = 0; i < 2; i++) {
			assertEquals(status1S.get(i).getName(), (statusOne.get(2).get(i)).getName());
		}

		ArrayList<ArrayList<Task>> statusTwo = new ArrayList<ArrayList<Task>>(
				op.addStateToUndo(status2, status2A, status2S));
		assertEquals(3, statusTwo.size());

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), (statusTwo.get(0).get(i)).getName());
			assertEquals(status2A.get(i).getName(), (statusTwo.get(1).get(i)).getName());
		}

		for (int i = 0; i < 3; i++) {
			assertEquals(status2S.get(i).getName(), (statusTwo.get(2).get(i)).getName());
		}

		ArrayList<ArrayList<Task>> statusThree = op.undo();
		assertEquals(3, statusThree.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusThree.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusThree.get(1).get(i).getName());
		}

		for (int i = 0; i < 2; i++) {
			assertEquals(status1S.get(i).getName(), (statusOne.get(2).get(i)).getName());
		}

		ArrayList<ArrayList<Task>> statusFour = op.undo();
		assertEquals(3, statusFour.size());

		for (int i = 0; i < 3; i++) {
			assertEquals(initial.get(i).getName(), statusFour.get(0).get(i).getName());
			assertEquals(initialArchived.get(i).getName(), statusFour.get(1).get(i).getName());
		}

		for (int i = 0; i < 1; i++) {
			assertEquals(initialSearch.get(i).getName(), statusFour.get(2).get(i).getName());
		}
	}

	/**
	 * Test for redo function
	 */
	@Test
	public void redoTest() {
		UndoRedoOp op = UndoRedoOp.getInstance();

		op.addStateToUndo(initial, initialArchived, initialSearch);

		ArrayList<Task> status1 = new ArrayList<Task>();
		ArrayList<Task> status1A = new ArrayList<Task>();
		ArrayList<Task> status1S = new ArrayList<Task>();
		ArrayList<Task> status2 = new ArrayList<Task>();
		ArrayList<Task> status2A = new ArrayList<Task>();
		ArrayList<Task> status2S = new ArrayList<Task>();

		status1.add(new Task("Meeting"));
		status1.add(new Task("SEP"));
		status1.add(new Task("Movie with john"));
		status1.add(new Task("Dinner with Tom"));

		status1A.add(new Task("Meeting tmr"));
		status1A.add(new Task("SEP done"));
		status1A.add(new Task("Movie with john yes"));
		status1A.add(new Task("Dinner with Tom haha"));

		status1S.add(new Task("Meeting"));
		status1S.add(new Task("Internship"));

		status2.add(new Task("Meeting"));
		status2.add(new Task("SEP"));
		status2.add(new Task("Movie with john"));
		status2.add(new Task("Dinner with Tom"));
		status2.add(new Task("Party"));

		status2A.add(new Task("Meeting tmr"));
		status2A.add(new Task("SEP done"));
		status2A.add(new Task("Movie with john yes"));
		status2A.add(new Task("Dinner with Tom"));
		status2A.add(new Task("Party"));

		status2S.add(new Task("Meeting"));
		status2S.add(new Task("Internship"));
		status2S.add(new Task("Family Dinner"));

		ArrayList<ArrayList<Task>> statusOne = op.addStateToUndo(status1, status1A, status1S);

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusOne.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusOne.get(1).get(i).getName());
		}

		for (int i = 0; i < 2; i++) {
			assertEquals(status1S.get(i).getName(), statusOne.get(2).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusTwo = op.addStateToUndo(status2, status2A, status2S);

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), statusTwo.get(0).get(i).getName());
			assertEquals(status2A.get(i).getName(), statusTwo.get(1).get(i).getName());
		}

		for (int i = 0; i < 3; i++) {
			assertEquals(status2S.get(i).getName(), statusTwo.get(2).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusThree = op.undo();
		assertEquals(3, statusThree.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusThree.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusThree.get(1).get(i).getName());
		}

		for (int i = 0; i < 2; i++) {
			assertEquals(status1S.get(i).getName(), statusThree.get(2).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusFour = op.redo();
		assertEquals(3, statusFour.size());

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), statusFour.get(0).get(i).getName());
			assertEquals(status2A.get(i).getName(), statusFour.get(1).get(i).getName());
		}

		for (int i = 0; i < 3; i++) {
			assertEquals(status2S.get(i).getName(), statusTwo.get(2).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusFive = op.undo();
		assertEquals(3, statusFive.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusFive.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusFive.get(1).get(i).getName());
		}

		for (int i = 0; i < 2; i++) {
			assertEquals(status1S.get(i).getName(), statusThree.get(2).get(i).getName());
		}
		
		ArrayList<ArrayList<Task>> statusSix = op.undo();
		assertEquals(3, statusSix.size());

		for (int i = 0; i < 3; i++) {
			assertEquals(initial.get(i).getName(), statusSix.get(0).get(i).getName());
			assertEquals(initialArchived.get(i).getName(), statusSix.get(1).get(i).getName());
		}

		for (int i = 0; i < 1; i++) {
			assertEquals(initialSearch.get(i).getName(), statusFour.get(2).get(i).getName());
		}
		
		ArrayList<ArrayList<Task>> statusSeven = op.redo();
		assertEquals(3, statusSeven.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusSeven.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusSeven.get(1).get(i).getName());
		}

		for (int i = 0; i < 2; i++) {
			assertEquals(status1S.get(i).getName(), statusThree.get(2).get(i).getName());
		}
		
		ArrayList<ArrayList<Task>> statusEight = op.redo();
		assertEquals(3, statusEight.size());

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), statusEight.get(0).get(i).getName());
			assertEquals(status2A.get(i).getName(), statusEight.get(1).get(i).getName());
		}
		
		for (int i = 0; i < 3; i++) {
			assertEquals(status2S.get(i).getName(), statusTwo.get(2).get(i).getName());
		}
	}
}
