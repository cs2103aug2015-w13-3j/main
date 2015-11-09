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
	boolean inSearchStatus;

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
		Task task=null;
		Task newTask;
		boolean found=false;
		String msg="";
		ArrayList<Task> taskList = mgr.getTaskList();
		int targetIndex=-1;
		String updateType="";
		
		if(isInteger(target,10)==true){
			targetIndex = Integer.parseInt(target)-1;
			task = taskList.get(targetIndex);
		}
		
		if(targetIndex<taskList.size() &&  targetIndex>=0 ){
			found=true;
		}else{ 
			for (int i = 0; i < taskList.size(); i++) {
				task = taskList.get(i);
				if (task.getName().equals(target)){
					found = true;
					targetIndex = i;
					break;
				}
			}
		}
		
		
		
		if(found == false){
			
			throw new InvalidCommandException("Task not found.Is the index or name correct?");
			
		}else{//found == true
			
			newTask= copyOf(task);
			
			if (update.get(2).equalsIgnoreCase("name")) {
				if (update.get(3) == null || update.get(3).equals("")) {
					throw new InvalidCommandException("What's the new name?");
				}
				assert update.get(3)!=null;
				newTask.setTaskName(update.get(3));
				msg = "Task \""+target+"\" updated to \""+task.getName()+"\"";

			} else if (update.get(2).equals("#") || 
				update.get(2).equalsIgnoreCase("priority")) {
				
				//System.out.println("update p: "+update.get(3));
				
				if (update.get(3) == null || update.get(3).equals("")) {
					throw new InvalidCommandException("What's the new priority?");
				}
				
				if (update.get(3).equals("remove") || update.get(3).equals("delete")) {
					newTask.setPriority(null);
					msg = "priority removed";
					
				}else if(!isInteger(update.get(3),10)){
					throw new InvalidCommandException("Priority should be an integer");
					
				}else if(isInteger(update.get(3),10)){
					int priority = Integer.parseInt(update.get(3));	
					
					if(priority>3 || priority<=0){		
						throw new InvalidCommandException
						("Invalid priority.Priority is valid from 1 to 3");
					}
					
					newTask.setPriority(priority);
					msg = "Task \""+target+"\" priority updated to \""+priority+"\"";
				}

			} else if (update.get(2).equalsIgnoreCase("startTime") || 
					update.get(2).equalsIgnoreCase("st")) {
				
				if (update.get(3) == null || update.get(3).equals("")) {
					throw new InvalidCommandException("What's the new start time?");
				}
				if (update.get(3) == "remove" || update.get(3)=="delete") {
					newTask.setStartTime(null);
					msg = "priority removed";
				}else{
					newTask.setStartTime(commandInfo.startingTime());
					msg = "Task \""+target+"\" start time updated";
				}
				
				
			} else if (update.get(2).equalsIgnoreCase("endTime") || 
					update.get(2).equalsIgnoreCase("et")) {
				
				if (update.get(3) == null || update.get(3).equals("")) {
					throw new InvalidCommandException("What's the new end time?");
				}
				if (update.get(3) == "remove" || update.get(3)=="delete") {
					newTask.setEndTime(null);
					msg = "priority removed";
				}else{
					newTask.setEndTime(commandInfo.endingTime());
					msg = "Task \""+target+"\" end time updated";
				}
				
				
			} else{
				throw new InvalidCommandException("Invalid update type. You can"
						+ "update name/priority/startTime/endTime");
			}

			mgr.update(targetIndex, newTask);
			
			return msg;
			
		}
		
	}
	
	private boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}
	
	private Task copyOf(Task target){
		Task newTask = new Task(target.getName());
		
		if (target.getStartTime() != null) {
			newTask.setStartTime(target.getStartTime());;
		}
		
		if (target.getEndTime() != null) {
			newTask.setEndTime(target.getEndTime());;
		}
		
		if (target.getPriority() != null) {
			newTask.setPriority(target.getPriority());
		}
		
		return newTask;
	}

}
