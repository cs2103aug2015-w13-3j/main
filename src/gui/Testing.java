package gui;

import java.util.ArrayList;
import java.util.Scanner;

import logic.LogicClass;
import logic.Task;
import parser.CommandPackage;
import parser.CommandParser;
import storage.Storage;

public class Testing {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Storage storage = new Storage();
		LogicClass logic = LogicClass.getInstance(storage);
		CommandParser cmdParser = new CommandParser();
		while (true) {
			System.out.println("Plz key in your command: ");
			String input = sc.nextLine();
			CommandPackage cmdPack = cmdParser.getCommandPackage(input);
			logic.executeCommand(cmdPack);
			ArrayList<Task> taskList = new ArrayList<Task>();
			taskList = LogicClass.getTaskList();
			for (int i = 0; i < taskList.size(); i++) {
				System.out.println(i + ". " + taskList.get(i).getName());
			}
		}
	}
}