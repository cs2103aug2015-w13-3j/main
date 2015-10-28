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
				LogicClass.getInstance(null).addTask(commandPackage).getName());

	}
	
	@Test
	public void deleteTest() {
        CommandPackage commandPackage = new CommandPackage();
        commandPackage.setCommand("delete");
        commandPackage.setPhrase("meeting");
        Task t = new Task("meeting");
        
		
		assertEquals(t.getName(),
				LogicClass.getInstance(null).delete( commandPackage.getPhrase()));

	}
}
