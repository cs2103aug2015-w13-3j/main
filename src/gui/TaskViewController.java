package gui;

import logic.*;
import parser.CommandPackage;
import parser.CommandParser;

import java.util.ArrayList;
import java.util.Stack;
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
	private static Stack<String> pastCommands = new Stack<String>();
	private static Stack<String> poppedCommands = new Stack<String>();

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
		if (poppedCommands.isEmpty()) {
			txtCommandInput.clear();
		} else {
			txtCommandInput.clear();
			String pastCommand = poppedCommands.pop();
			pastCommands.push(pastCommand);
			txtCommandInput.setText(pastCommand);
		}
	}

	private void upKeyEvent() {
		if (pastCommands.isEmpty()) {
			txtCommandInput.clear();
		} else {
			txtCommandInput.clear();
			String pastCommand = pastCommands.pop();
			poppedCommands.push(pastCommand);
			txtCommandInput.setText(pastCommand);
		}
	}

	private void enterKeyEvent() {
		if (txtCommandInput.getText() == null || txtCommandInput.getText().isEmpty()) {
			taskTableView.setItems(mainApp.getTaskData());
		} else {
			String input = txtCommandInput.getText();
			pastCommands.push(input);
			if (input.equalsIgnoreCase("exit")) {
				mainApp.exit();
			} else if (input.equalsIgnoreCase("help")) {
				mainApp.indexHelp();
			} else if (input.equalsIgnoreCase("sos")) {
				mainApp.sos();
			} else if (input.equalsIgnoreCase("basic")
					|| input.equalsIgnoreCase("`hb")) {
				mainApp.basic();
			} else if (input.equalsIgnoreCase("advance")
					|| input.equalsIgnoreCase("`ha")) {
				mainApp.advance();
			} else if (input.equalsIgnoreCase("shortcut")
					|| input.equalsIgnoreCase("shortcuts")
					|| input.equalsIgnoreCase("shortform")
					|| input.equalsIgnoreCase("shortforms")
					|| input.equalsIgnoreCase("`sc")) {
				mainApp.shortForm();
			} else if (input.equalsIgnoreCase("credit")
					|| input.equalsIgnoreCase("`cd")) {
				mainApp.credit();
			} else {
				String inputCommand = txtCommandInput.getText().trim();
				logger.log(Level.INFO, "Here comes a command.");
				execute(inputCommand);
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
