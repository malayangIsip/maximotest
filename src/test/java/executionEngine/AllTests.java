package executionEngine;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import utility.*;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {
    String message = "Hello World";	
    MessageUtils messageUtil = new MessageUtils(message);

	@Test
	public void testPrintMessage() {	  
	    assertEquals(message,messageUtil.printMessage());
	}
}
