package tools;

/**
 * Utilities for comparing doubles safely
 */
public class DUtils {
    private static double DEFAULT_EPSILON = .000001;

    public static boolean equalTo(double d1, double d2) {
        double diff = Math.abs(d1 - d2);
        return diff < DEFAULT_EPSILON;
    }

    public static boolean lessThan(double d1, double d2) {
        return d1 < d2 && d2 - d1 > DEFAULT_EPSILON;
    }

    public static boolean greaterThan(double d1, double d2) {
        return d1 > d2 && d1 - d2 > DEFAULT_EPSILON;
    }

    public static int compareTo(double d1, double d2) {
        if(Math.abs(d1 - d2) < DEFAULT_EPSILON) return 0;
        if (d1 > d2) return 1;
        return -1;
    }
}
