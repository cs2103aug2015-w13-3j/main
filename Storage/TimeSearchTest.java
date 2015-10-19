import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;


public class TimeSearchTest {

	@Test
	public void test() {
		Task a = new Task("A");
		a.setStartTime(new DateTime(2014, 1, 1, 1, 1));
		Task b = new Task("B");
		b.setStartTime(new DateTime(2014, 2, 1, 1, 1));
		TimeLine.addToTL(a);
		TimeLine.addToTL(b);
		assertEquals(Searcher.searchDate(new DateTime(2014,1,1,1,1)).get(0).getName(), "A");
	}

}
