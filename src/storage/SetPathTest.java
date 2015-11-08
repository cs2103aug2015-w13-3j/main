package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import logic.Task;
//@@author A0133948W
public class SetPathTest {

	@Test
	public void test() {
		
		ArrayList<Task> a = new ArrayList<Task>();
		ArrayList<Task> c = new ArrayList<Task>();
		Task t = new Task("test");
		a.add(t);
		Storage.getInstance().write(a,c);
		Storage.getInstance().setPath("Task_Bomber.txt");
		ArrayList<Task> b = Storage.getInstance().read().get(0);
		System.out.println(a);
		System.out.println(b);
		assertEquals(b.get(0).getName(), a.get(0).getName());
		
	}

}
