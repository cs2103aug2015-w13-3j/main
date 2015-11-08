//@@author A0133949U
package logic;

public abstract class Command {
	private CommandType ct;
	
	public Command(CommandType ct) {		
		this.ct = ct;
	}
	
	public abstract String execute();
	
}
