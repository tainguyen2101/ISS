package ISS;
/**
 * WindDirection
 */
public class Wind {

  private int myDirection;

  private String myStringDirection;

  private int myWindSpeed;

  private String myCompass;



  public Wind(final int theDirection, final int theWindSpeed) {
    this.myWindSpeed = theWindSpeed;
    this.myDirection = theDirection;
	this.myCompass = CreateStringCompass(myDirection, myWindSpeed);
  }

  //private String CreateGraphicDisplay (int compassDegree, int WindSpeedinput,double [] data, int min, int max) {
	//return CreateStringCompass(compassDegree, WindSpeedinput)+makeGraph(data, min, max);
	
//}


public static String CreateStringCompass(int compassDegree, int WindSpeedinput){
	int size = 5;   //// Size of the compass. 
	double mag=2; ///// Change the distance of the dot on the compass. Need to adjust when changing the size.
	String WindSpeed = "";
	if(WindSpeedinput<10) {
		WindSpeed=WindSpeed+0;
	}
	WindSpeed=WindSpeed+WindSpeedinput;

	int compass = compassDegree%360;
	boolean[] NSEW = new boolean[16] ;
	int index = (int)(((compass))/22.5);
	index=index%16;
	NSEW[index]=true;
	
	
	
	StringBuilder draw = new StringBuilder();
	draw.append("\tWind\n\t");
	for (int i = -size; i <= size; i++) {
		for (int j = -size; j <= size; j++) {
			if(j==0 && i==-1 ) {
				draw.append(WindSpeed + "  ");
			}
			else if(j==0 && i==0 ) {
				draw.append("MPH");
			}

			else if (i==0 && (j==-size || j==size)) {
				if (j==-size) {
					draw.append("W   ");
				}
				else if (j==size) {					
					draw.append("E    ");
				}
			}
			else if (j==0 && (i==-size || i==size)) {
				if (i==-size) {
					draw.append("N    ");
				}
				else if (i==size) {
					draw.append("S    ");
				}
			}
			else if (j==i && j*j+i*i-size*size<(size)+3 && j*j+i*i-size*size>-(size) ) {
				if (i>0) {
					draw.append("SE   ");
				}
				if (i<0) {
					draw.append("NW   ");
				}
			}
			else if (j==-i && j*j+i*i-size*size<(size+3) && j*j+i*i-size*size>-(size)) {
				if (i>0) {
					draw.append("SW   ");
				}
				if (i<0) {
					draw.append("NE   ");
				}
			}
			else if ( j*j+i*i-size*size+3<2 && j*j+i*i-size*size+3>-2 ) {
				draw.append(".    ");		
			}
			else if (i==(int)(-(size-1)) && j==0 && NSEW[0]) {
				draw.append("o  ");
			}
			else if (i==(int)(-2*mag) && j==(int)(1*mag) && NSEW[1]) {
				draw.append("o   ");
			}
			else if (i==(int)(-(size/2)*mag+1 )&& j==(int)((size/2)*mag-1 )&& NSEW[2]) {
				draw.append("o   ");
			}
			else if (i==(int)(-1*mag) && j==(int)(2*mag) && NSEW[3]) {
				draw.append("o  ");
			}
			else if (i==0 && j==(int)((size-1)) && NSEW[4]) {
				draw.append(">   ");
			}
			else if (i==(int)(1*mag) && j==(int)(2*mag) && NSEW[5]) {
				draw.append("o   ");
			}
			else if (i==(int)((size/2)*mag-1) && j==(int)((size/2)*mag-1) && NSEW[6]) {
				draw.append("o   ");
			}
			else if (i==(int)(2*mag) && j==(int)(1*mag) && NSEW[7]) {
				draw.append("o   ");
			}
			else if (i==(int)((size-1)) && j==0 && NSEW[8]) {
				draw.append("v   ");
			}
			else if (i==(int)(2*mag) && j==(int)(-1*mag) && NSEW[9]) {
				draw.append("o   ");
			}
			else if (i==(int)((size/2)*mag-1) && j==(int)(-(size/2)*mag+1) && NSEW[10]) {
				draw.append("o   ");
			}
			else if (i==(int)(1*mag) && j==(int)(-2*mag) && NSEW[11]) {
				draw.append("o   ");
			}
			else if (i==0 && j==(int)(-(size-1)) && NSEW[12]) {
				draw.append("<   ");
			}
			else if (i==(int)(-1*mag) && j==(int)(-2*mag) && NSEW[13]) {
				draw.append("o   ");
			}
			else if (i==(int)(-(size/2)*mag+1 )&& j==(int)((-size/2)*mag+1) && NSEW[14]) {
				draw.append("o   ");
			}
			else if (i==(int)(-2*mag) && j==(int)(-1*mag )&& NSEW[15]) {
				draw.append("o   ");
			}
			else if (j*j+i*i-size*size<1.5) {
				draw.append("`      ");
			}

			else {
				draw.append("        ");
			}
		}
		if(i!=size) {
			draw.append("\n\t");
		}
		else {
		draw.append("\n  Weather Report\t\t"+"Every 1 hr\n");
		}
	}
	return draw.toString();
}

  public void setMyDirection(final int myDirection) {
    this.myDirection = myDirection;
  }

  public int getMyDirection() {
    return this.myDirection;
  }

  public String getMyStringDirection() {
    return myStringDirection;
  }

  public void setMyStringDirection(final String myStringDirection) {
    this.myStringDirection = myStringDirection;
  }

  public int getMyWindSpeed() {
    return myWindSpeed;
  }

  public String getMyCompass() {
      return myCompass;
  }


}