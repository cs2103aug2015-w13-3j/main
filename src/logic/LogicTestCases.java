////@@ A0133949U
//package logic;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import logic.command.AddCommand;
//import parser.CommandPackage;
//
//public class LogicTestCases {
//	public LogicClass lc = LogicClass.getInstance();
//	Manager mgr = Manager.getInstance();
//	
//	@Test
//	public void TestAdd() {
//		
//        CommandPackage commandPackage = new CommandPackage();
//        commandPackage.setCommand("add");
//        commandPackage.setPhrase("meeting");
//        Task t = new Task("meeting");
//        CommandType ct = CommandType.valueOf("ADD");
//		Command cmd = new AddCommand(ct,mgr,commandPackage);
//		cmd.execute();
//        
////		assertEquals(t.getName(),
////				LogicClass.getInstance().addTask(commandPackage).getName());
//
//	}
//	
//	@Test
//	public void deleteTest() {
//        CommandPackage commandPackage = new CommandPackage();
//        commandPackage.setCommand("delete");
//        commandPackage.setPhrase("meeting");
//        Task t = new Task("meeting");
//        
//		
////		assertEquals(t.getName(),
////				LogicClass.getInstance().delete( commandPackage.getPhrase()));
//
//	}
//}