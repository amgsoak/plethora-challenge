package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import debug.Log;
import models.CircularArc;
import models.Edge;
import models.LineSegment;
import models.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputFileData {
    public HashMap<Integer, Point> idToVertex;
    public ArrayList<Edge> edges;

    public InputFileData() {}

    public void load(String filePath) {
        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch(IOException e) {
            throw new RuntimeException("Failed to load input file from '"+filePath+"'. " + e);
        }
        parseData(jsonString);
    }

// Check formatting
    private void parseData(String jsonString) {
        JsonElement rootElement = new JsonParser().parse(jsonString);
        JsonObject rootObject = rootElement.getAsJsonObject();

        // Parse vertices
        idToVertex = new HashMap();
        JsonObject verticesObject = rootObject.getAsJsonObject("Vertices");
        for (Map.Entry<String,JsonElement> vertexEntry : verticesObject.entrySet()) {
            int id = Integer.parseInt(vertexEntry.getKey());
            JsonObject posObject = vertexEntry.getValue().getAsJsonObject().getAsJsonObject("Position");
            float x = posObject.getAsJsonPrimitive("X").getAsFloat();
            float y = posObject.getAsJsonPrimitive("Y").getAsFloat();
            idToVertex.put(id, new Point(x, y));
        }

        // Parse edges
        edges = new ArrayList();
        JsonObject edgesObject = rootObject.getAsJsonObject("Edges");
        for (Map.Entry<String,JsonElement> edgeEntry : edgesObject.entrySet()) {
            int id = Integer.parseInt(edgeEntry.getKey());
            JsonObject edgeObject = edgeEntry.getValue().getAsJsonObject();
            String type = edgeObject.getAsJsonPrimitive("Type").getAsString();

            JsonArray vertexIdArray = edgeObject.getAsJsonArray("Vertices");
            int v1Id = vertexIdArray.get(0).getAsInt();
            int v2Id = vertexIdArray.get(1).getAsInt();
            Point v1 = idToVertex.get(v1Id);
            Point v2 = idToVertex.get(v2Id);

            Edge edge = null;
            if (type.equals("LineSegment")) {
                edge = new LineSegment(id, v1, v2);
            } else if (type.equals("CircularArc")) {
                int startId = edgeObject.getAsJsonPrimitive("ClockwiseFrom").getAsInt();
                if (startId != v1Id) {
                    Point tmp = v1;
                    v1 = v2;
                    v2 = tmp;
                }
                JsonObject centerObject = edgeObject.getAsJsonObject("Center");
                double x = centerObject.getAsJsonPrimitive("X").getAsDouble();
                double y = centerObject.getAsJsonPrimitive("Y").getAsDouble();
                Point centerPt = new Point(x, y);
                edge = new CircularArc(id, centerPt, v1, v2);
            } else {
                throw new RuntimeException("Unknown edge type: " + type);
            }
            edges.add(edge);
        }
    }

    // Build a list of points that describe all edges in the file
    public List<Point> generateEdgePoints() {
        double edgePointPrecision = .01;
        ArrayList<Point> vertices = new ArrayList();
        // Collect all endpoints
        for (int key : idToVertex.keySet()) {
            vertices.add(idToVertex.get(key));
        }
        // Collect all other edge vertices
        for (Edge edge : edges) {
            edge.pushAdditionalVertices(vertices, edgePointPrecision);
        }

        Log.dLog("Edge points:");
        Log.dListPoints(vertices);
        Log.log();

        return vertices;
    }

    public String toString() {
        return "Vertices: " + idToVertex + "\nEdges: " + edges;
    }
}
