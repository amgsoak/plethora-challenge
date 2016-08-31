import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class Main {
    public static void main(String[] args) {
        System.out.println("Begin main.");

        // These should come via commandline arguments
        String configPath = "assets/config.xml";
        String dataPath = "assets/input/Rectangle.json";

//        JSONParser parser = new JSONParser(, );
        Object o = parse("{'Test':'Value'}");

        Log.pwd();

//        ChanHullTester.test();

//        parseEdgesAndVertices(""); // Parse xml input data to vertex/edge data
//        ArrayList<Vertex> hullVertices = null; // Get hull vertices from vertices/edges


        ConfigValues config = new ConfigValues();
        config.load(configPath);

        Log.l(config);
        System.out.println();
    }

    /**
     * Parses the given JSON text string and returns object representation.
     * @param jsonLine
     * @return
     */
// Check formatting
    public static Object parse(String jsonLine) {
        JsonElement rootElement = new JsonParser().parse(jsonLine);
        JsonObject rootObject = rootElement.getAsJsonObject();

        // Parse vertices
        HashMap<Integer, Point> idToVertex = new HashMap();
        JsonObject verticesObject = rootObject.getAsJsonObject("Vertices");
        for (Map.Entry<String,JsonElement> vertexEntry : verticesObject.entrySet()) {
            int id = Integer.parseInt(vertexEntry.getKey());
            JsonObject posObject = vertexEntry.getValue().getAsJsonObject().getAsJsonObject("Position");
            float x = posObject.getAsJsonPrimitive("X").getAsFloat();
            float y = posObject.getAsJsonPrimitive("Y").getAsFloat();
            idToVertex.put(id, new Point(x, y));
        }

        // Parse edges
        ArrayList<Edge> edges = new ArrayList();
        JsonObject edgesObject = rootObject.getAsJsonObject("Edges");
        for (Map.Entry<String,JsonElement> edgeEntry : edgesObject.entrySet()) {
            int id = Integer.parseInt(edgeEntry.getKey());
            JsonObject edgeObject = edgeEntry.getValue().getAsJsonObject();
            String type = edgeObject.getAsJsonPrimitive("Type").getAsString();

            JsonObject vertexIdsObject = edgeObject.getAsJsonObject("Vertices");
            JsonArray vertexIdArray = vertexIdsObject.getAsJsonArray();
            int v1Id = vertexIdArray.get(0).getAsInt();
            int v2Id = vertexIdArray.get(1).getAsInt();
            Point v1 = idToVertex.get(v1Id);
            Point v2 = idToVertex.get(v2Id);

            Edge edge = null;
            if (type == "LineSegment") {
                edge = new LineSegment(id, v1, v2);

            } else if (type == "CircularArc") {
                int startId = edgeObject.getAsJsonPrimitive("ClockwiseFrom").getAsInt();
                if (startId != v1Id) {
                    Point tmp = v1;
                    v1 = v2;
                    v2 = tmp;
                }
                JsonObject centerObject = edgeObject.getAsJsonObject("Center");
                float x = centerObject.getAsJsonPrimitive("X").getAsFloat();
                float y = centerObject.getAsJsonPrimitive("Y").getAsFloat();

                edge = new CircularArc(id, new Point(x, y), v1, v2);
            } else {
                throw new RuntimeException("Unknown edge type: " + type);
            }
            JsonObject posObject = edgeEntry.getValue().getAsJsonObject().getAsJsonObject("Type");
            float x = posObject.getAsJsonPrimitive("X").getAsFloat();
            float y = posObject.getAsJsonPrimitive("Y").getAsFloat();
            idToVertex.put(id, new Point(x, y));
        }




        JsonArray jarray = rootObject.getAsJsonArray("translations");
        rootObject = jarray.get(0).getAsJsonObject();
        String result = rootObject.get("translatedText").toString();
        return result;
    }

    private static String loadInput(String filePath) {
        return "";
    }

    /**
     * Parse the xml string containing the edges and vertices
     * @param xml
     */
    private static void parseEdgesAndVertices(String xml) {

    }
}

/**
 * Loads and stores configuration values
 */
class ConfigValues {
    public float materialPadding; // Inches
    public float materialCost; // Dollars per square inch
    public float maxLaserSpeed; // Inches per second
    public float machineTimeCost; // Dollars per second

    public ConfigValues() {}

    public void load(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("Unable to find config file at: " + filePath);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            materialPadding = getRequiredFloat(doc, "MATERIAL_PADDING");
            materialCost = getRequiredFloat(doc, "MATERIAL_COST");
            maxLaserSpeed = getRequiredFloat(doc, "LASER_SPEED");
            machineTimeCost = getRequiredFloat(doc, "MACHINE_TIME_COST");
        } catch(Exception e) {
            throw new RuntimeException("Failed to parse config file at: " + filePath + ". " + e);
        }
    }

    private String getRequiredString(Document document, String elementName) {
        NodeList list = document.getElementsByTagName(elementName);
        if (list.getLength() <= 0) throw new RuntimeException("Config missing required element:" + elementName);
        String value = list.item(0).getTextContent();
        if (value == null)
            throw new RuntimeException("Config element missing value:" + elementName);
        return value;
    }

    private float getRequiredFloat(Document document, String elementName) {
        String value = getRequiredString(document, elementName);
        try {
            return Float.parseFloat(value);
        } catch(Exception e) {
            throw new RuntimeException("Bad config float value. Element:" + elementName + ", Value:" + value);
        }
    }
}