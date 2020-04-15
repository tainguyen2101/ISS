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

	static double [][] dataMemory;   // Contains 24 hr data information

	static int currentMemIndex;       // Index in 2D array of current data

	static int graphVariable;		// Choose index of variable to graph

	static int hour;				// Every 60 data points (represents 1 hr) then data is added to 2D array.
	//// If we just used real time it would take a long time for the graph to change. 


	public GUI() throws Exception {
		super();
		myFrame = new JFrame("ISS");
		graphInfo = new JPanel();
		tempInfo = new JPanel();
		myButton = new ArrayList<JButton>();
		dataMemory = new double[7][26];
		setupDataArray();
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
	
		myButton.get(10).addActionListener(e -> { /// Action listeners for Jbutton changes the variable on the graph
			graphVariable=0;  //Bar
		});
		myButton.get(2).addActionListener(e -> {
			graphVariable=1;  //HumidOut
		});
		myButton.get(5).addActionListener(e -> {
			graphVariable=2;	//RainRate
		});
		myButton.get(0).addActionListener(e -> {
			graphVariable=3;   // temp
		});
		myButton.get(4).addActionListener(e -> {
			graphVariable=4;  //Wind Speed
		});
		

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


		myCompass = new makeCompass(0,0);
		myGraph = new makeGraphTemp();

		graphInfo.add(myCompass, BorderLayout.NORTH);
		graphInfo.add(myGraph, BorderLayout.SOUTH);


		theDisplay.add(graphInfo, BorderLayout.WEST);
		theDisplay.add(tempInfo, BorderLayout.EAST);

		return theDisplay;
	}

	public void updateDisplay(final Barometer theBaro, final Humidity theHumid, final Rain theRain,
			final Temperature theTemp, final Wind theWind, final HumidIn humidIn, final TempIn tempIn) {


		if(hour%60==0) {     /// Every 60 minutes data points are added to the 2D array for the graph
			dataMemory[0][currentMemIndex]=(double)(theBaro.getMyBaroPressure());
			dataMemory[1][currentMemIndex]=(double)(theHumid.getMyHumid());
			dataMemory[2][currentMemIndex]=(double)(theRain.getMyRainRate());
			dataMemory[3][currentMemIndex]=(double)(theTemp.getMyTemp());
			dataMemory[4][currentMemIndex]=(double)(theWind.getMyWindSpeed());
			dataMemory[5][currentMemIndex]=(double)(humidIn.getMyHumid());
			dataMemory[6][currentMemIndex]=(double)(tempIn.getMyTemp());
			currentMemIndex=(currentMemIndex+1)%24;
			hour=time.getHour();
			hour=hour%60;

		}
		hour++;

		// Calculate Chill 
		double chill = 35.74 + 0.6215 * theTemp.getMyTemp() - (35.75 * (Math.pow(theWind.getMyWindSpeed(), 0.16))) 
				+ (0.4275 * theTemp.getMyTemp() * (Math.pow(theWind.getMyWindSpeed(), 0.16)));

		time = LocalDateTime.now();
		date = LocalDateTime.now();
		myTime.setText(TIME_FORMAT.format(time));
		myDate.setText(DATE_FORMAT.format(date));
		myTemp.setText("TEMP OUT\n " + theTemp.getMyTemp() + "\u00B0" + "F");
		myBaro.setText("BARO\n " + theBaro.getMyBaroPressure() + " in");
		myHumid.setText("HUM OUT\n " + theHumid.getMyHumid() + "%");
		myRain.setText("RAIN RATE\n " + theRain.getMyRainRate() + " in/hr");
		myTempIn.setText("TEMP IN\n " + tempIn.getMyTemp()  + "\u00B0" + "F");
		myHumidIn.setText("HUM IN\n " + humidIn.getMyHumid() + "%");
		myChill.setText("CHILL\n" + String.format("%.2f",chill) + "\u00B0" + "F");

		myGraph = new makeGraph(toArray());
		myCompass = new makeCompass(theWind.getMyWindSpeed(), theWind.getMyDirection());
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
		
		final int[] result = new int[26];//// Creates array to be sent to the graph
		double min =  dataMemory[graphVariable][24];   /// Max and min change the data to fit the graph
		double max = dataMemory[graphVariable][25];
		for (int i = 0; i < 24; i++) {
			result[i] = (int) (8*(dataMemory[graphVariable][(i+currentMemIndex)%24]/((max-min)/17)));
		
		}



		result[24]=(int) dataMemory[graphVariable][24];     // Min of variable
		result[25]=(int) dataMemory[graphVariable][25];		// Max of variable 
		return result;
	}




	public static void dataMemory(double[] data) {     // Creates the 2D array that holds 24 hr information
		currentMemIndex=(currentMemIndex+1)%24;			// after 24 hrs the data rewrites over old data
		for(int i =0; i< dataMemory.length;i++) {
			for(int k = 0; k<data.length; k++) {
				dataMemory[k][currentMemIndex]=data[k];
			}
		}
	}




	public static void setupDataArray() {
		for(int i =0; i<dataMemory.length;i++) {  // Fill the 2D array with negative, negative number represents null value
			for (int k = 0;  k<dataMemory[i].length; k++) {
				dataMemory[i][k]=-1;				// If value is negative then it does not get graphed. Initial set up.
			} 
		}
		
		//Change max and min of variable to adjust variance of data representation on graph
		
		//BARO 
		dataMemory[0][24]=0; //MIN
		dataMemory[0][25]=50; //MAX

		//HUMID OUT
		dataMemory[1][24]=0;   
		dataMemory[1][25]=100;

		// RAIN
		dataMemory[2][24]=8;   
		dataMemory[2][25]=10;

		//TEMPOUT
		dataMemory[3][24]=40;  
		dataMemory[3][25]=140;

		//WINDSPEED
		dataMemory[4][24]=0;   
		dataMemory[4][25]=30;

		//HUMIDIN
		dataMemory[5][24]=0;   
		dataMemory[5][25]=100;

		//TEMPIN
		dataMemory[6][24]=50;   
		dataMemory[6][25]=90;

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

		for (int i = 0; i < 24; i++) {

			if(data[i]>0) {
				shapes.add(new Ellipse2D.Double(((getWidth() / 24) * i) + 10, getHeight() - 13 - data[i], 5, 5));
			}
		}
		for (int i = 0; i < shapes.size(); i++) {
			g2d.fill(shapes.get(i));
			g2d.draw(shapes.get(i));
		}
	}




}

class makeGraphTemp extends JPanel {

	
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

	private double radian;
	private double x;
	private double y;
	
	public makeCompass(int theSpeed, int theDirection) {
		
		super();
		this.windSpeed = Integer.toString(theSpeed);
	
		radian = ((450-theDirection)%360)*Math.PI/180;	// converts compass degrees to radians
		x=80*Math.cos(radian);							// Radius=80
		y=80*Math.sin(radian);
		
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

		g2d.drawString("o", (getWidth()/2-16)+(int)x, getHeight()/2+5 -(int) y);  /// Need to add better image for pointer
	
		g2d.drawString("Wind", 10,15);
		g2d.drawString("N", getWidth()/2-16, 12);
		g2d.drawString("S", getWidth()/2-16, getHeight()-2);
		g2d.drawString("W", getWidth()/8+2, getHeight()/2);
		g2d.drawString("E", getWidth()-(getWidth()/4), getHeight()/2);
		g2d.drawString(windSpeed, (getWidth()/2)-15, getHeight()/2);
		g2d.drawString("MPH", (getWidth()/2)-25, (getHeight()/2)+15);
	}

}


