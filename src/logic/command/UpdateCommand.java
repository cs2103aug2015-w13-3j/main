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
	public String execute() {
		
		ArrayList<String> update = commandInfo.getUpdateSequence();
		String target = update.get(1);
		Task task;
		Task newTask;
		ArrayList<Task> taskList = mgr.getTaskList();

		for (int i = 0; i < taskList.size(); i++) {
			task = taskList.get(i);
			if (task.getName().equals(target)) {

				if (update.get(2).equalsIgnoreCase("name")) {
					if (update.get(3) != null) {
						task.setTaskName(update.get(3));
					}

				} else if (update.get(2).equals("#")) {
					task.setPriority(update.get(3));
				} else if (update.get(2).equalsIgnoreCase("startTime")) {
					// System.out.println("parse date" +
					// DateParser.setDate(update.get(3)));
					task.setStartTime(commandInfo.startingTime());
				} else if (update.get(2).equalsIgnoreCase("endTime")) {
					task.setEndTime(commandInfo.endingTime());
				}

				newTask = new Task(task.getName());
				newTask.setStartTime(task.getStartTime());
				newTask.setEndTime(task.getEndTime());
				if (task.getPriority() != null) {
					newTask.setPriority(task.getPriority().toString());
				}

				taskList.remove(i);
				taskList.add(i, newTask);
				mgr.setTaskList(taskList);
				break;
			}

		}
		
		return "task updated";
	}

}
