//@@author A0133949U
package logic.command;

import logic.Command;
import logic.CommandType;
import logic.Manager;


public class RedoCommand extends Command{
	CommandType ct;
	Manager mgr;

	public RedoCommand(CommandType ct,Manager manager) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		
	}

	@Override
	public String execute() throws InvalidCommandException{
		if(mgr.redo()){
		    return "Redo previous operation";
		}else{
			throw new InvalidCommandException("No operation to redo");
		}
	}

}
