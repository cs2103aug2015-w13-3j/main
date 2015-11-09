//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Task;
import parser.CommandPackage;

public class AddCommand extends Command{
	CommandType ct;
	Manager mgr;
	CommandPackage commandInfo;

	public AddCommand(CommandType ct,Manager manager,CommandPackage cp) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.commandInfo = cp;
		
	}

	@Override
	public String execute() {
		
		Task task = new Task(commandInfo.getPhrase());
		if (commandInfo.startingTime() != null) {
			task.setStartTime(commandInfo.startingTime());
		}
		if (commandInfo.endingTime() != null) {
			task.setEndTime(commandInfo.endingTime());
		}

		if (commandInfo.getPriority() != null) {
			String pri = commandInfo.getPriority().trim();
			if (pri != null && pri != "") {
				task.setPriority(pri);
			}
		}

		mgr.addToTaskList(task);

		return "Task Added: "+task.getName();
	}

}
