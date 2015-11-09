package logic;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

//@@author A0133948W
public class TimeSearchTest {

	@Test
	public void test1() {
		//test: event task 
		Task a = new Task("A");
		a.setStartTime(new DateTime(2014, 1, 1, 1, 1));
		a.setEndTime(new DateTime(2014, 1, 2, 1, 1));
		Task b = new Task("B");
		b.setStartTime(new DateTime(2014, 2, 1, 1, 1));
		b.setEndTime(new DateTime(2014, 2, 2, 1, 1));
		TimeLine.getInstance().clear();
		TimeLine.getInstance().addToTL(a);
		TimeLine.getInstance().addToTL(b);
		assertEquals("A",Searcher.getInstance().searchDate(new DateTime(2014,1,1,1,1), new DateTime(2014,1,1,2,1)).get(0).getName());
	}

	@Test
	public void test2() {
		//test: deadline Task in range
		Task c = new Task("C");
		c.setStartTime(new DateTime(2014, 1, 1, 1, 1));
		c.setEndTime(new DateTime(2014, 1, 2, 1, 1));
		Task d = new Task("D");
		d.setEndTime(new DateTime(2014, 1, 3, 1, 1));
		TimeLine.getInstance().clear();
		TimeLine.getInstance().addToTL(c);
		TimeLine.getInstance().addToTL(d);
		assertEquals("D",Searcher.getInstance().searchDate(new DateTime(2014,1,3,1,1), new DateTime(2014,1,3,2,1)).get(0).getName());
	}
	
	public void test3() {
		//test: deadline Task out of range
		Task c = new Task("C");
		c.setStartTime(new DateTime(2014, 1, 1, 1, 1));
		c.setEndTime(new DateTime(2014, 1, 2, 1, 1));
		Task d = new Task("D");
		d.setEndTime(new DateTime(2014, 2, 3, 1, 1));
		TimeLine.getInstance().clear();
		TimeLine.getInstance().addToTL(c);
		TimeLine.getInstance().addToTL(d);
		assertEquals(0 ,Searcher.getInstance().searchDate(new DateTime(2014,1,3,1,1), new DateTime(2014,1,3,1,2)).size());
	}

	@Test
	public void test4() {
		//test: task with only start time which is before the start time being search.
		Task e = new Task("E");
		e.setStartTime(new DateTime(2014, 1, 3, 1, 1));
		e.setEndTime(new DateTime(2014, 1, 4, 1, 1));
		Task f = new Task("F");
		f.setStartTime(new DateTime(2014, 1, 1, 1, 1));
		TimeLine.getInstance().clear();
		TimeLine.getInstance().addToTL(e);
		TimeLine.getInstance().addToTL(f);
		assertEquals("F",Searcher.getInstance().searchDate(new DateTime(2014,2,1,1,1), new DateTime(2014,2,3,1,1)).get(0).getName());
	}

	@Test
	public void test5() {
		//test: task with only start time which is after the start time being search.
		Task e = new Task("E");
		e.setStartTime(new DateTime(2014, 1, 3, 1, 1));
		e.setEndTime(new DateTime(2014, 1, 4, 1, 1));
		Task f = new Task("F");
		f.setStartTime(new DateTime(2014, 2, 2, 1, 1));
		TimeLine.getInstance().clear();
		TimeLine.getInstance().addToTL(e);
		TimeLine.getInstance().addToTL(f);
		assertEquals("F",Searcher.getInstance().searchDate(new DateTime(2014,2,1,1,1), new DateTime(2014,2,3,1,1)).get(0).getName());
	}
	
	@Test
	public void test6() {
		//test: task with only start time which is after the end time being search.
		Task e = new Task("E");
		e.setStartTime(new DateTime(2014, 1, 3, 1, 1));
		e.setEndTime(new DateTime(2014, 1, 4, 1, 1));
		Task f = new Task("F");
		f.setStartTime(new DateTime(2014, 3, 2, 1, 1));
		TimeLine.getInstance().clear();
		TimeLine.getInstance().addToTL(e);
		TimeLine.getInstance().addToTL(f);
		assertEquals(0 ,Searcher.getInstance().searchDate(new DateTime(2014,2,1,1,1), new DateTime(2014,2,3,1,1)).size());
	}
	
	@Test
	public void test7() {
		//test: floating task
		Task g = new Task("G");
		g.setStartTime(new DateTime(2014, 1, 3, 1, 1));
		g.setEndTime(new DateTime(2014, 1, 4, 1, 1));
		Task h = new Task("H");
		TimeLine.getInstance().clear();
		TimeLine.getInstance().addToTL(g);
		TimeLine.getInstance().addToTL(h);
		assertEquals(0 ,Searcher.getInstance().searchDate(new DateTime(2014,2,1,1,1), new DateTime(2014,2,3,1,1)).size());
	}
}
