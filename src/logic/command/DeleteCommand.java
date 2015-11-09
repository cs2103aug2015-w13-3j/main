//@@author A0133949U
package logic.command;

import java.util.ArrayList;

import logic.Command;
import logic.CommandType;
import logic.Manager;
import logic.Task;

public class DeleteCommand extends Command{

	CommandType ct;
	Manager mgr;
	String deleteInfo;
	boolean inSearchStatus=false;

	public DeleteCommand(CommandType ct,Manager manager,
			String deleteInfo, boolean inSearchStatus) {
		super(ct);
		this.ct= ct;
		this.mgr=manager;	
		this.deleteInfo = deleteInfo;
		this.inSearchStatus=inSearchStatus;
		
		
	}

	@Override
	public String execute() throws InvalidCommandException{
		
		if(inSearchStatus==false){
			int index=0;
			int searchIndex=-1;
			boolean found = false;
			ArrayList<Task> taskList = mgr.getTaskList();
			Task t;
			if (isInteger(deleteInfo, 10)) { // delete by index
				index = Integer.parseInt(deleteInfo)-1;
				if(index<taskList.size()  && index>=0){
					found =true;
					t= mgr.delete(index,searchIndex,false);		
					return "Task deleted: "+t.getName();
				}
				
			}  
			// delete by name
			for (int i = 0; i < taskList.size(); i++) {
				t = taskList.get(i);
				if (t.getName().equals(deleteInfo)) {
					index=i;
					found = true;
					break;
				}
			}
			
			if(found == false){
				throw new InvalidCommandException("Task not found");
			}
			
			t= mgr.delete(index,searchIndex,false);		
			return "Task deleted: "+t.getName();
			
		}else{ //in search status
			
			int searchIndex=-1;
			int taskIndex=-1;
			boolean found = false;
			ArrayList<Task> searchList = mgr.getSearchList();
			ArrayList<Task> taskList = mgr.getTaskList();
			
			Task t;
			if (isInteger(deleteInfo, 10)) { // delete by index
				searchIndex = Integer.parseInt(deleteInfo)-1;
				if(searchIndex<searchList.size()  && searchIndex>=0){
					
					found =true;
					t= searchList.get(searchIndex);				
					Task temp;
					
					for (int i = 0; i < taskList.size(); i++) {
						temp = taskList.get(i);
						if (temp.equals(t)) {
							found= true;
							taskIndex=i;
							break;
						}
					}
					t= mgr.delete(taskIndex,searchIndex,true);		
					return "Task deleted: "+t.getName();
				}
				
			}  
			// delete by name
			for (int i = 0; i < searchList.size(); i++) {
				t = searchList.get(i);
				
				if (t.getName().equals(deleteInfo)) {
					searchIndex=i;
					Task temp;
					
					for (int j = 0; j < taskList.size(); j++) {
						temp = taskList.get(i);
						if (temp.equals(t)) {
							taskIndex=i;
							break;
						}
					}
					found = true;
					t= mgr.delete(taskIndex,searchIndex,true);		
					return "Task deleted: "+t.getName();
				}
			}
			
			//task not found
			if(found == false){
				throw new InvalidCommandException("Task not found in searched list. To return to full list,press Enter");
			}
			return "Task not found";
			
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

}
