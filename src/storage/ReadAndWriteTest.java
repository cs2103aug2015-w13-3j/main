package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import logic.Task;
//@@author A0133948W
public class ReadAndWriteTest {

	@Test
	public void test() {
		Storage storage = Storage.getInstance();
		ArrayList<Task> a = new ArrayList<Task>();
		ArrayList<Task> c = new ArrayList<Task>();
		for(int i = 1; i < 10; i++){
			Task t1 = new Task("test" + i);
			t1.setStartTime(new DateTime(2016,i,1,1,1));
			t1.setEndTime(new DateTime(2016,i+1,1,1,1));
			t1.setPriority("1");
			a.add(t1);
		}
		storage.read();
		storage.write(a,c);
		ArrayList<Task> b = storage.read().get(0);
		System.out.println(a);
		System.out.println(b);
		for(int i = 0; i < 9; i++){
			assertEquals(a.get(i).getName(), b.get(i).getName());
			assertEquals(a.get(i).getStartTime(), b.get(i).getStartTime());
			assertEquals(a.get(i).getEndTime(), b.get(i).getEndTime());
			assertEquals(a.get(i).getPriority(), b.get(i).getPriority());
		}
	}

}
