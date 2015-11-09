//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Task;

public class MarkCommand extends Command{

	CommandType ct;
	Manager mgr;
	String markInfo;

	public MarkCommand(CommandType ct,Manager manager,String markInfo) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.markInfo = markInfo;
		
	}

	@Override
	/**
	 * execute mark command and return a string of message
	 * @return     return message
	 * @throws InvalidCommandException  If command is invalid.
	 */
	public String execute() throws InvalidCommandException{
		int index=0;
		ArrayList<Task> taskList = mgr.getTaskList();
		Task t;
		boolean found =false;
		
		if (isInteger(markInfo, 10)) { // delete by index
			index = Integer.parseInt(markInfo)-1;
			if(index<taskList.size()  && index>=0){
				found =true;
				t= mgr.mark(index);		
				return "Task done: "+t.getName();
			}
			
		}  
		// delete by name
		for (int i = 0; i < taskList.size(); i++) {
			t = taskList.get(i);
			if (t.getName().equals(markInfo)) {
				index=i;
				found = true;
				break;
			}
		}
		
		if(found == false){
			throw new InvalidCommandException("Task not found");
		}
		
		t= mgr.mark(index);
		return "Task done: " + t.getName();
	}
	
	private boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

}
