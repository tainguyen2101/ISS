package junittests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ISS.Driver;
import ISS.Wind;

class DriverTest extends Driver {
	Driver test = new Driver();
	
	
	@Test
	void testDebug() {
		assertEquals(1, test.debug(), "GUI not created successfully");
	}
}
