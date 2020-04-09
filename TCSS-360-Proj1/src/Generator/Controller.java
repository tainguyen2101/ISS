package Generator;

public class Controller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomSensorDataGenerator generate = new RandomSensorDataGenerator();
		generate.createISSData();
		generate.createEnvoyData();
		generate.printDataToConsole();
		
		
	}

}
