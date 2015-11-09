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
//@@author A0133948W
public class Storage {

	private static File filePath = new File("filePath");
	private static File userFile = new File("taskBomber.txt");
	private static Storage storage = null;
	
	private Storage(){
	}
	
	public static Storage getInstance(){
		if(storage == null){
			storage = new Storage();
			return storage;
		}else{
			return storage;
		}
	}
	
	/**
	* This operation open the file and start to read it
	*
	* @return a list contain the ongoing tasklist and 'done' tasklist.
	*/

	public ArrayList<ArrayList<Task>> read() {
		ArrayList<ArrayList<Task>> result = null;
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
			result = readInTask(buff);
			buff.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	* This operation read all the task from local file
	*
	* @return a list contain the ongoing tasklist and 'done' tasklist.
	*/
	
	private ArrayList<ArrayList<Task>> readInTask(BufferedReader buff) throws IOException {
		String content;
		boolean done = false;
		ArrayList<Task> taskList = new ArrayList<Task>();
		ArrayList<Task> doneList = new ArrayList<Task>();
		while ((content = buff.readLine()) != null && content.trim().length() != 0) {
			System.out.println("content" + content);
            if(content.equals("done:")){
		    	done = true;
		    	continue;
		    }
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
			if (taskInfo[3] != null && taskInfo[3] != "") {
				taskToRead.setPriority(taskInfo[3]);
			}
			if(done == false){
				taskList.add(taskToRead);
			}else{
				doneList.add(taskToRead);
			}
		}
		ArrayList<ArrayList<Task>> result = new ArrayList<ArrayList<Task>>();
		result.add(taskList);
		result.add(doneList);
		return result;
	}

	/**
	* This operation open the file and start to write into it
	*
	* @param ongoing tasklist and 'done' tasklist.
	*/
	public void write(ArrayList<Task> taskList, ArrayList<Task> doneList) {
		try {
			FileWriter fw = new FileWriter(userFile, false);
			BufferedWriter buff = new BufferedWriter(fw);
			String content = "";
			content = writeTasks(taskList, content);
			content += "done:";
			content += "\n";
			content = writeTasks(doneList, content);
			buff.write(content);
			buff.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* This operation open the file and start to write into it
	*
	* @param a list of tasks to be written and previous content
	*/
	private String writeTasks(ArrayList<Task> taskList, String content) {
		Task taskToWrite;
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
			content += "\n";
			// System.out.println("Writing into file.");
		}
		return content;
	}
	
	/**
	* This operation set the path of storage file to the specific path
	*
	* @param file path
	* @return a boolean value which implies the success of operation
	*/
	public Boolean setPath(String path){
		try{
			File newPath = new File(path);
			newPath.createNewFile();
			ArrayList<ArrayList<Task>> content = read();
			userFile.delete();
			userFile = newPath;
			write(content.get(0), content.get(1));
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
