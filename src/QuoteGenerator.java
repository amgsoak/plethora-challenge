import algorithms.ChanHull;
import algorithms.GrahamScan;
import algorithms.RotatingCalipers;
import debug.Log;
import models.CircularArc;
import models.Edge;
import models.EdgeType;
import models.Point;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuoteGenerator {
    public static float getQuote(ConfigValues config, String inputFilePath) {
        // Load data
        InputFileData fileData = new InputFileData();
        fileData.load(inputFilePath);
        Log.dLog(fileData);

        // Determine the total time necessary to cut
        double timeSeconds = 0;
        for (Edge edge : fileData.edges) {
            double speed = config.maxLaserSpeed;
            if (edge.type == EdgeType.Curved) {
                CircularArc arc = (CircularArc)edge;
                speed *= Math.exp(1/arc.radius);
            }
            timeSeconds += edge.getLength() / speed;
        }

        // Determine the set of points to be used to calculate minimum rect size.
        // Line segments only need to supply their vertices, while circular arcs must generate outer-bounds vertices
        ArrayList<Point> vertices = new ArrayList();
        for (Edge edge : fileData.edges) {
            edge.pushHullVertices(vertices, .01);
        }
// TODO: Create more tests for graham/chan
//TODO: Go back to chan hull if time. Needs to match graham hull in tests.
//        List<Point> hullVertices = ChanHull.calcHull(vertices);
        List<Point> hullVertices = GrahamScan.calcHull(vertices);

        // Determine the size of the rectangle necessary to bound the hull in its most efficient orientation
        Point2D.Double[] rect = RotatingCalipers.getMinimumBoundingRectangle(hullVertices); // 4 elements
        System.out.println(Arrays.toString(rect));

        // Determine costs
        float timeCost = (float)(timeSeconds * config.machineTimeCost);
        float materialCost = 0;
        float totalCost = timeCost + materialCost;

        Log.log("Time cost: " + timeCost);
        Log.log("Material cost: " + materialCost);
        Log.log("Total cost: " + totalCost);

        return totalCost;
    }
}