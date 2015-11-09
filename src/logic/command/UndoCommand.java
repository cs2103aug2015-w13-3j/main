//@@author A0133949U
package logic.command;

import logic.Command;
import logic.CommandType;
import logic.Manager;


public class UndoCommand extends Command{
	CommandType ct;
	Manager mgr;

	public UndoCommand(CommandType ct,Manager manager) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
	}

	@Override
	public String execute() throws InvalidCommandException{
		if(mgr.undo()){
		    return "Undo previous operation";
		}else{
			throw new InvalidCommandException("No operation to undo");
		}
	}

}
