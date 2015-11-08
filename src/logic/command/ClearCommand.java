//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.ReturnPackage;
import parser.CommandPackage;

public class ClearCommand extends Command{
	CommandType ct;
	Manager mgr;

	public ClearCommand(CommandType ct,Manager manager) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		
	}

	@Override
	public String execute() {

		mgr.clear();

		return "all tasks cleared";
	}

}
