package ISS;
/**
 * Barometer
 */
public class Barometer {

   /*  private static double READ_ACCURACY = 0.03;

    private static double EQUATION_ACCURACY = 0.01;

    private static int ELEVATION_ACCURACY = 10;

    private static double OVERALL_ACCURACY = 0.04; */

    private int myElevation;

    private double myBaroPressure;


    public Barometer(final double theBaroPressure) {
        this.myBaroPressure = theBaroPressure;
    }

    public int getMyElevation() {
        return myElevation;
    }

    public void setMyElevation(int myElevation) {
        this.myElevation = myElevation;
    }

    public double getMyBaroPressure() {
        return myBaroPressure;
    }

    public void setMyBaroPressure(double myBaroPressure) {
        this.myBaroPressure = myBaroPressure;
    }

}