import javafx.collections.ObservableList;
import java.io.IOException;
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

	public static ObservableList<TempoTask> taskList = FXCollections.observableArrayList(
			new TempoTask(1, "Meeting", "9/10/2015", "10/10/2015", 1),
			new TempoTask(2, "Watch Movie", "9/10/2015 7pm", "9/10/2015 9pm", 3),
			new TempoTask(3, "SEP", "3/10/2015", "23/10/2015", 2),
			new TempoTask(4, "Assignment 2", "15/10/2015 2359", null, 4)
			);
	
	public ObservableList<TempoTask> getTaskData() {
		return taskList;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TaskBomber");
        
        initRootLayout();

        showTaskList();

	}
	 /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
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

    public void showTaskList() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("TaskView.fxml"));
            AnchorPane taskieOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(taskieOverview);
            
            TaskViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) {
        launch(args);
    }

}
