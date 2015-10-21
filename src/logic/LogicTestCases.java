package logic;


import static org.junit.Assert.*;
import org.junit.Test;

import parser.CommandPackage;

public class LogicTestCases {

	
	@Test
	public void addTest() {
        CommandPackage commandPackage = new CommandPackage();
        commandPackage.setCommand("add");
        commandPackage.setPhrase("meeting");
        Task t = new Task("meeting");
        
		
		assertEquals(t.getName(),
				LogicClass.addTask(commandPackage).getName());

	}
}
