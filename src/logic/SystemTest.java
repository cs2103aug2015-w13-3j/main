package logic;

import static org.junit.Assert.*;
import parser.CommandPackage;
import parser.CommandParser;

import org.junit.Test;
//@@author A0133948W
public class SystemTest {

	@Test
	public void test1() {
		//test: add, update, delete
		CommandParser cmp = new CommandParser();
		CommandPackage cmd = cmp.getCommandPackage("add meeting 9-Oct");
		LogicClass lg = LogicClass.getInstance();
		lg.executeCommand(cmd);
		int size = LogicClass.getTaskList().size();
		assertEquals(LogicClass.getTaskList().get(0).getName(), "meeting");
		cmd = cmp.getCommandPackage("update `meeting `name `party");
		lg.executeCommand(cmd);
		assertEquals(LogicClass.getTaskList().get(0).getName(), "party");
		cmd = cmp.getCommandPackage("delete party");
		lg.executeCommand(cmd);
		assertEquals(LogicClass.getTaskList().size(), size - 1);
	}
	
	@Test
	public void test2() {
		//test: clear, undo, redo
		CommandParser cmp = new CommandParser();
		CommandPackage cmd = cmp.getCommandPackage("clear");
		LogicClass lg = LogicClass.getInstance();
		lg.executeCommand(cmd);
		int size = LogicClass.getTaskList().size();
		assertEquals(0, size);
		cmd = cmp.getCommandPackage("add meeting 9-Oct");
		lg.executeCommand(cmd);
		cmd = cmp.getCommandPackage("undo");
		lg.executeCommand(cmd);
		size = LogicClass.getTaskList().size();
		assertEquals(0, size);
		cmd = cmp.getCommandPackage("redo");
		lg.executeCommand(cmd);
		size = LogicClass.getTaskList().size();
		assertEquals(1, size);
	}
	
	@Test
	public void test3() {
		//test: sort, search
		CommandParser cmp = new CommandParser();
		CommandPackage cmd = cmp.getCommandPackage("add party 8-Oct #3");
		LogicClass lg = LogicClass.getInstance();
		lg.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting 9-Oct #2");
		lg.executeCommand(cmd);
		cmd = cmp.getCommandPackage("sort name");
		lg.executeCommand(cmd);
		assertEquals("meeting", LogicClass.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("sort time");
		lg.executeCommand(cmd);
		assertEquals("party", LogicClass.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("sort priority");
		lg.executeCommand(cmd);
		assertEquals("meeting", LogicClass.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("search meeting");
		lg.executeCommand(cmd);
		assertEquals("meeting", LogicClass.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("search 8-Oct");
		lg.executeCommand(cmd);
		assertEquals("party", LogicClass.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("search #2");
		lg.executeCommand(cmd);
		assertEquals("meeting", LogicClass.getTaskList().get(0).getName());	
	}

}
