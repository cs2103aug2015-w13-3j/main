//@@author A0133949U
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Task;
import logic.UndoRedoOp;
import logic.command.AddCommand;
import logic.command.ClearCommand;
import logic.command.DeleteCommand;
import logic.command.InvalidCommandException;
import logic.command.MarkCommand;
import logic.command.SearchCommand;
import logic.command.SortCommand;
import logic.command.UndoCommand;
import logic.command.UpdateCommand;
import parser.CommandPackage;
import parser.CommandParser;


public class CommandTest {
	
	CommandParser cmdp = CommandParser.getInstance();
	
	@Test
	public void TestNormalAddCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Task Added: meeting";
		
		CommandPackage cp = cmdp.getCommandPackage("add meeting 10-oct");
		
        CommandType ct = CommandType.valueOf("CREATE");
		Command cmd = new AddCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			e.printStackTrace();
		}
		
        assertTrue(msg.equals(CorrectMsg));
	}
	
	@Test
	public void TestInvalidAddCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Invalid priority.Priority is valid from 1 to 3";
		
		CommandPackage cp = cmdp.getCommandPackage("add meeting 10-oct #5");
		
        CommandType ct = CommandType.valueOf("CREATE");
		Command cmd = new AddCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
	}
	
	@Test
	public void TestClearCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="All tasks cleared";
		
		CommandPackage cp = cmdp.getCommandPackage("clear");
		
        CommandType ct = CommandType.valueOf("CLEAR");
		Command cmd = new ClearCommand(ct,mgr);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
	}
	
	@Test
	public void TestNotInSearchStatusDeleteCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Task not found";
		
        CommandPackage cp = cmdp.getCommandPackage("delete party");		
        CommandType ct = CommandType.valueOf("DELETE");
		Command cmd = new DeleteCommand(ct,mgr,cp.getPhrase(),false);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
		
		
		//correct delete
		CorrectMsg="Task deleted: party";
		Task task = new Task("party");
		mgr.addToTaskList(task);
		
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
	}
	
	@Test
	public void TestSearchCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Search completed. To go back, press Enter";
		
		Task task = new Task("party");
		mgr.addToTaskList(task);
		
		
        CommandPackage cp = cmdp.getCommandPackage("search party");		
        CommandType ct = CommandType.valueOf("SEARCH");
		Command cmd = new SearchCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
			//System.out.println("heree"+mgr.getSearchList().toString());
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
        
        //exception testing
        CorrectMsg="Invalid priority.Priority is valid from 1 to 3";
        cp = cmdp.getCommandPackage("search #4");	
        cmd = new SearchCommand(ct,mgr,cp);
        try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
		
	}
	
	@Test
	public void TestInSearchStatusDeleteCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Task deleted: party";
		
		Task task = new Task("party");
		mgr.addToTaskList(task);
		mgr.addToSearchList(task);
		
        CommandPackage cp = cmdp.getCommandPackage("delete party");		
        CommandType ct = CommandType.valueOf("DELETE");
		Command cmd = new DeleteCommand(ct,mgr,cp.getPhrase(),true);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
        
        // exception testing
        CorrectMsg="Task not found in searched list. To return to full list,press Enter";
        task = new Task("homework");
        cp = cmdp.getCommandPackage("delete homework");	
		cmd = new DeleteCommand(ct,mgr,cp.getPhrase(),true);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		assertTrue(msg.equals(CorrectMsg));
		
	}
	
	@Test
	public void TestMarkCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Task done: party";
		
		Task task = new Task("party");
		mgr.addToTaskList(task);
		
        CommandPackage cp = cmdp.getCommandPackage("mark party");		
        CommandType ct = CommandType.valueOf("MARK");
		Command cmd = new MarkCommand(ct,mgr,cp.getPhrase());
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
		
		
		//exception testing
		CorrectMsg="Task not found";
		try {
			msg= cmd.execute();
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
        assertTrue(msg.equals(CorrectMsg));
	}
	
	@Test
	public void TestUndoCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Undo previous operation";
		UndoRedoOp undoRedo = UndoRedoOp.getInstance();
		
		Task t= new Task("party");
		Task tDone= new Task("done");
		
		ArrayList<Task> recentState= new ArrayList<Task>();
		ArrayList<Task> archivedList= new ArrayList<Task>();
		ArrayList<Task> searchList= new ArrayList<Task>();
		ArrayList<ArrayList<Task>> current= new ArrayList<ArrayList<Task>>();
		recentState.add(t);
		recentState.add(tDone);
		current= undoRedo.addStateToUndo(recentState, archivedList,searchList);
		System.out.println(current.get(0).toString());
		
        CommandPackage cp = cmdp.getCommandPackage("Undo");		
        CommandType ct = CommandType.valueOf("UNDO");
		Command cmd = new UndoCommand(ct,mgr);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		
        assertTrue(msg.equals(CorrectMsg));
        
	}
	
	@Test
	public void TestSortCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="Sorted by name";
		
		CommandPackage cp = cmdp.getCommandPackage("add party 9pm #3");		
        CommandType ct = CommandType.valueOf("CREATE");
        Command cmd = new AddCommand(ct,mgr,cp);
		try {
			cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			e.printStackTrace();
		}
		
		cp = cmdp.getCommandPackage("add go home 11pm #1");		
        ct = CommandType.valueOf("CREATE");
        cmd = new AddCommand(ct,mgr,cp);
		try {
			cmd.execute();
			
		} catch (InvalidCommandException e) {
			e.printStackTrace();
		}
		
        cp = cmdp.getCommandPackage("Sort name");		
        ct = CommandType.valueOf("SORT");
	    cmd = new SortCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		CorrectMsg="Sorted by name";
		System.out.println("herere"+msg);
        assertTrue(msg.equals(CorrectMsg));
        
        cp = cmdp.getCommandPackage("Sort time");		
        ct = CommandType.valueOf("SORT");
	    cmd = new SortCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		CorrectMsg="Sorted by time";
        assertTrue(msg.equals(CorrectMsg));
        
        
        cp = cmdp.getCommandPackage("Sort priority");		
        ct = CommandType.valueOf("SORT");
	    cmd = new SortCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		CorrectMsg="Sorted by priority";
        assertTrue(msg.equals(CorrectMsg));
        
        cp = cmdp.getCommandPackage("Sort blabla");		
        ct = CommandType.valueOf("SORT");
	    cmd = new SortCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			msg= e.getMessage();
		}
		CorrectMsg="Invalid sorting type";
        assertTrue(msg.equals(CorrectMsg));
        
	}
	
	@Test
	public void TestUpdateCommand() {
		Manager mgr = new Manager();
		String msg="";
		String CorrectMsg="";
		
		CommandPackage cp = cmdp.getCommandPackage("add party 9pm #3");		
        CommandType ct = CommandType.valueOf("CREATE");
        Command cmd = new AddCommand(ct,mgr,cp);
		try {
			cmd.execute();
			
		} catch (InvalidCommandException e) {
			
			e.printStackTrace();
		}

        cp = cmdp.getCommandPackage("update `party `endtime `10pm");		
        ct = CommandType.valueOf("UPDATE");
	    cmd = new UpdateCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
		} catch (InvalidCommandException e) {
			msg= e.getMessage();
		}
		
		CorrectMsg="Task \"party\" end time updated";
        assertTrue(msg.equals(CorrectMsg));
        
        cp = cmdp.getCommandPackage("update `party `# `2");		
        ct = CommandType.valueOf("UPDATE");
	    cmd = new UpdateCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
		} catch (InvalidCommandException e) {
			msg= e.getMessage();
		}
		
		CorrectMsg="Task \"party\" priority updated to \"2\"";
        assertTrue(msg.equals(CorrectMsg));
        
        cp = cmdp.getCommandPackage("update `party `name `home");		
        ct = CommandType.valueOf("UPDATE");
	    cmd = new UpdateCommand(ct,mgr,cp);
		try {
			msg= cmd.execute();
		} catch (InvalidCommandException e) {
			msg= e.getMessage();
		}
		
		CorrectMsg="Task \"party\" updated to \"home\"";
		System.out.println("herere"+msg);
        assertTrue(msg.equals(CorrectMsg));
        
        
	}

}