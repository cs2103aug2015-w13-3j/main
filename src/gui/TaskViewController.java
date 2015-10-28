package gui;

import logic.*;
import parser.CommandPackage;
import parser.CommandParser;
import storage.Storage;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TaskViewController {

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

	// Reference to the main application.
	private MainApp mainApp;

	private Storage storage = new Storage();

	private LogicClass logic = LogicClass.getInstance(storage);
	
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
			String input = txtCommandInput.getText().trim();
			System.out.println("input: " + input);
			if (input != null && input != "") {
				logger.log(Level.INFO, "Here comes a command.");
				CommandPackage cmdPack = cmdParser.getCommandPackage(input);
				logger.log(Level.INFO, "CommandParser parses the command.");
				assert (cmdPack != null);
				logic.executeCommand(cmdPack);
				logger.log(Level.INFO, "Logic executes the command.");
				
				// every time when user click "enter", redisplay the task list
				taskTableView.setItems(mainApp.getTaskData());
				logger.log(Level.INFO, "Update the table view.");
				txtCommandInput.clear();
			}
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */

	public void setMainApp(MainApp mainApp) {
		System.out.println("set Main app.");
		this.mainApp = mainApp;
		logger.log(Level.INFO, "Set MainApp.");
		// Add observable list data to the table
		taskTableView.setItems(mainApp.getTaskData());
	}
}
