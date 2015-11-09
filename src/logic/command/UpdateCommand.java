//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Task;
import parser.CommandPackage;
import parser.DateParser;

public class UpdateCommand extends Command{
	CommandType ct;
	Manager mgr;
	CommandPackage commandInfo;

	public UpdateCommand(CommandType ct,Manager manager,CommandPackage cp) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.commandInfo = cp;
		
	}

	@Override
	public String execute() throws InvalidCommandException{
		
		ArrayList<String> update = commandInfo.getUpdateSequence();
		String target = update.get(1);
		Task task;
		Task newTask;
		boolean found=false;
		String msg="";
		ArrayList<Task> taskList = mgr.getTaskList();

		for (int i = 0; i < taskList.size(); i++) {
			task = taskList.get(i);
			if (task.getName().equals(target)) {
                found=true;
                
				if (update.get(2).equalsIgnoreCase("name")) {
					if (update.get(3) != null) {
						task.setTaskName(update.get(3));
					}
					msg = "Task \""+target+"\" updated to \""+task.getName()+"\"";

					
				} else if (update.get(2).equals("#") || 
					update.get(2).equalsIgnoreCase("priority")) {
					
					int priority = Integer.parseInt(update.get(3));	
					
					if(priority>3 || priority<=0){
						throw new InvalidCommandException
						("Invalid priority.Priority is valid from 1 to 3");
					}
					task.setPriority(update.get(3));
					
					msg = "Task \""+target+"\" priority updated to \""+priority+"\"";
					
				} else if (update.get(2).equalsIgnoreCase("startTime")) {
					if(commandInfo.startingTime()==null){
						
					}
					task.setStartTime(commandInfo.startingTime());
					msg = "Task \""+target+"\" start time updated to \""
					+commandInfo.startingTime().toString()+"\"";
					
				} else if (update.get(2).equalsIgnoreCase("endTime")) {
					task.setEndTime(commandInfo.endingTime());
					msg = "Task \""+target+"\" end time updated to \""
							+commandInfo.endingTime().toString()+"\"";
					
				} else{
					throw new InvalidCommandException("Invalid update type. You can"
							+ "update name/priority/startTime/endTime");
				}

				newTask = new Task(task.getName());
				newTask.setStartTime(task.getStartTime());
				newTask.setEndTime(task.getEndTime());
				
				if (task.getPriority() != null) {
					newTask.setPriority(task.getPriority().toString());
				}

				mgr.update(i, newTask);
				
				return msg;
				
			}

		}
		
		return "Task not Found";
		
	}

}
