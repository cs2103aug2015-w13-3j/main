package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;

import parser.CommandPackage;
import parser.CommandParser;

import org.junit.Test;

import logic.LogicClass;
import logic.command.InvalidCommandException;
//@@author A0133948W
public class SystemTest {
	LogicClass lc = LogicClass.getInstance();
	
	@Test
	public void test1() throws InvalidCommandException {
		//test: clear, add, update, delete
		CommandParser cmp = CommandParser.getInstance();
		CommandPackage cmd = cmp.getCommandPackage("clear");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting1 9-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting2 1am");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting3 9pm 10pm");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting4 9-Dec 10-Dec");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting5 start 11-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting6");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting7 tomorrow");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting8 Sunday");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting9 #1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting10 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("create meeting11 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("`a meeting12 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting13 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting14 30/11/2015 #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting15 30.11.2015 #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add \"meeting16 tomorrow\"");
		lc.executeCommand(cmd);
		assertEquals(16,lc.getTaskList().size());
		cmd = cmp.getCommandPackage("update `meeting1 `name `party1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("update `meeting2 `# `2");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("update `meeting3 `priority `3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("update `meeting4 `starttime `9pm");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("update `meeting5 `endtime `10pm");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("edit `meeting6 `name `party6");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("`e `meeting7 `starttime `9.10.2016");
		lc.executeCommand(cmd);
		assertEquals(16, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("delete party1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("delete 1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("delete meeting2");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("bomb meeting3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("remove meeting4");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("`b meeting5");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("`rmv party6");
		lc.executeCommand(cmd);
		assertEquals(9, lc.getTaskList().size());
	}
	
	@Test
	public void test2() throws InvalidCommandException {
		//test: undo, redo
		CommandParser cmp = CommandParser.getInstance();
		CommandPackage cmd = cmp.getCommandPackage("clear");
		lc.executeCommand(cmd);
		int size = lc.getTaskList().size();
		assertEquals(0, size);
		cmd = cmp.getCommandPackage("add meeting1 9-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting2 1am");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting3 9pm 10pm");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting4 9-Dec 10-Dec #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting5 start 11-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("undo");
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		size = lc.getTaskList().size();
		assertEquals(0, size);
		cmd = cmp.getCommandPackage("redo");
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		lc.executeCommand(cmd);
		size = lc.getTaskList().size();
		assertEquals(5, size);
		cmd = cmp.getCommandPackage("delete 1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("update `meeting4 `name `party4");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("undo");
		lc.executeCommand(cmd);
		assertEquals("meeting4", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("update `meeting4 `# `1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("undo");
		lc.executeCommand(cmd);
		assertEquals(new Integer(3), lc.getTaskList().get(0).getPriority());
		cmd = cmp.getCommandPackage("update `meeting4 `starttime `8-Dec");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("undo");
		lc.executeCommand(cmd);
		assertEquals(0, lc.getTaskList().get(0).getStartTime().compareTo(new DateTime(2015,12,9,0,0)));
	}
	
	@Test
	public void test3() throws InvalidCommandException {
		//test: sort, search
		CommandParser cmp = CommandParser.getInstance();
		CommandPackage cmd = cmp.getCommandPackage("clear");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting1 9-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting2 1am");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting3 9pm 10pm");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting4 9-Dec 10-Dec");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting5 start 11-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting6");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting7 tomorrow");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting8 Sunday");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting9 #1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting10 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("create meeting11 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("`a meeting12 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting13 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting14 30/11/2015 #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting15 30.11.2015 #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add \"meeting16 tomorrow\"");
		lc.executeCommand(cmd);
		assertEquals(16,lc.getTaskList().size());
		cmd = cmp.getCommandPackage("sort name");
		lc.executeCommand(cmd);
		assertEquals("meeting1", lc.getTaskList().get(1).getName());
		cmd = cmp.getCommandPackage("sort time");
		lc.executeCommand(cmd);
		assertEquals("meeting3", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("sort priority");
		lc.executeCommand(cmd);
		assertEquals("meeting9", lc.getTaskList().get(0).getName());
		cmd = cmp.getCommandPackage("search meeting");
		lc.executeCommand(cmd);
		assertEquals(16, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("find meeting11");
		lc.executeCommand(cmd);
		assertEquals(1, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("`s meeting2");
		lc.executeCommand(cmd);
		assertEquals(1, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("search #2");
		lc.executeCommand(cmd);
		assertEquals(0, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("search #3");
		lc.executeCommand(cmd);
		assertEquals(6, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("search 9-Dec");
		lc.executeCommand(cmd);
		assertEquals(1, lc.getTaskList().size());
	}

	@Test
	public void test4() throws InvalidCommandException {
		//test: mark, search done
		CommandParser cmp = CommandParser.getInstance();
		CommandPackage cmd = cmp.getCommandPackage("clear");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting1 9-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting2 1am");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting3 9pm 10pm");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting4 9-Dec 10-Dec");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting5 start 11-Oct");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting6");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting7 tomorrow");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting8 Sunday");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting9 #1");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting10 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("create meeting11 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("`a meeting12 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting13 10pm #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting14 30/11/2015 #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add meeting15 30.11.2015 #3");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("add \"meeting16 tomorrow\"");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("mark meeting1");
		lc.executeCommand(cmd);
		assertEquals(15, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("search done");
		lc.executeCommand(cmd);
		lc.setIsSearchOp(false);
		assertEquals(15, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("search done");
		lc.executeCommand(cmd);
		lc.setIsSearchOp(false);
		assertEquals(15, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("search done");
		lc.executeCommand(cmd);
		lc.setIsSearchOp(false);
		cmd = cmp.getCommandPackage("complete meeting2");
		lc.executeCommand(cmd);
		cmd = cmp.getCommandPackage("mark meeting3");
		lc.executeCommand(cmd);
		assertEquals(13, lc.getTaskList().size());
		cmd = cmp.getCommandPackage("search done");
		lc.executeCommand(cmd);
	}
	
	@Test
	public void test5(){
		//test: invalid command
		CommandParser cmp = CommandParser.getInstance();
		CommandPackage cmd = cmp.getCommandPackage("clear");
		try {
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("add meeting #4");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("add meeting #-1");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("add meeting1 9-Oct");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("delete party");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("delete 2");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("update `party `name `meeting");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("update `2 `name `meeting");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("update `meeting1 `starttime `what");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("update `meeting1 `# `4");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("mark party");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("search 9-Nov 9pm");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("search #4");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		try {
			cmd = cmp.getCommandPackage("sort abc");
			lc.executeCommand(cmd);
		} catch (InvalidCommandException e) {
		}
		
		
	}
}
