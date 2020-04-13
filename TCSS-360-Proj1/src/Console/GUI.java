package Console;

import ISS.*;
import Envoy.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.geom.Ellipse2D;

public class GUI {

    private static final Toolkit TOOL = Toolkit.getDefaultToolkit();

    private static final Dimension SCREEN_SIZE = TOOL.getScreenSize();

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd");

    private final JFrame myFrame;

    private LocalDateTime time, date;

    private JPanel myDisplay, tempInfo, graphInfo;

    private final ArrayList<JButton> myButton;

    private JTextArea myDate, myTime, myTemp, myBaro, myRain, myHumid, myTempIn, myHumidIn, myChill;

    private JPanel myGraph, myCompass;

    private final ArrayList<Integer> storage;

    public GUI() throws Exception {
        super();
        storage = new ArrayList<>();
        myFrame = new JFrame("ISS");
        graphInfo = new JPanel();
        tempInfo = new JPanel();
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
        myFrame.setMinimumSize(myFrame.getPreferredSize());
        myFrame.setLocation(SCREEN_SIZE.width / 8, (int) (SCREEN_SIZE.getHeight() / 8));
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
        final JPanel buttonPanel = new JPanel(new GridLayout(6, 2));
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

        time = LocalDateTime.now();
        date = LocalDateTime.now();
        final JPanel theDisplay = new JPanel();

        graphInfo.setLayout(new BorderLayout());   
        tempInfo.setLayout(new GridLayout(3,3));
        theDisplay.setLayout(new BorderLayout());

        myTemp = new JTextArea();
        myBaro = new JTextArea();
        myHumid = new JTextArea();
        myRain = new JTextArea();
        myTempIn = new JTextArea();
        myHumidIn = new JTextArea();
        myChill = new JTextArea();
        myTime = new JTextArea(TIME_FORMAT.format(time));
        myDate = new JTextArea(DATE_FORMAT.format(date));

        
        tempInfo.add(myDate);
        tempInfo.add(myTime);
        tempInfo.add(myTemp);
        tempInfo.add(myBaro);
        tempInfo.add(myHumid);
        tempInfo.add(myTempIn);
        tempInfo.add(myHumidIn);
        tempInfo.add(myChill);
        tempInfo.add(myRain);


        myCompass = new makeCompass(0);
        myGraph = new makeGraphTemp();

        graphInfo.add(myCompass, BorderLayout.NORTH);
        graphInfo.add(myGraph, BorderLayout.SOUTH);
        
        
        theDisplay.add(graphInfo, BorderLayout.WEST);
        theDisplay.add(tempInfo, BorderLayout.EAST);

        return theDisplay;
    }

    public void updateDisplay(final Barometer theBaro, final Humidity theHumid, final Rain theRain,
            final Temperature theTemp, final Wind theWind, final HumidIn humidIn, final TempIn tempIn) {

        storage.add(theWind.getMyWindSpeed());
        double chill = 35.74 + 0.6215 * theTemp.getMyTemp() - (35.75 * (Math.pow(theWind.getMyWindSpeed(), 0.16))) 
                        + (0.4275 * theTemp.getMyTemp() * (Math.pow(theWind.getMyWindSpeed(), 0.16)));
        myTemp.setText("TEMP OUT\n " + theTemp.getMyTemp() + "\u00B0" + "F");
        myBaro.setText("BARO\n " + theBaro.getMyBaroPressure() + " in");
        myHumid.setText("HUM OUT\n " + theHumid.getMyHumid() + "%");
        myRain.setText("RAIN RATE\n " + theRain.getMyRainRate() + " in/hr");
        myTempIn.setText("TEMP IN\n " + tempIn.getMyTemp()  + "\u00B0" + "F");
        myHumidIn.setText("HUM IN\n " + humidIn.getMyHumid() + "%");
        myChill.setText("CHILL\n" + String.format("%.2f",chill) + "\u00B0" + "F");
        
        myGraph = new makeGraph(toArray());
        myCompass = new makeCompass(theWind.getMyWindSpeed());
        graphInfo.removeAll();
        graphInfo.add(myCompass, BorderLayout.NORTH);
        graphInfo.add(myGraph, BorderLayout.SOUTH);

        graphInfo.setSize(graphInfo.getPreferredSize());
        graphInfo.revalidate();
        myDisplay.revalidate();
        graphInfo.repaint();
        myDisplay.repaint();
        myFrame.setMinimumSize(myFrame.getPreferredSize());
        myFrame.revalidate();
        myFrame.repaint();
        
    }

    private int[] toArray() {
        final int[] result = new int[storage.size()];
        for (int i = 0; i < storage.size(); i++) {
            result[i] = storage.get(i) * 10;
        }
        return result;
    }

}

class makeGraph extends JPanel {

    private static final long serialVersionUID = 1L;

    private int[] data;
    private final ArrayList<Shape> shapes = new ArrayList<>();
    /** The width of the panel. */
    private static final int WIDTH = 300;

    /** The height of the panel. */
    private static final int HEIGHT = 200;

    public makeGraph(final int[] theData) {
        super();
        this.data = theData;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final Shape rectangle = new Rectangle2D.Double(10, 10, getWidth() - 20, getHeight() - 20);
        g2d.draw(rectangle);
        for (int i = 0; i < data.length; i++) {
            shapes.add(new Ellipse2D.Double(((getWidth() / 24) * i) + 10, getHeight() - 10 - data[i], 5, 5));
        }
        for (int i = 0; i < shapes.size(); i++) {
            g2d.fill(shapes.get(i));
            g2d.draw(shapes.get(i));
        }
    }
}

class makeGraphTemp extends JPanel {

     /**
    *
    */
    private static final long serialVersionUID = 1L;

    /** The width of the panel. */
     private static final int WIDTH = 300;

     /** The height of the panel. */
     private static final int HEIGHT = 200;
 
     public makeGraphTemp() {
         super();
         setPreferredSize(new Dimension(WIDTH, HEIGHT));
     }
 
     @Override
     public void paintComponent(final Graphics g) {
         super.paintComponent(g);
         final Graphics2D g2d = (Graphics2D) g;
 
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
         final Shape rectangle = new Rectangle2D.Double(10, 10, getWidth() - 20, getHeight() - 20);
         g2d.draw(rectangle);
}
}

class makeCompass extends JPanel {

    private static final long serialVersionUID = 1L;
    
    /** The width of the panel. */
    private static final int WIDTH = 300;

    /** The height of the panel. */
    private static final int HEIGHT = 200;

    private String windSpeed;


    public makeCompass(int theSpeed) {
        super();
        this.windSpeed = Integer.toString(theSpeed);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Shape circle = new Ellipse2D.Double(getWidth()/8, 0, getHeight(), getHeight());
        final Shape circle2 = new Ellipse2D.Double((getWidth()/8)+14, 14, 
                                                    getHeight()-28,getHeight()-28);
        g2d.draw(circle);
        g2d.draw(circle2);
        g2d.drawString("Wind", 10,15);
        g2d.drawString("N", getWidth()/2-16, 12);
        g2d.drawString("S", getWidth()/2-16, getHeight()-2);
        g2d.drawString("W", getWidth()/8+2, getHeight()/2);
        g2d.drawString("E", getWidth()-(getWidth()/4), getHeight()/2);
        g2d.drawString(windSpeed, (getWidth()/2)-15, getHeight()/2);
        g2d.drawString("MPH", (getWidth()/2)-25, (getHeight()/2)+15);
    }
}

    
