//package CE1;

import java.io.IOException;
import java.util.Scanner;

/**
 * Runs actual program that initiates ReadFile.java and takes command given by
 * users. This command will edit into the existing file that has been input.
 * Such commands include add, clear, delete and display and terminate the
 * program
 */

public class TextBuddy {

	public static void main(String[] args) throws Exception {
		TextBuddy ce1 = new TextBuddy();
		ce1.run(args[0]);
	}

	private void run(String args) throws Exception {
		String file_name = args;
		try {
			tryTextBuddy(file_name);
		} catch (IOException e) {
			printError(e);
		}
	}

	private void printError(IOException e) {
		print(e.getMessage());
	}

	private void tryTextBuddy(String file_name) throws IOException {
		// executes TextBuddy if there is no error
		print("Welcome to TextBuddy. mytextfile.txt is ready for use");
		printCMDRequest();
		ReadFile file = new ReadFile(file_name);
		Scanner sc = new Scanner(System.in);
		readCMD(file, sc);
	}

	private void readCMD(ReadFile file, Scanner sc) throws IOException {
		// runs the commands and reads the parameters given
		String CMD = sc.next();
		while (!CMD.equals("exit")) {
			if (CMD.equals("display")) {
				file.display();
			} else if (CMD.equals("add")) {
				addToTask(file, sc);
			} else if (CMD.equals("delete")) {
				deleteRank(file, sc);
			} else if (CMD.equals("clear")) {
				file.clear();
			}
			printCMDRequest();
			CMD = sc.next();
		}
	}

	private void printCMDRequest() {
		System.out.print("command: ");
	}

	private void deleteRank(ReadFile file, Scanner sc) throws IOException {
		int rankNum = sc.nextInt();
		file.delete(rankNum);
	}

	private void addToTask(ReadFile file, Scanner sc) throws IOException {
		String task = sc.nextLine();
		file.addLine(task.trim());
	}

	private void print(String result) {
		System.out.println(result);
	}

}
