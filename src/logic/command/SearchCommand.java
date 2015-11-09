//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Searcher;
import logic.Task;
import parser.CommandPackage;

public class SearchCommand extends Command{
	CommandType ct;
	Manager mgr;
	CommandPackage commandInfo;

	public SearchCommand(CommandType ct,Manager manager,CommandPackage cp) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.commandInfo = cp;
		
	}

	@Override
	public String execute() throws InvalidCommandException{
		
		Task task = new Task(commandInfo.getPhrase());
		if (commandInfo.startingTime() != null) {
			task.setStartTime(commandInfo.startingTime());
		}

		if (commandInfo.endingTime() != null) {
			task.setEndTime(commandInfo.endingTime());
		}

		String pri = commandInfo.getPriority().trim();

		if (pri != null && pri != "") {
			int priority = Integer.parseInt(pri);
			
			if(priority>3 || priority<=0){
				throw new InvalidCommandException
				("Invalid priority.Priority is valid from 1 to 3");
			}
			task.setPriority(priority);
		}

        mgr.search(task);
		
		return "Search completed. To go back, press Enter";
	}

}
