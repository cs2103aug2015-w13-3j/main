 package logic;

import org.joda.time.DateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task implements Comparable<Task> {
	private String name;
	private DateTime startTime = null;
	private DateTime endTime = null;
	private Integer priority = null;
	private Integer taskNum = null;
	
	public Task(String name) {
		this.name = name;
	}

	public Task(String name, DateTime start, DateTime end, int pri) {
		this.name = name;
		this.startTime = start;
		this.endTime = end;
		this.priority = pri;
	}

	public String getName() {
		return this.name;
	}

	public DateTime getStartTime() {
		return this.startTime;
	}

	public DateTime getEndTime() {
		return this.endTime;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public Integer getTaskNum() {
		return this.taskNum;
	}

	public void setStartTime(DateTime start) {
		this.startTime = start;
	}

	public void setEndTime(DateTime end) {
		this.endTime = end;
	}

	//@author A0133915H
	public void setPriority(String pri) {
		if (pri == null) {
			System.out.println("Oh no the priority is null");
		}
		if (pri != null && pri != "") {
			// System.out.println("The priority should not be null here");
			this.priority = Integer.parseInt(pri);
		}
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNum = taskNumber;
	}

	public void setTaskName(String name) {
		this.name = name;
	}

	public boolean containKeyword(String keyword) {
		if (name.matches(".*\\b" + keyword + "\\b.*")) {
			return true;
		} else if (startTime.toString().matches(".*\\b" + keyword + "\\b.*")) {
			return true;
		} else if (endTime.toString().matches(".*\\b" + keyword + "\\b.*")) {
			return true;
			// } else if (deadline.toString().matches(".*\\b" + keyword +
			// "\\b.*")) {
			// return true;
			// } else if (location.matches(".*\\b" + keyword + "\\b.*")) {
			// return true;
			// } else if (description.matches(".*\\b" + keyword + "\\b.*")) {
			// return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Task oTask) {
		int result = this.name.compareToIgnoreCase(oTask.name);
		if (result < 0)
			return -1;
		else if (result == 0)
			return 0;
		else
			return 1;

	}

	@Override
	public String toString() {
		return name;
	}

	//@author A0133915H
	public StringProperty taskNameProperty() {
		return new SimpleStringProperty(this.name);
	}

	//@author A0133915H
	public StringProperty startTimeProperty() {
		// System.out.println("startTime: " + startTime);
		StringProperty result;
		if (this.startTime == null) {
			return new SimpleStringProperty("");
		}

		if (startTime.getYear() == 2015) {
			if(startTime.getMinuteOfHour() == 00) {
				result = new SimpleStringProperty(startTime.toString("d/MMM  haa"));
			} else {
				result = new SimpleStringProperty(startTime.toString("d/MMM  h:mmaa"));
			}
		} else {
			if(startTime.getMinuteOfHour() == 00) {
				result = new SimpleStringProperty(startTime.toString("d/MMM/yy  haa"));
			} else {
				result = new SimpleStringProperty(startTime.toString("d/MMM/yy  h:mmaa"));
			}
		}
		return result;
	}

	//@author A0133915
	public StringProperty endTimeProperty() {
		// System.out.println("endTime: " + endTime);
		StringProperty result;
		if (this.endTime == null) {
			return new SimpleStringProperty("");
		}
		if (endTime.getYear() == 2015) {
			if(endTime.getMinuteOfHour() == 00) {
				result = new SimpleStringProperty(endTime.toString("d/MMM  haa"));
			} else {
				result = new SimpleStringProperty(endTime.toString("d/MMM  h:mmaa"));
			}
		} else {
			if(endTime.getMinuteOfHour() == 00) {
				result = new SimpleStringProperty(endTime.toString("d/MMM/yy  haa"));
			} else {
				result = new SimpleStringProperty(endTime.toString("d/MMM/yy  h:mmaa"));
			}
		}
		return result;
	}

	//@author A0133915H
	public StringProperty priorityProperty() {
		if (this.priority == null) {
			return new SimpleStringProperty("");
		}
		return new SimpleStringProperty(Integer.toString(this.priority));
	}

	//@author A0133915H
	public SimpleStringProperty taskNumberProperty() {
		if (this.taskNum == null) {
			return new SimpleStringProperty("");
		}
		return new SimpleStringProperty(Integer.toString(this.taskNum));
	}
}
