package TaskBomber.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TaskViewController implements Initializable {
	
	@FXML 
	private TextField txtCommandInput;
	
	@FXML 
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
