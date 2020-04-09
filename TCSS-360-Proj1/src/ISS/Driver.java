package ISS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//import java.util.ArrayList;

import Console.GUI;

/**
 * Driver
 */
public class Driver {

    private static final File test = new File("C:\\Users\\taing\\Desktop\\Java\\OutSide.txt");

    private Temperature tempLabel;

    private Wind winLabel;

    private Rain rainLabel;

    private Barometer baroLabel;

    private Humidity humiLabel;

    private static GUI theGUI;

    public static void main(String[] args) {
        try {
            theGUI = new GUI();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            new Driver().run(test);
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
                    this.wait(5*1000);
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
    /*
    private String makeGraph(double [] data) {
        int max = 25;
        int min = 0;
        double multiple = 100/(max-min);
        int height = 10;
        int length = 24;
        double averageChange = ((100)/height);
        String graph = "";
    
    
        for (int i=0; i<height+4; i++) {
    
            for(int k =0; k<length+1; k++) {
    
                if( k!=0 && (int)(((data[k-1]-(min))*multiple)/averageChange)==height-i ) {
                    if(k==length) {
                        graph=graph+" o |";
                    }
                    else {
                        graph=graph+" o";
                    }
                }
                else if(i==0) {
    
                    if(k==0 ) {
                        graph=graph+"  ";
                    }
                    else if(k==length ) {
                        graph=graph+"___";
                    }
                    else {
                        graph=graph+"__";
                    }
                }
                else if(k==0 && i<height+2) {
                    graph=graph+"| ";
                }
                else if(k==length && i<height+2) {
    
                    if(i==height+1) {
                        graph=graph+" |";
                        graph=graph+"\n Vertical Scale \t\t  X10";
                    }
                    else {
                        graph=graph+"   |";
    
                    }
                }
                else if(i==height+1) {
                    if (k==0) {
                        graph=graph+"  ";
                    }
                    else if(k==length-1) {
                        graph=graph+"____";
    
                    }
                    else {
                        graph=graph+"__";
                    }
                }
                else {
    
                    graph=graph+"  ";
                }
            }
            graph=graph+"\n";
        }
    
        return graph;
    }*/

}