//@@author A0133949U
package logic.command;

public class InvalidCommandException extends Exception{
    
	public InvalidCommandException(){
		super();
	}
	
	public InvalidCommandException(String msg){
		super(msg);
	}
}
