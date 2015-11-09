//@@author A0133949U
package logic;

import logic.command.InvalidCommandException;

public abstract class Command {
	private CommandType ct;
	
	public Command(CommandType ct) {		
		this.ct = ct;
	}
	
	public abstract String execute() throws InvalidCommandException;
	
}
