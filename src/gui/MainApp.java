package gui;

import logic.LogicClass;
import logic.Task;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//@@author A0133915H
public class MainApp extends Application {
	private Stage primaryStage;

	private static ObservableList<Task> taskList;
	private static ObservableList<String> todayTaskList;

	private static Logger logger = Logger.getLogger("MainApp");

	public MainApp() {
	}

	public ObservableList<Task> getTaskData() {
		ArrayList<Task> tasks = LogicClass.getTaskList();
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println(tasks.get(i).getName());
		}
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setTaskNumber(i + 1);
		}
		taskList = FXCollections.observableArrayList(tasks);
		return taskList;
	}

	public ObservableList<String> getTodayTasks() {
		ArrayList<String> todayTasks = LogicClass.getTodayTasks();
		todayTaskList = FXCollections.observableArrayList(todayTasks);
		return todayTaskList;
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			Button btn = new Button();
			btn.setText("Help");
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					indexHelp();
				}
			});

			BorderPane rootLayout = new BorderPane();

			rootLayout.setTop(btn);
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("TaskBomber");

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("TaskView.fxml"));
			AnchorPane taskBomberOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(taskBomberOverview);
			TaskViewController controller = loader.getController();

			controller.setMainApp(this);
			logger.log(Level.INFO, "RootLayout is initiated.");

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			logger.log(Level.INFO, "The view is generated.");

		} catch (Exception e) {
			logger.log(Level.SEVERE, null, e);
		}
	}

	public void indexHelp() {
		Image image = new Image("gui/help/Index.TIF");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		ScrollPane secondaryLayout = new ScrollPane();
		secondaryLayout.setContent(imageView);

		Scene secondScene = new Scene(secondaryLayout, 800, 450);
		Stage secondStage = new Stage();
		secondStage.setTitle("Help");
		secondStage.setScene(secondScene);

		imageView.fitWidthProperty().bind(secondStage.widthProperty());
		// Set position of second window, related to primary window.
		secondStage.setX(primaryStage.getX() + 200);
		secondStage.setY(primaryStage.getY() + 150);

		secondStage.show();
	}

	public void sos() {
		Image image = new Image("gui/help/SOS.TIF");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		ScrollPane thirdLayout = new ScrollPane();
		thirdLayout.setContent(imageView);

		Scene thirdScene = new Scene(thirdLayout, 800, 450);
		Stage thirdStage = new Stage();
		thirdStage.setTitle("SOS");
		thirdStage.setScene(thirdScene);

		imageView.fitWidthProperty().bind(thirdStage.widthProperty());

		// Set position of second window, related to primary window.
		thirdStage.setX(primaryStage.getX() + 200);
		thirdStage.setY(primaryStage.getY() + 150);

		thirdStage.show();
	}

	public void basic() {
		Image image = new Image("gui/help/Basic.TIF");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		ScrollPane fourthLayout = new ScrollPane();
		fourthLayout.setContent(imageView);

		Scene fourthScene = new Scene(fourthLayout, 800, 450);
		Stage fourthStage = new Stage();
		fourthStage.setTitle("Help Basic");
		fourthStage.setScene(fourthScene);

		imageView.fitWidthProperty().bind(fourthStage.widthProperty());

		// Set position of second window, related to primary window.
		fourthStage.setX(primaryStage.getX() + 200);
		fourthStage.setY(primaryStage.getY() + 150);

		fourthStage.show();
	}
	
	public void advance() {
		Image image = new Image("gui/help/Advance.TIF");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		ScrollPane fifthLayout = new ScrollPane();
		fifthLayout.setContent(imageView);

		Scene fifthScene = new Scene(fifthLayout, 800, 450);
		Stage fifthStage = new Stage();
		fifthStage.setTitle("Help Advance");
		fifthStage.setScene(fifthScene);

		imageView.fitWidthProperty().bind(fifthStage.widthProperty());

		// Set position of second window, related to primary window.
		fifthStage.setX(primaryStage.getX() + 200);
		fifthStage.setY(primaryStage.getY() + 150);

		fifthStage.show();
	}

	public void shortForm() {
		Image image = new Image("gui/help/Shortcut.TIF");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		ScrollPane sixthLayout = new ScrollPane();
		sixthLayout.setContent(imageView);

		Scene sixthScene = new Scene(sixthLayout, 900, 600);
		Stage sixthStage = new Stage();
		sixthStage.setTitle("Shortcuts");
		sixthStage.setScene(sixthScene);

		imageView.fitWidthProperty().bind(sixthStage.widthProperty());

		// Set position of second window, related to primary window.
		sixthStage.setX(primaryStage.getX() + 200);
		sixthStage.setY(primaryStage.getY() + 100);

		sixthStage.show();
	}
	
	public void credit() {
		Image image = new Image("gui/help/Credit.TIF");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);

		ScrollPane seventhLayout = new ScrollPane();
		seventhLayout.setContent(imageView);

		Scene seventhScene = new Scene(seventhLayout, 900, 520);
		Stage seventhStage = new Stage();
		seventhStage.setTitle("Shortcuts");
		seventhStage.setScene(seventhScene);

		imageView.fitWidthProperty().bind(seventhStage.widthProperty());

		// Set position of second window, related to primary window.
		seventhStage.setX(primaryStage.getX() + 200);
		seventhStage.setY(primaryStage.getY() + 150);

		seventhStage.show();
	}

    public void exit() {
        Stage stage = getPrimaryStage();
        stage.close();
    }
    
	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		assert (primaryStage != null);
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
