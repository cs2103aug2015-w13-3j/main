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
	public String execute() {
		int index=0;
		ArrayList<Task> taskList = mgr.getTaskList();
		Task t;
		if (isInteger(markInfo, 10)) { // mark by index
			index = Integer.parseInt(markInfo)-1;
			
		} else {// mark by name
			for (int i = 0; i < taskList.size(); i++) {
				t = taskList.get(i);
				if (t.getName().equals(markInfo)) {
					index=i;
					break;
				}
			}
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
