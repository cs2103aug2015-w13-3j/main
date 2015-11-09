package gui;


import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
//import org.fxmisc.richtext.InlineCssTextArea;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

//import com.sun.javafx.robot.FXRobot;
//import com.sun.javafx.robot.FXRobotImage;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Task;

import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

public class GUITest extends FxRobot{
	
	private static MainApp mainApp;
	public static Stage primaryStage;
	
	//Setting up
	@BeforeClass
	public static void setUpClass() throws Exception {
		primaryStage = FxToolkit.registerPrimaryStage();
	}
	
	@AfterClass
	public static void tearDownClass() {
		
	}	
	
	@Before
	public void before() {
		try {
		mainApp = (MainApp)FxToolkit.setupApplication(MainApp.class);
		WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS, primaryStage.showingProperty());
		sleep(2000);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
		
	@After
    public void after() throws TimeoutException {
        FxToolkit.cleanupStages();
        FxToolkit.hideStage();
    }
	
	@Test
	public void keyValidEnterTest() {
		TextField commandInput= (TextField) mainApp.getScene().lookup("#txtCommandInput");
   		clickOn("#txtCommandInput").write("add meeting 9-Nov-2016").push(KeyCode.ENTER);
   		assertTrue(commandInput.getText().equals("")); 		
	}
	
	@Test
	public void keyInvalidEnterTest(){
		Text feedback = (Text) mainApp.getScene().lookup("#feedback");
		clickOn("#txtCommandInput").write("abcabc").push(KeyCode.ENTER);
		assertTrue(feedback.getText().equals("Invalid command. Please type \"help\" for more instructions."));
	}
	
	@Test
	public void keyInvalidPriorityEnterTest(){
		Text feedback = (Text) mainApp.getScene().lookup("#feedback");
		clickOn("#txtCommandInput").write("add meeting #6").push(KeyCode.ENTER);
		assertTrue(feedback.getText().equals("Invalid priority.Priority is valid from 1 to 3"));
	}
	
	@Test
	public void keyInvalidIndexDeleteEnterTest(){
		Text feedback = (Text) mainApp.getScene().lookup("#feedback");
		clickOn("#txtCommandInput").write("delete 100").push(KeyCode.ENTER);
		assertTrue(feedback.getText().equals("Task not found"));
	}
	
	@Test
	public void keyInvalidNameDeleteEnterTest(){
		Text feedback = (Text) mainApp.getScene().lookup("#feedback");
		clickOn("#txtCommandInput").write("delete party").push(KeyCode.ENTER);
		assertTrue(feedback.getText().equals("Task not found"));
	}
	
	@Test
	public void keyInvalidSearchDeleteEnterTest(){
		Text feedback = (Text) mainApp.getScene().lookup("#feedback");
		clickOn("#txtCommandInput").write("search meeting").push(KeyCode.ENTER);
		clickOn("#txtCommandInput").write("delete party").push(KeyCode.ENTER);
		assertTrue(feedback.getText().equals("Task not found in searched list. To return to full list,press Enter"));
	}
	
	@Test
	public void keyClearEnterTest(){
		Text feedback = (Text) mainApp.getScene().lookup("#feedback");
		clickOn("#txtCommandInput").write("clear").push(KeyCode.ENTER);
		assertTrue(feedback.getText().equals("All tasks cleared"));
	}

	
//	@Test
//	public void tableViewContentTest(){
//		TableView<Task> tableView = (TableView<Task>) mainApp.getScene().lookup("#taskTableView");
//		clickOn("#txtCommandInput").push(KeyCode.ENTER);
//		ObservableList<Task> expectedList = mainApp.getTaskData();
//		assertTrue(tableView.getColumns().equals(expectedList));
//	}
	
	
}
