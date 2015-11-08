//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.ReturnPackage;
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
	public String execute() {
		int index=0;
		ArrayList<Task> taskList = mgr.getTaskList();
		Task t;
		if (isInteger(deleteInfo, 10)) { // delete by index
			index = Integer.parseInt(deleteInfo)-1;
			
		} else {// delete by name
			for (int i = 0; i < taskList.size(); i++) {
				t = taskList.get(i);
				if (t.getName().equals(deleteInfo)) {
					index=i;
					break;
				}
			}
		}
		mgr.delete(index);
		
		return "task deleted";
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
