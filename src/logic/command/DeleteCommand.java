//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Task;

public class DeleteCommand extends Command{

	CommandType ct;
	Manager mgr;
	String deleteInfo;

	public DeleteCommand(CommandType ct,Manager manager,String deleteInfo) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.deleteInfo = deleteInfo;
		
	}

	@Override
	public String execute() throws InvalidCommandException{
		int index=0;
		boolean found = false;
		ArrayList<Task> taskList = mgr.getTaskList();
		Task t;
		if (isInteger(deleteInfo, 10)) { // delete by index
			index = Integer.parseInt(deleteInfo)-1;
			if(index<taskList.size()  && index>=0){
				found =true;
				t= mgr.delete(index);		
				return "Task deleted: "+t.getName();
			}
			
		}  
		// delete by name
		for (int i = 0; i < taskList.size(); i++) {
			t = taskList.get(i);
			if (t.getName().equals(deleteInfo)) {
				index=i;
				found = true;
				break;
			}
		}
		
		if(found == false){
			throw new InvalidCommandException("Task not found");
		}
		
		t= mgr.delete(index);		
		return "Task deleted: "+t.getName();
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
