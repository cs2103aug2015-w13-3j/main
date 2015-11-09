//@@author A0133915H
package gui;

import java.util.ArrayList;
import java.util.Scanner;

import logic.LogicClass;
import logic.Task;
import logic.command.InvalidCommandException;
import parser.CommandPackage;
import parser.CommandParser;

/************************************************************************
 * This is a simple test for the whole system, but not automated. 
 * @author susumei
 *
 ************************************************************************/
public class Testing {
	public static void main(String[] args) throws InvalidCommandException {
		Scanner sc = new Scanner(System.in);
		LogicClass logic = LogicClass.getInstance();
		CommandParser cmdParser = CommandParser.getInstance();
		while (true) {
			System.out.println("Plz key in your command: ");
			String input = sc.nextLine();
			CommandPackage cmdPack = cmdParser.getCommandPackage(input);
			logic.executeCommand(cmdPack);
			ArrayList<Task> taskList = new ArrayList<Task>();
			taskList = LogicClass.getInstance().getTaskList();
			for (int i = 0; i < taskList.size(); i++) {
				System.out.println(i + ". " + taskList.get(i).getName());
			}
			sc.close();
		}
	}
}
