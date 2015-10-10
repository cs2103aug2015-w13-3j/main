package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TempoTask {
	
    private final SimpleIntegerProperty taskNum;
    private final SimpleStringProperty taskName;
    private final SimpleStringProperty start;
    private final SimpleStringProperty end;
    private final SimpleIntegerProperty pri;
    
    public TempoTask(int taskNum, String name, String startTime, String endTime, int priority){
    	this.taskNum = new SimpleIntegerProperty(taskNum);
    	this.taskName = new SimpleStringProperty(name);
    	this.start = new SimpleStringProperty(startTime);
    	this.end = new SimpleStringProperty(endTime);
    	this.pri = new SimpleIntegerProperty(priority);
    }
    
    public int getTaskNum() {
        return taskNum.get();
    }

    public void setTaskNum(int taskNumber) {
    	taskNum.set(taskNumber);
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String tName) {
    	taskName.set(tName);
    }

    public String getStart() {
        return start.get();
    }

    public void setStart(String st) {
    	start.set(st);
    }
    
    public String getEnd() {
        return end.get();
    }

    public void setEnd(String endTime) {
    	end.set(endTime);
    }
    
    public int getPri() {
        return pri.get();
    }

    public void setPri(int priority) {
    	pri.set(priority);
    }
}
