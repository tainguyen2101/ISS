package ISS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Console.GUI;

/**
 * Driver
 */
public class Driver {

    //private static final File test = new File("C:\\Users\\taing\\Desktop\\Java\\OutSide.txt");

    private static final File testMac = new File("/Users/taing/Desktop/TCSS360-Project1/TCSS360-Project1-ISS/OutSide.txt");

    private Temperature tempLabel;

    private Wind winLabel;

    private Rain rainLabel;

    private Barometer baroLabel;

    private Humidity humiLabel;

    private static GUI theGUI;

   //private ArrayList<Integer> storage = new ArrayList<>();


    public static void main(String[] args) {
        try {
            theGUI = new GUI();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            new Driver().run(testMac);
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
    private void run(final File theFile) throws Exception {
        try {
            final BufferedReader rdr = new BufferedReader(new FileReader(theFile));
            String data;
            while ((data = rdr.readLine()) != null) {
                final String[] arrData = data.split(",", 7);
                for (int i = 1; i < data.length(); i++) {
                    switch (i) {
                        case 1:
                            winLabel = new Wind(Integer.valueOf(arrData[i]), Integer.valueOf(arrData[i+1]));
                            break;
                        case 3:
                            tempLabel = new Temperature(Double.valueOf(arrData[i])/10);
                            break;
                        case 4:
                            humiLabel = new Humidity(Double.valueOf(arrData[i])/10);
                            break;
                        case 5:
                            baroLabel = new Barometer(Double.valueOf(arrData[i])/10);
                            break;
                        case 6:
                            rainLabel = new Rain(Double.valueOf(arrData[i])/100);
                            break;
                    }
                }
                theGUI.updateDisplay(baroLabel, humiLabel, rainLabel, tempLabel, winLabel);
                synchronized (this) {
                    this.wait(3*1000);
                }
            }
            rdr.close();
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