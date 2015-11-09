//@@author A0133915H
package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UndoRedoOpTest {

	static ArrayList<Task> initial = new ArrayList<Task>();
	static ArrayList<Task> initialArchived = new ArrayList<Task>();

	/***************************************************************
	 * Scripted testing & white-box testing 
	 * This test is only for the undo/redo function 
	 ***************************************************************/

	@BeforeClass
	public static void oneTimeSetUp() {
		initial.clear();
		initialArchived.clear();
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
	}

	@After
	public void tearDown() {
		initial.clear();
		initialArchived.clear();
	}

	/**
	 * Test for undo function
	 */
	@Test
	public void undoTest() {

		UndoRedoOp op = UndoRedoOp.getInstance();

		op.addStateToUndo(initial, initialArchived);

		ArrayList<Task> status1 = new ArrayList<Task>();
		ArrayList<Task> status1A = new ArrayList<Task>();
		ArrayList<Task> status2 = new ArrayList<Task>();
		ArrayList<Task> status2A = new ArrayList<Task>();

		status1.add(new Task("Meeting"));
		status1.add(new Task("SEP"));
		status1.add(new Task("Movie with john"));
		status1.add(new Task("Dinner with Tom"));

		status1A.add(new Task("Meeting tmr"));
		status1A.add(new Task("SEP done"));
		status1A.add(new Task("Movie with john yes"));
		status1A.add(new Task("Dinner with Tom"));

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

		ArrayList<ArrayList<Task>> statusOne = new ArrayList<ArrayList<Task>>(op.addStateToUndo(status1, status1A));
		assertEquals(2, statusOne.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), (statusOne.get(0).get(i)).getName());
			assertEquals(status1A.get(i).getName(), (statusOne.get(1).get(i)).getName());
		}

		ArrayList<ArrayList<Task>> statusTwo = new ArrayList<ArrayList<Task>>(op.addStateToUndo(status2, status2A));
		assertEquals(2, statusTwo.size());

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), (statusTwo.get(0).get(i)).getName());
			assertEquals(status2A.get(i).getName(), (statusTwo.get(1).get(i)).getName());
		}

		ArrayList<ArrayList<Task>> statusThree = op.undo();
		assertEquals(2, statusThree.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusThree.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusThree.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusFour = op.undo();
		assertEquals(2, statusFour.size());

		for (int i = 0; i < 3; i++) {
			assertEquals(initial.get(i).getName(), statusFour.get(0).get(i).getName());
			assertEquals(initialArchived.get(i).getName(), statusFour.get(1).get(i).getName());
		}

	}

	/**
	 * Test for redo function
	 */
	@Test
	public void redoTest() {
		UndoRedoOp op = UndoRedoOp.getInstance();

		op.addStateToUndo(initial, initialArchived);

		ArrayList<Task> status1 = new ArrayList<Task>();
		ArrayList<Task> status1A = new ArrayList<Task>();
		ArrayList<Task> status2 = new ArrayList<Task>();
		ArrayList<Task> status2A = new ArrayList<Task>();

		status1.add(new Task("Meeting"));
		status1.add(new Task("SEP"));
		status1.add(new Task("Movie with john"));
		status1.add(new Task("Dinner with Tom"));

		status1A.add(new Task("Meeting tmr"));
		status1A.add(new Task("SEP done"));
		status1A.add(new Task("Movie with john yes"));
		status1A.add(new Task("Dinner with Tom haha"));

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

		ArrayList<ArrayList<Task>> statusOne = op.addStateToUndo(status1, status1A);

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusOne.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusOne.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusTwo = op.addStateToUndo(status2, status2A);

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), statusTwo.get(0).get(i).getName());
			assertEquals(status2A.get(i).getName(), statusTwo.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusThree = op.undo();
		assertEquals(2, statusThree.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusThree.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusThree.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusFour = op.redo();
		assertEquals(2, statusFour.size());

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), statusFour.get(0).get(i).getName());
			assertEquals(status2A.get(i).getName(), statusFour.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusFive = op.undo();
		assertEquals(2, statusFive.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusFive.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusFive.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusSix = op.undo();
		assertEquals(2, statusSix.size());

		for (int i = 0; i < 3; i++) {
			assertEquals(initial.get(i).getName(), statusSix.get(0).get(i).getName());
			assertEquals(initialArchived.get(i).getName(), statusSix.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusSeven = op.redo();
		assertEquals(2, statusSeven.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusSeven.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusSeven.get(1).get(i).getName());
		}

		ArrayList<ArrayList<Task>> statusEight = op.redo();
		assertEquals(2, statusEight.size());

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), statusEight.get(0).get(i).getName());
			assertEquals(status2A.get(i).getName(), statusEight.get(1).get(i).getName());
		}
	}
}
