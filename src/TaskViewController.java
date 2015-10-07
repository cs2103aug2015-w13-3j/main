
import org.joda.time.DateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class TaskViewController {

	@FXML
	private TextField txtCommandInput;

	@FXML
	private TableView<TempoTask> taskTableView;
	@FXML
	private TableColumn<TempoTask, Integer> taskNumberColumn;
	@FXML
	private TableColumn<TempoTask, String> taskNameColumn;
	@FXML
	private TableColumn<TempoTask, String> startTimeColumn;
	@FXML
	private TableColumn<TempoTask, String> endTimeColumn;
	@FXML
	private TableColumn<TempoTask, Integer> priority;

	// Reference to the main application.
	private MainApp mainApp;

	private Logic logic = new Logic();

	@FXML
	private void initialize() {

		taskNameColumn.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<String, String> p) {
				return new SimpleStringProperty(p.getValue());
			}
		});

		startTimeColumn.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<String, String> p) {
				return new SimpleStringProperty(p.getValue());
			}
		});

		endTimeColumn.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<String, String> p) {
				return new SimpleStringProperty(p.getValue());
			}
		});

		priority.setCellValueFactory(new PropertyValueFactory<TempoTask,String>("pri"));
	}

	public void enterCommand(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String input = txtCommandInput.getText();
			System.out.println(input);
			if (input != null) {
				CommandParser cmdParser = new CommandParser();
				CommandPackage cmdPack = cmdParser.getCommandPackage(input);
				logic.executeCommand(cmdPack);
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
		this.mainApp = mainApp;
		taskTableView.setItems(mainApp.getTaskData());
	}
}
