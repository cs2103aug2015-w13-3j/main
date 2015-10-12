package gui;

import logic.*; 

import javafx.collections.ObservableList;
import java.io.IOException;

import org.joda.time.DateTime;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;

	public static ObservableList<Task> taskList = FXCollections.observableArrayList();
	
	public MainApp(){
		DateTime time1 = new DateTime(2015, 10, 12, 10, 00);
		DateTime time2 = new DateTime(2015, 10, 12, 12, 00);
		DateTime time3 = new DateTime(2015, 10, 03, 00, 00);
		DateTime time4 = new DateTime(2015, 10, 23, 23,59);
		DateTime time5 = new DateTime(2015, 10, 12, 14, 00);
		taskList.add(new Task("meeting", time1, time2, 2));
		taskList.add(new Task("SEP", time3, time4, 1));
		taskList.add(new Task("Singtel Workshop", time5, null, 3));
	}

	public ObservableList<Task> getTaskData() {
		return taskList;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TaskBomber");
        
        initRootLayout();

        showTaskBomberOverview();
	}
	
	  /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showTaskBomberOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("TaskView.fxml"));
            AnchorPane taskBomberOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(taskBomberOverview);
            
            TaskViewController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	public static void main(String[] args) {
        launch(args);
    }

}
