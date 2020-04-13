package ISS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Console.GUI;
import Envoy.HumidIn;
import Envoy.TempIn;

/**
 * Driver
 */
public class Driver {

    private static GUI theGUI;

    private static final File Outside = new File("OutSide.txt");

    private static final File Inside = new File("InSide.txt");

    private Temperature tempLabel;

    private Wind winLabel;

    private Rain rainLabel;

    private Barometer baroLabel;

    private Humidity humiLabel;

    private HumidIn humidIn;

    private TempIn tempIn;

    public static void main(String[] args) {
    	
		
        try {
            theGUI = new GUI();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            new Driver().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * read a line from a txt file, data is split by "," Create a new object of the
     * sensor class file with data.
     * 
     * @param theFile
     * @throws Exception
     */
    private void run() throws Exception {
        try {
            final BufferedReader rdrISS = new BufferedReader(new FileReader(Outside));
            final BufferedReader rdrEnv = new BufferedReader(new FileReader(Inside));
            String dataISS;
            String dataEnv;
            while ((dataISS = rdrISS.readLine()) != null && (dataEnv = rdrEnv.readLine()) != null) {
                final String[] arrDataISS = dataISS.split(",", 7);
                final String[] arrDataEnv = dataEnv.split(",", 3);
                for (int i = 1; i < dataISS.length(); i++) {
                    switch (i) {
                        case 1:
                            winLabel = new Wind(Integer.valueOf(arrDataISS[i]), Integer.valueOf(arrDataISS[i+1]));
                            break;
                        case 3:
                            tempLabel = new Temperature(Double.valueOf(arrDataISS[i])/10);
                            break;
                        case 4:
                            humiLabel = new Humidity(Double.valueOf(arrDataISS[i])/10);
                            break;
                        case 5:
                            baroLabel = new Barometer(Double.valueOf(arrDataISS[i])/10);
                            break;
                        case 6:
                            rainLabel = new Rain(Double.valueOf(arrDataISS[i])/100);
                            break;
                    }
                }
                for (int i = 1; i < dataEnv.length(); i++) {
                    switch (i) {
                        case 1:
                            tempIn = new TempIn(Double.valueOf(arrDataEnv[i])/10);
                            break;
                        case 2:
                            humidIn = new HumidIn(Double.valueOf(arrDataEnv[i])/10);
                    }
                }               
                theGUI.updateDisplay(baroLabel, humiLabel, rainLabel, tempLabel, winLabel, humidIn, tempIn);
                synchronized (this) {
                    this.wait(20);
                }
            }
            rdrISS.close();
            rdrEnv.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public Temperature getTempLabel() {
        return tempLabel;
    }

    public Wind getWinLabel() {
        return winLabel;
    }
    public Rain getRainLabel() {
        return rainLabel;
    }
    public Barometer getBaroLabel() {
        return baroLabel;
    }
    public Humidity getHumiLabel() {
        return humiLabel;
    }

}