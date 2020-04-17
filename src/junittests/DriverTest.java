package junittests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ISS.Driver;
import ISS.Wind;

class DriverTest extends Driver {
	Driver test = new Driver();
	Driver testb = new Driver();
	
	
	@Test
	void testDebug() {
		test.debug();
		assertNotEquals(test, testb, "whatcha doin");
	}
}
