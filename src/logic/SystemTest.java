package logic;

import static org.junit.Assert.*;
import parser.ActionLibrary;
import parser.CommandPackage;
import parser.CommandParser;
import parser.DatePackage;
import parser.TimeParser;

import org.junit.Test;
//@@author A0133948W
public class SystemTest {

	@Test
	public void test() {
		CommandParser cmp = new CommandParser();
		CommandPackage cmd = cmp.getCommandPackage("add meeting 9-Oct");
		LogicClass lg = LogicClass.getInstance(null);
		lg.executeCommand(cmd);
		int size = LogicClass.getTaskList().size();
		assertEquals(LogicClass.getTaskList().get(0).getName(), "meeting");
		cmd = cmp.getCommandPackage("update `meeting `name `party");
		lg.executeCommand(cmd);
		assertEquals(LogicClass.getTaskList().get(0).getName(), "party");
		cmd = cmp.getCommandPackage("delete party");
		lg.executeCommand(cmd);
		assertEquals(LogicClass.getTaskList().size(), size - 1);
		/*assertEquals(LogicClass.getInstance(null).delete("meeting").getName(), "meeting");
		cmd = cmp.getCommandPackage("")*/
	}

}
