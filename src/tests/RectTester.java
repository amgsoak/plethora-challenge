package tests;

import models.Point;

/**
 * Created by Adam on 9/1/2016.
 */
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

    }
}
