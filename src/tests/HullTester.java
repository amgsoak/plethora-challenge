package tests;

import algorithms.ActiveAlgs;
import algorithms.ChanHull;
import algorithms.GrahamScan;
import algorithms.RotatingCalipers;
import debug.Log;
import models.InputFileData;
import models.Point;
import tools.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HullTester {
    public static void test(String assetsDir) {
        testSetFromFile(assetsDir+"CutCircularArc.json");

        /*testSet("SQUARE_WITH_CENTER_PT",
                new Point[]{
                        new Point(0, 0),
                        new Point(10, 0),
                        new Point(10, 10),
                        new Point(0, 10),
                        new Point(5, 5)
                }
        );
        testSet("SCRAMBLED_SQUARE_WITH_CENTER_PT",
                new Point[]{
                        new Point(0, 0),
                        new Point(10, 0),
                        new Point(10, 10),
                        new Point(5, 5),
                        new Point(0, 10)
                }
        );*/
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

    public static double[] getRectSize(Point[] rect) {
        double[] size = new double[]{
                rect[0].getDistTo(rect[1]),
                rect[1].getDistTo(rect[2])
        };
        Arrays.sort(size);
        return size;
    }

    public static boolean compareRects(Point[] rect1, Point[] rect2) {
        double[] size1 = getRectSize(rect1);
        double[] size2 = getRectSize(rect2);
        boolean match = Utils.equal(size1[0],size2[0]) && Utils.equal(size1[1], size2[1]);
        if (!match) {
            Log.log("Non-matching rects: " + Arrays.toString(size1) + " - " + Arrays.toString(size2));
        }
        return match;
    }

    // Test chan hull against simpler graham hull
/*    private static void test(Point[] points) {
        List<Point> pointList = Arrays.asList(points);
        List<Point> chanHull = ActiveAlgs.getConvexHull(pointList);
        List<Point> grahamHull = ActiveAlgs.getGrahamHull(pointList);

        boolean match = compareHulls(grahamHull, chanHull);

        if (match) {
            System.out.println("Matched hulls: " + chanHull);
            System.out.println("");
        } else {
            System.out.println("Unmatched hulls.");
            System.out.println("Graham hull: " + grahamHull);
            System.out.println("");
        }
    }*/

    private static void testSetFromFile(String filePath) {
        InputFileData fileData = new InputFileData();
        fileData.load(filePath);
        testSet(filePath, fileData.generateEdgePoints());
    }

    private static void testSet(String setName, List<Point> pointList) {
        List<Point> prevHull = null;
        Point[] prevRect = null;
        for (double angle = 0; angle < 2*Math.PI; angle += Math.PI/3) {
            List<Point> rotatedPoints = rotatePoints(pointList, angle);
            List<Point> newHull = ActiveAlgs.getGrahamHull(rotatedPoints);
            Point[] newRect = ActiveAlgs.getMinimumBoundingRectangle(newHull);
            if (prevHull != null) {
                boolean rectMatch = compareRects(prevRect, newRect);
                if (!rectMatch) {
                    Log.log("Failure. Rotation of set changed output rect: " + setName);
                    Log.log("Previous hull:");
                    Log.dListPoints(prevHull);
                    Log.log();
                    Log.log("Current hull:");
                    Log.dListPoints(newHull);
                    Log.log();
                    Log.log("Previous rect:" + Arrays.toString(prevRect));
                    Log.log("Current rect:" + Arrays.toString(prevRect));
                    break;
                }
            }
            prevHull = newHull;
            prevRect = newRect;
            Log.log("Hull/Rect match. Angle:" + angle  + " Set:" + setName);
        }
    }

    private static void testSet(String setName, Point[] points) {
        List<Point> pointList = new ArrayList(Arrays.asList(points));
        testSet(setName, pointList);
    }

    private static List<Point> rotatePoints(List<Point> points, double angle) {
        double upRightOffset = 100;
        ArrayList<Point> newPoints = new ArrayList(points.size());
        for (Point point : points) {
            double rotatedX = point.x * Math.cos(angle) - point.y * Math.sin(angle);
            double rotatedY = point.y * Math.cos(angle) + point.x * Math.sin(angle);
            newPoints.add(new Point(rotatedX + upRightOffset, rotatedY + upRightOffset));
        }
        return newPoints;
    }
}
