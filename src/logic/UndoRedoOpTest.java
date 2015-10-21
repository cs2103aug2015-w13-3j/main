//package logic;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class UndoRedoOpTest {
//
//	static ArrayList<Task> expected = new ArrayList<Task>();
//	
//	static ArrayList<Task> initial = new ArrayList<Task>();
//	
//	@BeforeClass
//	public static void oneTimeSetUp(){
//		
//	}
//	
//	@Before
//	public void setUp(){
//		expected.clear();
//		assertEquals("all content deleted from a.txt",
//				TextBuddy.executeCommand("clear"));
//		assertEquals(0, TextBuddy.getLineCount());
//	}
//	
//	@After
//	public void tearDown(){
//		expected.clear();
//		assertEquals("all content deleted from a.txt",
//				TextBuddy.executeCommand("clear"));
//		assertEquals(0, TextBuddy.getLineCount());
//	}
//	
//	@Test
//	public void originalFunctionalityTest() {
//
//		assertEquals("added to a.txt: \"I love CS!!!\"",
//				TextBuddy.executeCommand("add I love CS!!!"));
//
//		String expected = "1. I love CS!!!";
//		assertEquals(expected, TextBuddy.executeCommand("display"));
//
//		assertEquals("deleted from a.txt: \"I love CS!!!\"",
//				TextBuddy.executeCommand("delete 1"));
//
//		assertEquals(0, TextBuddy.getLineCount());
//	}
//
//	@Test
//	public void sortTest() {
//
//		assertEquals("added to a.txt: \"I love CS!!!\"",
//				TextBuddy.executeCommand("add I love CS!!!"));
//
//		assertEquals("added to a.txt: \"You love CS!!!\"",
//				TextBuddy.executeCommand("add You love CS!!!"));
//
//		assertEquals("added to a.txt: \"He loves CS!!!\"",
//				TextBuddy.executeCommand("add He loves CS!!!"));
//
//		assertEquals("Successully sorted", TextBuddy.executeCommand("sort"));
//
//		String expected = "1. He loves CS!!!" + System.lineSeparator()
//				+ "2. I love CS!!!" + System.lineSeparator()
//				+ "3. You love CS!!!";
//		assertEquals(expected, TextBuddy.executeCommand("display"));
//
//		assertEquals("added to a.txt: \"Boom Shakalaka\"",
//				TextBuddy.executeCommand("add Boom Shakalaka"));
//
//		String expected2 = "1. Boom Shakalaka" + System.lineSeparator()
//				+ "2. He loves CS!!!" + System.lineSeparator()
//				+ "3. I love CS!!!" + System.lineSeparator()
//				+ "4. You love CS!!!";
//
//		assertEquals("Successully sorted", TextBuddy.executeCommand("sort"));
//
//		assertEquals(expected2, TextBuddy.executeCommand("display"));
//	}
//
//	@Test 
//	public void sortTest2(){
//		expected.add("A" + System.lineSeparator());
//		expected.add("B" + System.lineSeparator());
//		expected.add("C" + System.lineSeparator());
//		expected.add("D" + System.lineSeparator());
//		expected.add("E" + System.lineSeparator());
//		TextBuddy.executeCommand("add A");
//		TextBuddy.executeCommand("add C");
//		TextBuddy.executeCommand("add E");
//		TextBuddy.executeCommand("add B");
//		TextBuddy.executeCommand("add D");
//		TextBuddy.executeCommand("sort");
//		assertEquals(expected, TextBuddy.getLine());
//	}
//	
//	@Test
//	public void searchTest() {
//
//		assertEquals("added to a.txt: \"I love CS!!!\"",
//				TextBuddy.executeCommand("add I love CS!!!"));
//
//		assertEquals("added to a.txt: \"You love CS!!!\"",
//				TextBuddy.executeCommand("add You love CS!!!"));
//
//		assertEquals("added to a.txt: \"He loves CS!!!\"",
//				TextBuddy.executeCommand("add He loves CS!!!"));
//		
//		assertEquals("added to a.txt: \"Boom Shakalaka\"",
//				TextBuddy.executeCommand("add Boom Shakalaka"));
//		
//		String expected = "1. I love CS!!!" + System.lineSeparator()
//				+ "2. You love CS!!!" + System.lineSeparator()
//				+ "3. He loves CS!!!" + System.lineSeparator();
//
//		assertEquals(expected, TextBuddy.executeCommand("search love"));
//	}
//}
//
//
//}
