import algorithms.ActiveAlgs;
import algorithms.GrahamScan;
import debug.Log;
import models.CircularArc;
import models.Edge;
import models.EdgeType;
import models.Point;

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
        double totalTime = 0;
        for (Edge edge : fileData.edges) {
            double speed = config.maxLaserSpeed;
            if (edge.type == EdgeType.Curved) {
//TODO: Where to put logic of speed of cutting an arc?
                CircularArc arc = (CircularArc)edge;
                speed *= Math.exp(-1/arc.radius);
            }
            double length = edge.getLength();
            double time = length / speed;
            totalTime += time;
            Log.dLog("Type: " + edge.type + ", Length: " + length + ", Speed:" + speed + ", Time:" + time);
        }

        // Determine the set of points to be used to calculate minimum rect size
        ArrayList<Point> vertices = new ArrayList();
        // Collect all endpoints
        for (int key : fileData.idToVertex.keySet()) {
            vertices.add(fileData.idToVertex.get(key));
        }
        // Collect all other edge vertices
        for (Edge edge : fileData.edges) {
            edge.pushAdditionalVertices(vertices, .01);
        }

        Log.log("Initial vertices:");
        Log.listPoints(vertices);
        Log.separator();

// TODO: Create more tests for graham/chan
//TODO: Go back to chan hull if time. Needs to match graham hull in tests.
//        List<Point> hullVertices = ChanHull.calcHull(vertices);
        List<Point> hullVertices = GrahamScan.calcHull(vertices);
        Log.log("Hull vertices:");
        Log.listPoints(hullVertices);
        Log.separator();

        // Determine the size of the rectangle necessary to bound the hull in its most efficient orientation
        Point[] rect = ActiveAlgs.getMinimumBoundingRectangle(hullVertices); // 4 elements

        System.out.println("Bounding Rect Points: " + Arrays.toString(rect));

        double width = rect[0].getDistTo(rect[1]);
        double height = rect[1].getDistTo(rect[2]);
        double area = width * height;
        Log.log("Bounding Rect Width: " + width + " Height: " + height + " Area: " + area);

        width += config.materialPadding;
        height += config.materialPadding;
        area = width * height;
        Log.log("Padded Rect Width: " + width + " Height: " + height + " Area: " + area);

        Log.log("Total time: " + totalTime);

        // Determine costs
        double timeCost = totalTime * config.machineTimeCost;
        double materialCost = area * config.materialCost;
        double totalCost = timeCost + materialCost;
        float finalCost = roundMoney(totalCost);

        Log.log("Time cost: " + timeCost);
        Log.log("Material cost: " + materialCost);
        Log.log("Total cost: " + totalCost);
        Log.log("Final cost: " + finalCost);

        return finalCost;
    }

    private static float roundMoney(double value) {
        return ((float)Math.round(value*100))/100;
    }
}