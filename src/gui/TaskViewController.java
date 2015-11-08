package gui;

import logic.*;
import parser.CommandPackage;
import parser.CommandParser;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

//@@author A0133915H
public class TaskViewController {

	private final KeyCombination crtlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
	private final KeyCombination crtlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

	@FXML
	private TextField txtCommandInput;

	@FXML
	private TableView<Task> taskTableView;
	@FXML
	private TableColumn<Task, String> taskNumberColumn;
	@FXML
	private TableColumn<Task, String> taskNameColumn;
	@FXML
	private TableColumn<Task, String> startTimeColumn;
	@FXML
	private TableColumn<Task, String> endTimeColumn;
	@FXML
	private TableColumn<Task, String> priority;

	@FXML
	private ListView<String> todayTasksView;

	ArrayList<String> todayTasks = new ArrayList<String>();

	// Reference to the main application.
	private MainApp mainApp;

	private LogicClass logic = LogicClass.getInstance();

	CommandParser cmdParser = new CommandParser();

	private static Logger logger = Logger.getLogger("TaskViewController");

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public TaskViewController() {
	}

	@FXML
	private void initialize() {
		// Initialize the task table with the five columns.
		taskNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskNumberProperty());
		taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
		startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
		endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
		priority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
	}

	public void enterCommand(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			enterKeyEvent();
		} else if (event.getCode() == KeyCode.UP) {
			upKeyEvent();
		} else if (event.getCode() == KeyCode.DOWN) {
			downKeyEvent();
		} else if (crtlZ.match(event)) {
			controlZKeyEvent();
		} else if (crtlY.match(event)) {
			controlYKeyEvent();
		}
	}

	private void controlYKeyEvent() {
		execute("redo");
	}

	private void controlZKeyEvent() {
		execute("undo");
	}

	private void downKeyEvent() {
		// TODO Auto-generated method stub

	}

	private void upKeyEvent() {
		// TODO Auto-generated method stub

	}

	private void enterKeyEvent() {
		if (txtCommandInput.getText() == null || txtCommandInput.getText().isEmpty()) {
			taskTableView.setItems(mainApp.getTaskData());
		} else {
			if (txtCommandInput.getText().equalsIgnoreCase("help")) {
				mainApp.indexHelp();
			} else if (txtCommandInput.getText().equalsIgnoreCase("sos")) {
				mainApp.sos();
			} else {
				String input = txtCommandInput.getText().trim();
				logger.log(Level.INFO, "Here comes a command.");
				execute(input);
			}
			txtCommandInput.clear();
		}
	}

	private void execute(String input) {
		CommandPackage cmdPack = cmdParser.getCommandPackage(input);
		logger.log(Level.INFO, "CommandParser parses the command.");
		assert (cmdPack != null);
		logic.executeCommand(cmdPack);
		taskTableView.setItems(mainApp.getTaskData());
		logger.log(Level.INFO, "Update the table view.");
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		logger.log(Level.INFO, "Set MainApp.");
		// Add observable list data to the table
		taskTableView.setItems(mainApp.getTaskData());
	}
}
