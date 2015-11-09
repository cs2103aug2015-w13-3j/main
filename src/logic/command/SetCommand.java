//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Task;

public class SetCommand extends Command{

	CommandType ct;
	Manager mgr;
	String pathInfo;

	public SetCommand(CommandType ct,Manager manager,String pathInfo) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.pathInfo = pathInfo;
		
	}

	@Override
	public String execute() {
		if(mgr.setPath(pathInfo)){
			return "Path set to"+ pathInfo;
		}else{
			return "Invalid path";
			
		}
	}

}
