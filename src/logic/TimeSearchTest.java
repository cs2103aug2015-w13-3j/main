package logic;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

//@@author A0133948W
public class TimeSearchTest {

	@Test
	public void test() {
		Task a = new Task("A");
		a.setStartTime(new DateTime(2014, 1, 1, 1, 1));
		a.setEndTime(new DateTime(2014, 1, 2, 1, 1));
		Task b = new Task("B");
		b.setStartTime(new DateTime(2014, 2, 1, 1, 1));
		TimeLine.getInstance().addToTL(a);
		TimeLine.getInstance().addToTL(b);
		assertEquals(Searcher.getInstance().searchDate(new DateTime(2014,1,1,1,1), new DateTime(2014,1,1,2,1)).get(0).getName(), "A");
	}

}
