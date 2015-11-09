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
	// This array will be used to store the messages

	private static ArrayList<Task> archivedList = new ArrayList<Task>();
	private static boolean isSearchOp = false;
	Storage storage = Storage.getInstance();

	
	Manager mgr= Manager.getInstance();
	
	private String path = "";

	static LogicClass theOne = null;

	// constructor
	private LogicClass() {
		mgr.setTaskList(storage.read().get(0));	
		mgr.setArchivedList(storage.read().get(1));

	}

	public static LogicClass getInstance() {

		if (theOne == null) {
			theOne = new LogicClass();
		}
		return theOne;
	}

	public ArrayList<Task> getTaskList(){

		if(isSearchOp==true){
			isSearchOp=false;
			return mgr.getSearchList();
		}
		
		return mgr.getTaskList();
	}
	
	public ArrayList<Task> getTaskListForSearcher(){
		
		return mgr.getTaskList();
	}
	
	public String executeCommand(CommandPackage commandPackage) throws InvalidCommandException {
		isSearchOp = false;
//		if(commandPackage==null){
//			System.out.println("cp is null");
//		}
//		System.out.print("getCommand"+commandPackage.getCommand());
		CommandType commandType = CommandType.valueOf(commandPackage.getCommand().toUpperCase());	
		Command cmd = null;

		switch (commandType) {
		case CREATE:
			cmd = new AddCommand(commandType,mgr,commandPackage);
			break;
		case UPDATE:
			cmd = new UpdateCommand(commandType,mgr,commandPackage);
			break;
		case DELETE:
			cmd = new DeleteCommand(commandType,mgr,commandPackage.getPhrase());
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

	//@author A0133949U
//	public static boolean isTodayTask(Task t) {
//		if (t.getEndTime() == null && t.getStartTime() == null) {
//			return false;
//		}
//
//		DateTime now = new DateTime();
//		LocalDate today = now.toLocalDate();
//		LocalDate tomorrow = today.plusDays(1);
//
//		DateTime startOfToday = today.toDateTimeAtStartOfDay(now.getZone());
//		DateTime startOfTomorrow = tomorrow.toDateTimeAtStartOfDay(now.getZone());
//
//		if (t.getEndTime() == null && t.getStartTime() == null) {
//			return false;
//		}
//
//		if (t.getEndTime() != null) {
//			if (t.getEndTime().isBefore(startOfToday)) {
//				return false;
//			}
//		}
//
//		if (t.getStartTime() != null) {
//			if (t.getEndTime().isBefore(startOfToday)) {
//				return false;
//			}
//		}
//
//		if (t.getStartTime() != null && t.getEndTime() != null && t.getStartTime().isAfter(startOfToday)
//				&& t.getEndTime().isBefore(startOfTomorrow)) {
//			return true;
//		}
//		return false;
//	}




}
