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

	
	//scripted testing & white-box
	
	@BeforeClass
	public static void oneTimeSetUp() {
		

	}

	@Before
	public void setUp() {
		initial.add(new Task("Meeting"));
		initial.add(new Task("SEP"));
		initial.add(new Task("Movie with john"));
		expected.add(new Task("Meeting"));
		expected.add(new Task("SEP"));
		expected.add(new Task("Movie with john"));
	}

	@After
	public void tearDown() {
		// expected.clear();
		// assertEquals("all content deleted from a.txt",
		// TextBuddy.executeCommand("clear"));
		// assertEquals(0, TextBuddy.getLineCount());
	}

	@Test
	public void undoTest() {

		UndoRedoOp op = new UndoRedoOp(initial);

		ArrayList<Task> status1 = new ArrayList<Task>();
		status1.add(new Task("Meeting"));
		status1.add(new Task("SEP"));
		status1.add(new Task("Movie with john"));
		status1.add(new Task("Dinner with bf"));
		expected.add(new Task("Dinner with bf"));

		for(int i = 0; i < 4; i++){
			assertEquals(expected.get(i).getName(), op.addStateToUndo(status1).get(i).getName());
		}

		expected.remove(3);
		
		for(int i = 0; i < 3; i++){
			assertEquals(expected.get(i).getName(), op.undo().get(i).getName());
		}
		
	}
	
	@Test
	public void redoTest() {
		UndoRedoOp op = new UndoRedoOp(initial);

		ArrayList<Task> status1 = new ArrayList<Task>();
		status1.add(new Task("Meeting"));
		status1.add(new Task("SEP"));
		status1.add(new Task("Movie with john"));
		status1.add(new Task("Dinner with bf"));
		expected.add(new Task("Dinner with bf"));

		for(int i = 0; i < 4; i++){
			assertEquals(expected.get(i).getName(), op.addStateToUndo(status1).get(i).getName());
		}

		expected.remove(3);
		
		for(int i = 0; i < 3; i++){
			assertEquals(expected.get(i).getName(), op.undo().get(i).getName());
		}
		
		expected.add(new Task("Dinner with bf"));
		
		for(int i = 0; i < 4; i++){
			assertEquals(expected.get(i).getName(), op.redo().get(i).getName());
		}
	}

}
