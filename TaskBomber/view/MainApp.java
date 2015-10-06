package TaskBomber.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TaskView.fxml"));
	    
        Scene scene = new Scene(root, 600, 500);
        
        stage.setTitle("TaskBomber");
        stage.setScene(scene);
        stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
