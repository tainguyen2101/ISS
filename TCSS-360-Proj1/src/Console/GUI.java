package Console;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import ISS.*;


public class GUI {

    private final JFrame myFrame;

    private static final Toolkit TOOL = Toolkit.getDefaultToolkit();

    private static final Dimension SCREEN_SIZE = TOOL.getScreenSize();

    private JPanel myDisplay;

    private final ArrayList<JButton> myButton;

    private JTextArea myTemp;
    
    private JTextArea myBaro;

    private JTextArea myRain;

    private JTextArea myCompass;

    private JTextArea myGraph;

    private JTextArea myHumid;

    

    public GUI() throws Exception {
        super();
        myFrame = new JFrame("ISS");
        myButton = new ArrayList<JButton>();
        setUp();
    }

    /**
     * 
     * @throws Exception
     */
    private void setUp() throws Exception {
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add(createPanel());
        //myFrame.pack();
        myFrame.setMinimumSize(myFrame.getPreferredSize());
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                (int) (SCREEN_SIZE.getHeight() / 2 - myFrame.getHeight() / 2));
        myFrame.setVisible(true);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    private JPanel createPanel() throws Exception {
        final JPanel mainPanel = new JPanel(new BorderLayout());
        final JPanel buttonPanel = createButtonPanel();
        myDisplay = createDisplay();
        mainPanel.add(myDisplay, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        return mainPanel;
    }

    private JPanel createButtonPanel() {
        final JPanel buttonPanel = new JPanel(new GridLayout(6,2)); 
        myButton.add(new JButton("Temp"));
        myButton.add(new JButton("2ND"));
        myButton.add(new JButton("Hum"));
        myButton.add(new JButton("Forecast"));
        myButton.add(new JButton("Wind"));
        myButton.add(new JButton("Graph"));
        myButton.add(new JButton("RainDay"));
        myButton.add(new JButton("Hi/Low"));
        myButton.add(new JButton("RainYr"));
        myButton.add(new JButton("Alarm"));
        myButton.add(new JButton("Bar"));
        myButton.add(new JButton("Done"));
        for (int i = 0; i < myButton.size(); i++) {
            buttonPanel.add(myButton.get(i));
        }
        return buttonPanel;
    }

    private JPanel createDisplay() {

        final JPanel theDisplay = new JPanel();
        myTemp = new JTextArea();
        myBaro = new JTextArea();
        myHumid = new JTextArea();
        myRain = new JTextArea();
        myCompass = new JTextArea();
        myGraph = new JTextArea();

        theDisplay.setLayout(new GridLayout(3, 2));
        theDisplay.add(myCompass);
        theDisplay.add(myTemp);
        theDisplay.add(myBaro);
        theDisplay.add(myHumid);
        theDisplay.add(myRain);
        theDisplay.add(myGraph);

        return theDisplay;
    }

    public void updateDisplay(final Barometer theBaro, final Humidity theHumid, final Rain theRain,
            final Temperature theTemp, final Wind theWind) {
        
        myTemp.setText("TEMPERATURE\n " + theTemp.getMyTemp() );
        myTemp.setSize(myTemp.getPreferredSize());
        myBaro.setText("BAROMETER\n " + theBaro.getMyBaroPressure());
        myHumid.setText("HUMIDITY\n " + theHumid.getMyHumid());
        myRain.setText("RAIN RATE\n " + theRain.getMyRainRate());
        myCompass.setText(theWind.getMyCompass());
        myCompass.setSize(myCompass.getPreferredSize());
        //myGraph.setText(theGraph);
        //myGraph.setSize(myGraph.getPreferredSize());
        myDisplay.repaint();
        myFrame.setMinimumSize(myFrame.getPreferredSize());
        myFrame.repaint();
    }

    
    
}