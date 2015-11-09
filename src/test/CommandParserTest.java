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
    public void testGetCommandPackage() {// equivalance partitioning for command
					 // name
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("add meeting 10-oct 11-oct");
	boolean test1 = cPK1.getCommand().equals("create");

	assertTrue(test1);
    }

    @Test
    public void testGetCommandPackage2() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("update `meeting `starttime `10-oct");
	assertTrue(cPK1.getUpdateSequence().size() == 4);
    }

    @Test
    public void testGetCommandPackage3() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK1 = cmdP.getCommandPackage("search name 10-oct #1");
	DateTime date1 = new DateTime(2016, 10, 10, 0, 0);
	DateTime date2 = date1.plusDays(1).plusMillis(-1);
	assertTrue(cPK1.startingTime().isEqual(date1));
	assertTrue(cPK1.endingTime().isEqual(date2));
	assertTrue(cPK1.getPriority().equals("1"));
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

    public void testGetCommandPackage4() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK = cmdP.getCommandPackage("delete 1");
	fail("Not yet implemented");
    }

    public void testGetCommandPackage5() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK = cmdP.getCommandPackage("update ~eat bread ~task name ~eat chicken");
	fail("Not yet implemented");
    }

    public void testGetCommandPackage6() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK = cmdP.getCommandPackage("teemo loves chicken #1 search");
	fail("Not yet implemented");
    }

    public void testGetCommandPackage7() {
	CommandParser cmdP = CommandParser.getInstance();
	CommandPackage cPK = cmdP.getCommandPackage("");
	fail("Not yet implemented");
    }

}
