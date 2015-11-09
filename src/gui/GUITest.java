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

import javafx.scene.control.TextField;

//import com.sun.javafx.robot.FXRobot;
//import com.sun.javafx.robot.FXRobotImage;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

public class GUITest extends FxRobot{
	
	private static MainApp mainApp;
	private static TaskViewController controller;
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
		controller = mainApp.getController();
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
	
	
}
