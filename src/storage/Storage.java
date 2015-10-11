package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.format.ISODateTimeFormat;


public class Storage {

	private static String fileName = "textBomb.txt";
	private static File userFile;
	
	public static ArrayList<Task> Read(){
		ArrayList<Task> taskList = new ArrayList<Task>();
		try{
			userFile = new File(fileName);
			userFile.createNewFile();
			FileReader fr = new FileReader(userFile);
			BufferedReader buff = new BufferedReader(fr);
			String content;
			
			//read in original content in the file
			while((content = buff.readLine())!=null){
					String[] taskInfo = content.split("|");
					Task taskToRead = new Task(taskInfo[0]);
					
					if(taskInfo[1].equals(" ")){
						taskToRead.setStartTime(null);
					}else{
						taskToRead.setStartTime(ISODateTimeFormat.dateTime().parseDateTime(taskInfo[1]));
					}
	                
					if(taskInfo[2].equals(" ")){
						taskToRead.setDeadline(null);
					}else{
						taskToRead.setDeadline(ISODateTimeFormat.dateTime().parseDateTime(taskInfo[2]));
					}
					
					taskToRead.setPriority(Integer.parseInt(taskInfo[3]));
			
					taskList.add(taskToRead);
			}
			buff.close();
			fr.close();
			
		}catch (IOException e) {
		    // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;
	}
	
	public static void write(ArrayList<Task> taskList){
		try{
			FileWriter fw = new FileWriter(userFile);
			BufferedWriter buff = new BufferedWriter(fw);
			Task taskToWrite;
			String content = "";
		    for(int i = 0; i < taskList.size(); i++){
		    	taskToWrite = taskList.get(i);
		   
		    	content += taskToWrite.getName();
		    	content += "|";
		    	
		    	if(taskToWrite.getStartTime() == null){
		    		content += " ";
		    	}else{
		    		content += taskToWrite.getStartTime().toString();
		    	}
		    	content += "|";
		    	
		    	if(taskToWrite.getDeadline() == null){
		    		content += " ";
		    	}else{
		    		content += taskToWrite.getDeadline().toString();
		    	}
		    	content += "|";
		    	
		    	content += taskToWrite.getPriority();
		    	if(i != (taskList.size() - 1)){
		    		content += "\n";
		    	}
		 
			    buff.write(content);
		    }
		    buff.close();
		    fw.close();
		}catch (IOException e) {
		    // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
