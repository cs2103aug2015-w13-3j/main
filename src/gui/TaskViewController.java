package gui;

import logic.*;

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

	//private Storage storage = new Storage();

	//private Logic logic = new Logic();

	@FXML
	private void initialize() {

		//taskNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskNumberProperty());
		taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
		startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
		endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
		priority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
		
	}

	public void enterCommand(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String input = txtCommandInput.getText();
			System.out.println(input);
			// if (input != null) {
			// CommandParser cmdParser = new CommandParser();
			// CommandPackage cmdPack = cmdParser.getCommandPackage(input);
			// logic.executeCommand(cmdPack);
			// txtCommandInput.clear();
			// }
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		// taskTableView.setItems(mainApp.getTaskData());
	}
}
