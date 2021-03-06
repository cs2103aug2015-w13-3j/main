//@@author A0133949U
package logic.command;

import java.util.ArrayList;
import java.util.Collections;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.PriorityTaskList;
import logic.Task;
import parser.CommandPackage;

public class SortCommand extends Command{
	CommandType ct;
	Manager mgr;
	CommandPackage commandInfo;

	public SortCommand(CommandType ct,Manager manager,CommandPackage cp) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.commandInfo = cp;
		
	}

	@Override
	public String execute() throws InvalidCommandException{
		
		if (commandInfo.getPhrase().equals("name")) {
			mgr.sort("name");
			return "Sorted by name";
			
		} else if (commandInfo.getPhrase().equals("time")) {
			mgr.sort("time");
			return "Sorted by time";
			
		} else if (commandInfo.getPhrase().equals("priority")) {
			mgr.sort("priority");
			return "Sorted by priority";

		} else {
			throw new  InvalidCommandException("Invalid sorting type");
		}
	}

}
