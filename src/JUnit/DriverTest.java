package JUnit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ISS.Driver;

class DriverTest extends Driver {
	Driver test = new Driver();
	Driver testb = new Driver();
	
	
	@Test
	void testDebug() {
		test.debug();
		assertNotEquals(test, testb, "whatcha doin");
	}
}
