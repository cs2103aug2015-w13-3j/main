package gui;

import logic.Logic;
import logic.Task;

import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.ArrayList;

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

	private static ObservableList<Task> taskList;

	public MainApp() {
	}

	public ObservableList<Task> getTaskData() {
		ArrayList<Task> tasks = Logic.getTaskList();
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setTaskNumber(i + 1);
		}
		taskList = FXCollections.observableArrayList(tasks);
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
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		System.out.println("get Primary Stage.");
		return primaryStage;
	}

	public static void main(String[] args) {
		System.out.println("Launch main.");
		launch(args);
	}

}
