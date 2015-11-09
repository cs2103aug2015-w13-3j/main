//@@author A0133915H
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import logic.LogicClass;
import logic.Task;
import logic.command.InvalidCommandException;
import parser.CommandPackage;
import parser.CommandParser;

public class LogicClassTest {

	private static LogicClass logicClass;
	private static CommandParser parser;
	private static ArrayList<Task> taskList;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		logicClass = LogicClass.getInstance();
		parser = CommandParser.getInstance();
		taskList = new ArrayList<Task>(logicClass.getTaskList());
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void addTest() {
		CommandPackage cmdPackage = parser.getCommandPackage("add meeting");
		try {
			logicClass.executeCommand(cmdPackage);
		} catch (InvalidCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		taskList.add(new Task("meeting"));
		
		for(int i = 0; i < taskList.size(); i++){
			assertEquals(taskList.get(i).getName(), logicClass.getTaskList().get(i).getName());
		}
	}

}
