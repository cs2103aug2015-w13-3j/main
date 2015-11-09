//@@author A0133915H
package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UndoRedoOpTest {

	static ArrayList<Task> expected = new ArrayList<Task>();
	static ArrayList<Task> initial = new ArrayList<Task>();
	static ArrayList<Task> expectedAchived = new ArrayList<Task>();
	static ArrayList<Task> initialArchived = new ArrayList<Task>();

	// scripted testing & white-box

	@BeforeClass
	public static void oneTimeSetUp() {
		expected.clear();
		initial.clear();
		expectedAchived.clear();
		initialArchived.clear();
	}

	@Before
	public void setUp() {
		initial.add(new Task("Meeting"));
		initial.add(new Task("SEP"));
		initial.add(new Task("Movie with john"));
		// expected.add(new Task("Meeting"));
		// expected.add(new Task("SEP"));
		// expected.add(new Task("Movie with john"));
		initialArchived.add(new Task("Meeting tmr"));
		initialArchived.add(new Task("SEP done"));
		initialArchived.add(new Task("Movie with john yes"));
		// expectedAchived.add(new Task("Meeting tmr"));
		// expectedAchived.add(new Task("SEP done"));
		// expectedAchived.add(new Task("Movie with john yes"));
	}

	@After
	public void tearDown() {
		expected.clear();
		initial.clear();
		expectedAchived.clear();
		initialArchived.clear();
	}

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

		// expected.add(new Task("Dinner with Tom"));
		// expectedAchived.add(new Task("Dinner with Tom"));

		ArrayList<ArrayList<Task>> statusOne = new ArrayList<ArrayList<Task>>(op.addStateToUndo(status1, status1A));
		assertEquals(2, statusOne.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), (statusOne.get(0).get(i)).getName());
			assertEquals(status1A.get(i).getName(), (statusOne.get(1).get(i)).getName());
		}

		// expected.add(new Task("Party"));
		// expectedAchived.add(new Task("Party"));

		ArrayList<ArrayList<Task>> statusTwo = new ArrayList<ArrayList<Task>>(op.addStateToUndo(status2, status2A));
		assertEquals(2, statusTwo.size());

		for (int i = 0; i < 5; i++) {
			assertEquals(status2.get(i).getName(), (statusTwo.get(0).get(i)).getName());
			assertEquals(status2A.get(i).getName(), (statusTwo.get(1).get(i)).getName());
		}

		// expected.remove(4);
		// expectedAchived.remove(4);

		ArrayList<ArrayList<Task>> statusThree = op.undo();
		assertEquals(2, statusThree.size());

		assertEquals(4, status1.size());
		assertEquals(4, status1A.size());
		
		for (int i = 0; i < statusThree.size(); i++) {
			System.out.println("lalalalalalalal" + i);
			System.out.println("I am a buggggggggggg " + statusThree.get(0).get(i).getName());
		}

		assertEquals(4, statusThree.get(0).size());
		assertEquals(4, statusThree.get(1).size());

		for (int i = 0; i < 4; i++) {
			assertEquals(status1.get(i).getName(), statusThree.get(0).get(i).getName());
			assertEquals(status1A.get(i).getName(), statusThree.get(1).get(i).getName());
		}

		expected.remove(3);
		expectedAchived.remove(3);

		ArrayList<ArrayList<Task>> statusFour = op.undo();
		assertEquals(2, statusFour.size());

		for (int i = 0; i < 4; i++) {
			assertEquals(expected.get(i).getName(), statusFour.get(0).get(i).getName());
			assertEquals(expectedAchived.get(i).getName(), statusFour.get(1).get(i).getName());
		}

	}

	// @Test
	// public void redoTest() {
	// UndoRedoOp op = UndoRedoOp.getInstance();
	//
	// ArrayList<Task> status1 = new ArrayList<Task>();
	// status1.add(new Task("Meeting"));
	// status1.add(new Task("SEP"));
	// status1.add(new Task("Movie with john"));
	// status1.add(new Task("Dinner with bf"));
	// expected.add(new Task("Dinner with bf"));
	//
	// for (int i = 0; i < 4; i++) {
	// assertEquals(expected.get(i).getName(),
	// op.addStateToUndo(status1).get(i).getName());
	// }
	//
	// expected.remove(3);
	//
	// for (int i = 0; i < 3; i++) {
	// assertEquals(expected.get(i).getName(), op.undo().get(i).getName());
	// }
	//
	// expected.add(new Task("Dinner with bf"));
	//
	// for (int i = 0; i < 4; i++) {
	// assertEquals(expected.get(i).getName(), op.redo().get(i).getName());
	// }
	// }

}
