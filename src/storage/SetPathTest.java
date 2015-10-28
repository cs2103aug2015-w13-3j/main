package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import logic.Task;

public class SetPathTest {

	@Test
	public void test() {
		ArrayList<Task> a = Storage.read();
		Storage.setPath("Task_Bomber_.txt");
		assertEquals(Storage.read(), a);
		
	}

}
