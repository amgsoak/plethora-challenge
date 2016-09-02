package tests;

import algorithms.ChanHull;
import algorithms.GrahamScan;
import algorithms.RotatingCalipers;
import models.Point;

import java.util.Arrays;
import java.util.List;

public class HullTester {
    public static void test() {
        test(
                new Point[]{
                        new Point(0, 0),
                        new Point(10, 0),
                        new Point(10, 10),
                        new Point(0, 10),
                        new Point(5, 5)
                }
        );
        test(
                new Point[]{
                        new Point(0, 0),
                        new Point(10, 0),
                        new Point(10, 10),
                        new Point(5, 5),
                        new Point(0, 10)
                }
        );
        test(
                new Point[]{
                        new Point(0, 10),
                        new Point(10, 10),
                        new Point(10, 0),
                        new Point(0, 0)
                }
        );
    }

    // Test chan hull against simpler graham hull
    private static void test(Point[] points) {
        List<Point> pointList = Arrays.asList(points);
        List<Point> chanHull = ChanHull.calcHull(pointList);
        List<Point> grahamHull = GrahamScan.calcHull(pointList);

        boolean match = chanHull.size() == grahamHull.size();
        if (match) {
            for (int i = 0; i < chanHull.size(); i++) {
                match = chanHull.get(i).equals(grahamHull.get(i));
                if (!match) break;
            }
        }
        if (match) {
            System.out.println("Matched hulls: " + chanHull);
            System.out.println("");
        } else {
            System.out.println("Unmatched hulls.");
            System.out.println("Chan hull: " + chanHull);
            System.out.println("Graham hull: " + grahamHull);
            System.out.println("");
        }

    }

    // Callipers should provide the same minimum rect size, regardless of the orientation of the points supplied
    private static void testCallipers() {
        //RotatingCalipers calipers = new RotatingCalipers();
    }
}
