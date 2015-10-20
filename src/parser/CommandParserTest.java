package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParserTest {

	@Test
	public void testGetCommandPackage1() {//equivalance partitioning
		CommandParser cmdP = new CommandParser();
		CommandPackage cPK1 = cmdP.getCommandPackage("add meeting 10-oct");
		CommandPackage cPK2 = cmdP.getCommandPackage("add homework 10-oct");
		boolean test = cPK1.getCommand().equals(cPK2.getCommand());
		
		assertTrue(test);
	}
	
	@Test
	public void testGetCommandPackage2() { //boundary value analysis
		CommandParser cmdP = new CommandParser();
		CommandPackage cPK = cmdP.getCommandPackage("");
		assertEquals(cPK.getPhrase(), "");
	}

	public void testGetCommandPackage3() {
		CommandParser cmdP = new CommandParser();
		System.out.println(cmdP.getCommandPackage("teemo hello 9pm add").endingTime());
		fail("Not yet implemented");
	}

	public void testGetCommandPackage4() {
		CommandParser cmdP = new CommandParser();
		CommandPackage cPK = cmdP.getCommandPackage("delete 1");
		fail("Not yet implemented");
	}

	public void testGetCommandPackage5() {
		CommandParser cmdP = new CommandParser();
		CommandPackage cPK = cmdP.getCommandPackage("update ~eat bread ~task name ~eat chicken");
		fail("Not yet implemented");
	}

	public void testGetCommandPackage6() {
		CommandParser cmdP = new CommandParser();
		CommandPackage cPK = cmdP.getCommandPackage("teemo loves chicken #1 search");
		fail("Not yet implemented");
	}

	public void testGetCommandPackage7() {
		CommandParser cmdP = new CommandParser();
		CommandPackage cPK = cmdP.getCommandPackage("");
		fail("Not yet implemented");
	}


}
