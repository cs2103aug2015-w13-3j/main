package logic;

import static org.junit.Assert.*;
import parser.CommandPackage;
import parser.CommandParser;

import org.junit.Test;
//@@author A0133948W
public class SystemTest {
	LogicClass lc = LogicClass.getInstance();
	
	@Test
	public void test1() {
		//test: add, update, delete
		CommandParser cmp = new CommandParser();
		CommandPackage cmd = cmp.getCommandPackage("add meeting 9-Oct");
		lc.executeCommand(cmd);
		int size = lc.getTaskList().size();
		assertEquals(lc.getTaskList().get(0).getName(), "meeting");
		cmd = cmp.getCommandPackage("update `meeting `name `party");
		lc.executeCommand(cmd);
		assertEquals(lc.getTaskList().get(0).getName(), "party");
		cmd = cmp.getCommandPackage("delete party");
		lc.executeCommand(cmd);
		assertEquals(lc.getTaskList().size(), size - 1);
	}
	
	@Test
	public void test2() {
		//test: clear, undo, redo
		CommandParser cmp = new CommandParser();
		CommandPackage cmd = cmp.getCommandPackage("clear");

		lc.executeCommand(cmd);
		int size = lc.getTaskList().size();
		assertEquals(0, size);
		cmd = cmp.getCommandPackage("add meeting 9-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("undo");
		lc.executeCommand(cmd);
		size = lc.getTaskList().size();
		assertEquals(0, size);
		cmd = cmp.getCommandPackage("redo");
		lc.executeCommand(cmd);
		size = lc.getTaskList().size();
		assertEquals(1, size);
	}
	
	@Test
	public void test3() {
		//test: sort, search
		CommandParser cmp = new CommandParser();
		CommandPackage cmd = cmp.getCommandPackage("add party 8-Oct #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting 9-Oct #2");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("sort name");
		lc.executeCommand(cmd);
		assertEquals("meeting", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("sort time");
		lc.executeCommand(cmd);
		assertEquals("party", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("sort priority");
		lc.executeCommand(cmd);
		assertEquals("meeting", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("search meeting");
		lc.executeCommand(cmd);
		assertEquals("meeting", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("search 8-Oct");
		lc.executeCommand(cmd);
		assertEquals("party", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("search #2");
		lc.executeCommand(cmd);
		assertEquals("meeting", lc.getTaskList().get(0).getName());	
	}

}
