package tests;

import algorithms.ActiveAlgs;
import models.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RectTester {
    public static void test() {
        testSet(
                new Point[]{
                        new Point(0, 0),
                        new Point(10, 0),
                        new Point(10, 10),
                        new Point(0, 10)
                }
        );
    }

    private static void testSet(Point[] points) {
        List<Point> convexHull = ActiveAlgs.getGrahamHull(Arrays.asList(points));

        // Callipers should provide the same minimum rect size, regardless of the orientation of the points supplied
        Point[] prevRect = null;
        for (double angle = Math.PI/3; angle < 2*Math.PI; angle+= Math.PI/3) {
            List<Point> rotatedPoints = rotatePoints(convexHull, angle);
            Point[] rect = ActiveAlgs.getMinimumBoundingRectangle(rotatedPoints);
            System.out.println(Arrays.toString(rect));
            System.out.println(rect[0].getDistTo(rect[1]) + "x" + rect[1].getDistTo(rect[2]));
        }
    }

    private static List<Point> rotatePoints(List<Point> points, double angle) {
        ArrayList<Point> newPoints = new ArrayList(points.size());
        for (Point point : points) {
            double rotatedX = point.x * Math.cos(angle) - point.y * Math.sin(angle);
            double rotatedY = point.y * Math.cos(angle) + point.x * Math.sin(angle);
            newPoints.add(new Point(rotatedX, rotatedY));
        }
        return newPoints;
    }
}
