package storage;

import logic.Task;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.format.ISODateTimeFormat;

public class Storage {

	private static File filePath = new File("filePath");
	private static File userFile;

	public static ArrayList<Task> read() {
		ArrayList<Task> taskList = new ArrayList<Task>();
		try {
			
			filePath.createNewFile();

			FileReader fr = new FileReader(filePath);
			BufferedReader buff = new BufferedReader(fr);
			String path = buff.readLine();
			if(path == null){
				path = "taskBomber.txt";
			}
			userFile = new File(path);
			userFile.createNewFile();
			fr = new FileReader(userFile);
			buff = new BufferedReader(fr);
			String content;

			// read in original content in the file
			System.out.println("Reading file.");
			while ((content = buff.readLine()) != null) {

				String[] taskInfo = content.split("\\|");
				Task taskToRead = new Task(taskInfo[0]);

				if (taskInfo[1].equals(" ")) {
					taskToRead.setStartTime(null);
				} else {
					taskToRead.setStartTime(ISODateTimeFormat.dateTime().parseDateTime(taskInfo[1]));
				}

				if (taskInfo[2].equals(" ")) {
					taskToRead.setEndTime(null);
				} else {
					taskToRead.setEndTime(ISODateTimeFormat.dateTime().parseDateTime(taskInfo[2]));
				}
				
				if(taskInfo[3].equals("null")){
					//System.out.println("taskInfo 3 is null");
					taskInfo[3] = null;
				}

				//System.out.println("priority in storage: " + taskInfo[3]);

				if (taskInfo[3] != null && taskInfo[3] != "") {
					taskToRead.setPriority(taskInfo[3]);
				}
				taskList.add(taskToRead);
			}
			buff.close();
			fr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;
	}

	public static void write(ArrayList<Task> taskList) {
		try {
			FileWriter fw = new FileWriter(userFile, false);
			BufferedWriter buff = new BufferedWriter(fw);
			Task taskToWrite;
			String content = "";
			for (int i = 0; i < taskList.size(); i++) {
				taskToWrite = taskList.get(i);

				content += taskToWrite.getName();
				content += "|";

				if (taskToWrite.getStartTime() == null) {
					content += " ";
				} else {
					content += taskToWrite.getStartTime().toString();
				}
				content += "|";

				if (taskToWrite.getEndTime() == null) {
					content += " ";
				} else {
					content += taskToWrite.getEndTime().toString();
				}
				content += "|";

				content += taskToWrite.getPriority();
				if (i != (taskList.size() - 1)) {
					content += "\n";
				}
				// System.out.println("Writing into file.");

			}
			buff.write(content);
			buff.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Boolean setPath(String path){
		try{
			File newPath = new File(path);
			newPath.createNewFile();
			write(read());
			userFile.delete();
			userFile = newPath;
			FileWriter fw = new FileWriter(filePath, false);
			BufferedWriter buff = new BufferedWriter(fw);
			buff.write(path);
			buff.close();
			fw.close();
			return true;
		}catch(Exception e){
			System.out.println("invalid path");
			return false;
		}
	}

}
