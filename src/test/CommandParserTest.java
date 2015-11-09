package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;
import parser.CommandParser;
import parser.CommandPackage;

// @author A0122061B

public class CommandParserTest {

    @Test
    public void testAddGetCommandPackage1() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add meeting 7pm");
	boolean test1 = cPK1.getCommand().equals("create");

	assertTrue(test1);
    }

    @Test
    public void testAddGetCommandPackage2() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add meeting 10-oct #2");
	boolean test1 = cPK1.getCommand().equals("create");

	assertTrue(test1);
    }

    @Test
    public void testAddGetCommandPackage3() {// equivalance partitioning for
					     // command
	// name
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add meeting 10-oct 11-oct 7pm");
	boolean test1 = cPK1.getCommand().equals("create");

	assertTrue(test1);
    }

    @Test
    public void testAddGetCommandPackage4() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add name 10-oct 10am 7am #1");
	DateTime date1 = new DateTime(2016, 10, 10, 7, 0);
	DateTime date2 = new DateTime(2016, 10, 10, 10, 0);
	assertTrue(cPK1.startingTime().isEqual(date1));
	assertTrue(cPK1.endingTime().isEqual(date2));
	assertTrue(cPK1.getPriority().equals("1"));
    }

    @Test
    public void testAddGetCommandPackage5() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add meeting from 10-oct to 7pm end");
	boolean test1 = cPK1.getCommand().equals("create");

	assertTrue(test1);
    }

    @Test
    public void testAddGetCommandPackage6() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add name 10am 7am #1");
	DateTime date0 = new DateTime();
	DateTime date1 = new DateTime(date0.getYear(), date0.getMonthOfYear(), date0.getDayOfMonth(), 7, 0);
	DateTime date2 =
		new DateTime(date0.getDayOfYear(), date0.getMonthOfYear(), date0.getDayOfMonth(), 10, 0);
	assertTrue(cPK1.startingTime().getHourOfDay() == (date1.getHourOfDay()));
	assertTrue(cPK1.endingTime().getHourOfDay() == (date2.getHourOfDay()));
	assertTrue(cPK1.getPriority().equals("1"));
    }
 
    @Test
    public void testAddGetCommandPackage7() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("plus name 10-oct 7am 12-oct 10am #1");
	DateTime date1 = new DateTime(2016, 10, 10, 7, 0);
	DateTime date2 = new DateTime(2016, 10, 10, 10, 0);
	assertTrue(cPK1.startingTime().getHourOfDay() == (date1.getHourOfDay()));
	assertTrue(cPK1.endingTime().getHourOfDay() == (date2.getHourOfDay()));
	assertTrue(cPK1.getPriority().equals("1"));
    }

    @Test
    public void testAddGetCommandPackage8() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("Add");
	// boolean test1 = cPK1.getCommand().equals("create");
	boolean result = (cPK1 == null);
	assertTrue(result);
    }

    @Test
    public void testAddGetCommandPackage9() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add meeting start 10-oct");
	CommandPackage cPK2 = cmdP.getCommandPackage("add meeting end 10-oct");
	boolean test1 = cPK1.getCommand().equals("create");

	assertTrue(test1);
    }

    @Test
    public void testUpdateGetCommandPackage1() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("update `meeting `et `10-oct 7pm");
	assertTrue(cPK1.getUpdateSequence().size() == 4);
    }

    @Test
    public void testUpdateGetCommandPackage2() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("update `meeting `starttime `10-oct 7pm");
	assertTrue(cPK1.getUpdateSequence().size() == 4);
    }

    @Test
    public void testUpdateGetCommandPackage3() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("update `meeting `starttime `7pm");
	assertTrue(cPK1.getUpdateSequence().size() == 4);
    }

    @Test
    public void testUpdateGetCommandPackage4() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("update `meeting `et `7pm");
	assertTrue(cPK1.getUpdateSequence().size() == 4);
    }

    @Test
    public void testUpdateGetCommandPackage5() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("update `meeting `st `delete");
	System.out.println(cPK1.getUpdateSequence().size());
	assertTrue(cPK1.getUpdateSequence().size() == 6);
    }

    @Test
    public void testUpdateGetCommandPackage6() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("update `meeting `et `delete");
	System.out.println(cPK1.getUpdateSequence().size());
	assertTrue(cPK1.getUpdateSequence().size() == 6);
    }

    @Test
    public void testGetCommandPackage1() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("search name 10-oct");
	DateTime date1 = new DateTime(2016, 10, 10, 0, 0);
	DateTime date2 = date1.plusDays(1).plusMillis(-1);
	assertTrue(cPK1.startingTime().isEqual(date1));
	assertTrue(cPK1.endingTime().isEqual(date2));
	assertTrue(cPK1.getPriority().equals("1"));
    }

    @Test
    public void testGetCommandPackage6() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("Invalid Input");
	// boolean test1 = cPK1.getCommand().equals("create");
	boolean result = (cPK1 == null);
	assertTrue(result);
    }

    @Test
    public void testGetCommandPackage7() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("Clear");
	// boolean test1 = cPK1.getCommand().equals("create");
	boolean test1 = cPK1.getCommand().equals("clear");
	assertTrue(test1);
    }

    @Test
    public void testArrToArrayList() { // boundary value analysis
	CommandParser cmdP = CommandParser.getInstance();
	String[] abc = { "a", "b", "c" };
	ArrayList<String> test = cmdP.arrToArrayList(abc);
	boolean testEqual = true;
	for (int i = 0; i < abc.length; i++) {
	    if (!abc[i].equals(test.get(i))) {
		testEqual = false;
		break;
	    }
	}
	assertTrue(testEqual);
    }

    @Test
    public void testCallAction() {
	CommandParser cmdP = CommandParser.getInstance();
	String result = cmdP.callAction("remove");
	assertTrue(result.equals("delete"));
    }

}
