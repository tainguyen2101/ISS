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