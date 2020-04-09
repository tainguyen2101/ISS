package Generator;

public class Controller {

	public static void main(String[] args) {
		RandomSensorDataGenerator generate = new RandomSensorDataGenerator();
		generate.createISSData();
		generate.createEnvoyData();
		generate.printDataToConsole();
		
		
	}

}
