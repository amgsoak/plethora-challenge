package tests;

import debug.Log;
import models.CircularArc;
import models.Point;

import java.util.ArrayList;
import java.util.List;

public class GeneralTests {
    public static void testArcHulls() {
        CircularArc arc = null;

        arc = new CircularArc(0, new Point(5, 5), new Point(5, 0), new Point(10, 5));
//        testArcHull(arc, .1);
        testArcHull(arc, .01);
    }
    private static void testArcHull(CircularArc arc, double precision) {
        List<Point> hullPoints = new ArrayList<Point>();
        arc.pushAdditionalVertices(hullPoints, precision);
        hullPoints.add(arc.center);
        Log.dListPoints(hullPoints);
    }
}
