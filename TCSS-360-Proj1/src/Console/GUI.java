package Console;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

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

    private JTextArea myHumid;

    private ArrayList<Integer> storage;



    public GUI() throws Exception {
        super();
        storage = new ArrayList<>();
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
        myFrame.setMinimumSize(myFrame.getPreferredSize());
        myFrame.setLocation(SCREEN_SIZE.width / 8,
                (int) (SCREEN_SIZE.getHeight() / 8));
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

        theDisplay.setLayout(new GridLayout(3, 2));
        theDisplay.add(myCompass);
        theDisplay.add(myTemp);
        theDisplay.add(myBaro);
        theDisplay.add(myHumid);
        theDisplay.add(myRain);
        theDisplay.add(new makeCompass());
        return theDisplay;
    }

    public void updateDisplay(final Barometer theBaro, final Humidity theHumid, final Rain theRain,
            final Temperature theTemp, final Wind theWind) {
        storage.add(theWind.getMyWindSpeed());        
        myTemp.setText("TEMPERATURE\n " + theTemp.getMyTemp() );
        myTemp.setSize(myTemp.getPreferredSize());
        myBaro.setText("BAROMETER\n " + theBaro.getMyBaroPressure());
        myHumid.setText("HUMIDITY\n " + theHumid.getMyHumid());
        myRain.setText("RAIN RATE\n " + theRain.getMyRainRate());
        myCompass.setText(theWind.getMyCompass());
        myCompass.setSize(myCompass.getPreferredSize());
        myDisplay.remove(2);
        myDisplay.add(new makeGraph(toArray()), 2);
        myDisplay.repaint();
        myFrame.setMinimumSize(myFrame.getPreferredSize());
        myFrame.repaint();
    }

    private int[] toArray() {
        int[] result = new int[storage.size()];
        for(int i = 0; i < storage.size(); i++) {
            result[i] = storage.get(i) * 10;
        }
        return result;
    }
    
}
class makeGraph extends JPanel {

    private static final long serialVersionUID = 1L;

    private int[] data;
    private ArrayList<Shape> shapes = new ArrayList<>();

    public makeGraph(int[] theData) {
        this.data = theData;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapes.clear();
        final Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final Shape rectangle = new Rectangle2D.Double(10,10,getWidth()-20,getHeight()-20);
        g2d.draw(rectangle);
        for (int i = 0; i < data.length; i++) {
            shapes.add(new Ellipse2D.Double(((getWidth()/24)*i)+10,getHeight() - 10 - data[i],5,5));
        }
        
        for (int i = 0; i < shapes.size(); i++){
            g2d.fill(shapes.get(i));
            g2d.draw(shapes.get(i));
        }
    }
}
class makeCompass extends JPanel {

    private static final long serialVersionUID = 1L;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Shape circle = new Ellipse2D.Double(getWidth()/4, 0, getHeight(), getHeight());
        g2d.draw(circle);
        g2d.drawString("N", getWidth()/2, 12);
        g2d.drawString("S", getWidth()/2, getHeight()-2);
        g2d.drawString("W", getWidth()/4+2, getHeight()/2);
        g2d.drawString("E", getWidth()-(getWidth()/4)-10, getHeight()/2);
    }
}

    
