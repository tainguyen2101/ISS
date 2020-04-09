package Generator;


import java.io.File;

import java.io.FileNotFoundException;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;


/*
 * Random Generator creates two list containing randomized sensor data. The text file OutSide.txt contains ISS sensor Data 
 * in the order of 			
 * 
 * OutSide.txt <--------> [mywindDirection, myWindSpeed, myTempOut, myHumOut, myBarometer, myRainRate]
 * 
 * The second list is named InSide.txt which contain the Envoy sensor data in the order of 
 * 
 * InSide.txt  <--------> [myTempIn, HumIn]
 * 
 */
public class RandomSensorDataGenerator {
	static PrintStream printout;
	static PrintStream printin;
	static Random random = new Random();


	//// Outside data
	static int 		intervals = 200;   /// Interval is how many data point the Program prints to lists and represents minutes. 2400 hrs = 1 day of data.
	static double 	milTime;  			/// Range 0-2400   	military Time (module 2400) 
	static int 		mywindDirection;  	/// Range 0-360 	Degree Azimuth 
	static int 		myWindSpeed;     	/// Range 0-25 		miles per hour
	static int 		myTempOut;      	/// Range 0-1400  	divide by 10 to get degrees in F. Ex the value 754/10 = 74.5 F
	static int 		myHumOut;       	/// Range 0-1000 	divide by 10 to get %. Ex the value 754/10 = 74.5% Humidity
	static int 		myBarometer;    	/// Range 259 -320 	Divide by 10 to get  hPa
	static int 		myRainRate;       	/// Range 0-2000 	Divide by 100 to get inches per hour. Ex 223/100 = 2.23 inches of rain per hour.

	//// Inside data
	static int 		myTempIn ;			/// Range 0-1400  	divide by 10 to get degrees in F. Ex the value 754/10 = 74.5 F
	static int 		HumIn;				/// Range 0-1000 	divide by 10 to get %. Ex the value 754/10 = 74.5% Humidity


	public void setInitialOutValues() {
		milTime = 1200;     	/// Range 0-2400   military Time (module 2401)
		mywindDirection=5;  	/// Range 0-360 		degree Azimuth 
		myWindSpeed =5;     	/// Range 0-25 		miles per hour
		myTempOut=700;      	/// Range 0-1400  	divide by 10 to get degrees in F
		myHumOut=600;        	/// Range 0-1000 	divide by 10 to get %. Ex the value 754/10 = 74.5% Humidity
		myBarometer=300;    	/// Range 259 -320 	Divide by 10 to get  hPa
		myRainRate=100;   	 	/// Range 0-2000 	Divide by 100 to get inches per hour
	}

	public void setInitialInValues() {
		milTime = 1200;  
		myTempIn = 800;			/// Range 0-1400  	divide by 10 to get degrees in F. Ex the value 754/10 = 74.5 F
		HumIn = 600;			/// Range 0-1000	divide by 10 to get %. Ex the value 754/10 = 74.5% Humidity

	}

	public void createISSData() {   //// createISSData method writes Randomized ISS Sensor Data into OutSide.txt

		setInitialOutValues();

		try {
			printout = new PrintStream("OutSide.txt");
			printin = new PrintStream("InSide.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for(int i =0; i<intervals ; i++) {

			printout.print((int) (milTime)+",");

			int windDirectionOffset=random.ints(-5,(5)).findFirst().getAsInt(); //Wind direction offset plus minus 5
			mywindDirection = (360+mywindDirection+windDirectionOffset)%360;
			printout.print(mywindDirection+",");


			int windSpeedOffset=random.ints(-1,(2)).findFirst().getAsInt(); 	//Wind speed offset plus minus 1
			myWindSpeed = (Math.abs(myWindSpeed+windSpeedOffset))%25;
			printout.print(myWindSpeed+",");


			int tempOffset=random.ints(-5,(5)).findFirst().getAsInt(); 			// Temp Offset plus minus 1
			myTempOut = (int)((45*(Math.sin(1200/milTime)+1))*10+tempOffset);  // OutSide temp Cyclic
			printout.print(myTempOut+",");


			int humOutOffset = random.ints(-1,(2)).findFirst().getAsInt();     // humidity Offset
			myHumOut =Math.abs(myHumOut +humOutOffset);
			if(myHumOut>1000) {
				myHumOut=myHumOut-10;
			}
			printout.print(myHumOut+",");


			int BarometerOffset=random.ints(-1,(2)).findFirst().getAsInt();		// Barometer Offset
			myBarometer=myBarometer +BarometerOffset;							
			if(myBarometer<259) {
				myBarometer=myBarometer+10;
			}
			if(myBarometer>320) {
				myBarometer=myBarometer-10;
			}
			printout.print(myBarometer+",");


			int rainRateOffset=random.ints(-1,(2)).findFirst().getAsInt(); // Temp Offset plus minus 1
			myRainRate = myRainRate+rainRateOffset;  // OutSide temp Cyclic
			printout.print(myRainRate+"\n");



			milTime=(milTime+1)%2400;
		}
	}




	public void createEnvoyData() {		 //// createEnvoyData method writes Randomized Envoy Sensor Data into InSide.txt
		setInitialInValues();


		try {
			printin = new PrintStream("InSide.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for(int i =0; i<intervals ; i++) {
			printin.print((int) (milTime)+",");
			myTempIn = (int)((45*(Math.sin(1200/(milTime*.3+800))+1))*10)-30;  // InSide temp Cyclic
			printin.print(myTempIn+",");


			int humOutOffset = random.ints(-1,(2)).findFirst().getAsInt();
			myHumOut =Math.abs(myHumOut +humOutOffset);
			if(myHumOut>1000) {
				myHumOut=myHumOut-2;
			}
			printin.print(myHumOut+"\n");

			milTime=(milTime+1)%2400;
		}
	}


	public void printDataToConsole() {
		File file = new File("OutSide.txt");
		Scanner inFile=null;
		System.out.println("ISS Sensor Data");
		System.out.println("Time\t\tWindDirection\t\tWindSpeed\t\tTempOut\t\tHumOut\t\tBarometer\t\tRainRate");

		try {
			inFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for(int i =0; i<intervals; i++) {
			String[] data =  inFile.nextLine().split(",");

			System.out.println(data[0]+"\t\t"+data[1]+" degrees\t\t"+ data[2]+" mph\t\t\t"+
					Double.parseDouble(data[3])/10+" F\t\t" +Double.parseDouble(data[4])/10+" %\t\t" +
					Double.parseDouble(data[5])/10+" hPa\t\t" +Double.parseDouble(data[6])/100+" in/hr");
		}


	}







}