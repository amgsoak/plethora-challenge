package algorithms;

import models.Point;

import java.util.List;

/**
 * Contains static references to algorithm implementations currently being used.
 * Facilitates testing of various implementations
 */
public class ActiveAlgs {
    public static IHullGenerator grahamHullGenerator;
    public static IHullGenerator hullGenerator;
    public static IMinRectGenerator minRectGenerator;

    public static List<Point> getGrahamHull(List<Point> points) throws IllegalArgumentException {
        return grahamHullGenerator.getConvexHull(points);
    }

    public static List<Point> getConvexHull(List<Point> points) throws IllegalArgumentException {
        return hullGenerator.getConvexHull(points);
    }

    public static Point[] getMinimumBoundingRectangle(List<Point> points) throws IllegalArgumentException {
        return minRectGenerator.getMinimumBoundingRectangle(points);
    }
}
