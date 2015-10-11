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

    private static String fileName = "textBomb";
    private static File userFile;
    
    public ArrayList<Task> Read(){
        ArrayList<Task> taskList = new ArrayList<Task>();
        try{
            userFile = new File(fileName);
            userFile.createNewFile();
            
            System.out.println("Created userfile.");
            
            FileReader fr = new FileReader(userFile);
            BufferedReader buff = new BufferedReader(fr);
            String content;
            
            //read in original content in the file
            System.out.println("Reading file.");
            while((content = buff.readLine())!=null){
                
                String[] taskInfo = content.split("\\|");
                Task taskToRead = new Task(taskInfo[0]);
                
                
                if(taskInfo[1].equals(" ")){
                    taskToRead.setStartTime(null);
                }else{
                    taskToRead.setStartTime(ISODateTimeFormat.dateTime().parseDateTime(taskInfo[1]));
                }
                
                if(taskInfo[2].equals(" ")){
                    taskToRead.setEndTime(null);
                }else{
                    taskToRead.setEndTime(ISODateTimeFormat.dateTime().parseDateTime(taskInfo[2]));
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
            FileWriter fw = new FileWriter(userFile, false);
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
                
                if(taskToWrite.getEndTime() == null){
                    content += " ";
                }else{
                    content += taskToWrite.getEndTime().toString();
                }
                content += "|";
                
                content += taskToWrite.getPriority();
                if(i != (taskList.size() - 1)){
                    content += "\n";
                }
                //System.out.println("Writing into file.");
                
            }
            buff.write(content);
            buff.close();
            fw.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	

}
