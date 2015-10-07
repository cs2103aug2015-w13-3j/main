
import org.joda.time.DateTime;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TaskViewController implements Initializable {
	
	@FXML 
	private TextField txtCommandInput;
	
	@FXML
    private TableView<Task> taskTableView;
    @FXML
    private TableColumn<Task, Integer> taskNumberColumn;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, DateTime> startTimeColumn;
    @FXML
    private TableColumn<Task, DateTime> endTimeColumn;
    @FXML
    private TableColumn<Task, Integer> priorityColumn;
    
    @FXML
    private TextField textField;

    // Reference to the main application.
    private MainApp mainApp;

	@FXML 
	private void initialize() {
		taskNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskIdProperty());
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());
    }
	private void passCommand(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			System.out.println(txtCommandInput.getText());
			//TaskBomber.setUserCommand(txtCommandInput.getText());
			//CommandParser cmdParser = CommandParser.getInstance();
			//Command cmd = cmdParser.getCommand(txtCommandInput.getText());
			//cmd.execute();
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}

