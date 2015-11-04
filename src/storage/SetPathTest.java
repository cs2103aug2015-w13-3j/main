package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import logic.Task;
//@@author A0133948W
public class SetPathTest {

	@Test
	public void test() {
		ArrayList<Task> a = Storage.getInstance().read();
		Storage.getInstance().setPath("Task_Bomber_.txt");
		assertEquals(Storage.getInstance().read(), a);
		
	}

}
