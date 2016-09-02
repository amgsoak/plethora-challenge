package tests;

import algorithms.ActiveAlgs;
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

    /**
     * Returns true only if the point sets contain equal points in the same order
     * @param h1
     * @param h2
     * @return
     */
    public static boolean compareHulls(List<Point> h1, List<Point> h2) {
        if (h1.size() != h2.size()) return false;
        for (int i = 0; i < h1.size(); i++) {
            if (!h1.get(i).equals(h2.get(i))) {
                return false;
            }
        }
        return true;
    }

    // Test chan hull against simpler graham hull
    private static void test(Point[] points) {
        List<Point> pointList = Arrays.asList(points);
        List<Point> chanHull = ActiveAlgs.getConvexHull(pointList);
        List<Point> grahamHull = ActiveAlgs.getGrahamHull(pointList);

        boolean match = compareHulls(grahamHull, chanHull);

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
}
