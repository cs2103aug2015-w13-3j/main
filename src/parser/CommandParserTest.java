package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParserTest {

	@Test
	public void testGetCommandPackage() {
		CommandParser cmdP = new CommandParser();
		cmdP.getCommandPackage("add meeting 10-oct");
		fail("Not yet implemented");
	}

}
