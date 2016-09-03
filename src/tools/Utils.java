package tools;

/**
 * Created by Adam on 9/2/2016.
 */
public class Utils {
    private static double defaultEpsilon = .000001;
    public static boolean equal(double d1, double d2) {
        double diff = Math.abs(d1 - d2);
        return diff < defaultEpsilon;
    }
}
