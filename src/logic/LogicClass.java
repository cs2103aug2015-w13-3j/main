package logic;

//@@author A0133949U
import storage.Storage;
import java.util.ArrayList;
import logic.command.AddCommand;
import logic.command.ClearCommand;
import logic.command.DeleteCommand;
import logic.command.InvalidCommandException;
import logic.command.MarkCommand;
import logic.command.RedoCommand;
import logic.command.SearchCommand;
import logic.command.SetCommand;
import logic.command.SortCommand;
import logic.command.UndoCommand;
import logic.command.UpdateCommand;
import parser.CommandPackage;

public class LogicClass implements LogicInterface{

	private static boolean isSearchOp = false;
	Storage storage = Storage.getInstance();
	Manager mgr;	
	private String path = "";
	static LogicClass theOne = null;

	// constructor
	private LogicClass() {
		mgr= new Manager();
		mgr.initialize(storage.read().get(0), 
				storage.read().get(1));//taskList and archivedList respectively
		
	}

	public static LogicClass getInstance() {

		if (theOne == null) {
			theOne = new LogicClass();
		}
		return theOne;
	}

	public ArrayList<Task> getTaskList(){
		
		if(isSearchOp==true){
			return mgr.getSearchList();
		}	
		return mgr.getTaskList();
	}
	
	public ArrayList<Task> getTaskListForSearcher(){	
		return mgr.getTaskList();
	}
	
	public String executeCommand(CommandPackage commandPackage) throws InvalidCommandException {
		assert commandPackage!=null;
		CommandType commandType = CommandType.valueOf(commandPackage.getCommand().toUpperCase());	
		Command cmd = null;

		switch (commandType) {
		case CREATE:
			isSearchOp=false;
			cmd = new AddCommand(commandType,mgr,commandPackage);	
			break;
		case UPDATE:
			cmd = new UpdateCommand(commandType,mgr,commandPackage);
			break;
		case DELETE:
			if(isSearchOp==false){
				cmd = new DeleteCommand(commandType,mgr,commandPackage.getPhrase(),false);
			}else{
				cmd = new DeleteCommand(commandType,mgr,commandPackage.getPhrase(),true);
			}
			
			break;
		case CLEAR:
			cmd = new ClearCommand(commandType,mgr);
			break;
		case SORT:
			cmd = new SortCommand(commandType,mgr,commandPackage);
			break;
		case SEARCH:
			isSearchOp = true;
			cmd = new SearchCommand(commandType,mgr,commandPackage);
			break;	
		case REDO:
			cmd = new RedoCommand(commandType,mgr);
			break;
		case UNDO:
			cmd = new UndoCommand(commandType,mgr);
			break;
		case MARK:
			cmd = new MarkCommand(commandType,mgr,commandPackage.getPhrase());
			break;
		case SET:
			cmd = new SetCommand(commandType,mgr,commandPackage.getPhrase());
			break;
		case EXIT:
			System.exit(0);
		default:
			return invalid();

		}
		
		String returnMsg= cmd.execute();
		return returnMsg;
		
	}

	private String invalid() {
		return "The command is invalid, type in help to see help sheet.";
	}

	public boolean setPath(String path) {
		return storage.setPath(path);
	}


	public boolean setPathFirstTime(){
		return storage.setPath(this.path);
	}
	
	public void setIsSearchOp(boolean tof){
		isSearchOp=tof;
	}
}
