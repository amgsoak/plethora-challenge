import debug.Log;
import tests.HullTester;

public class Main {
    private static ConfigValues config;

    public static void main(String[] args) {
// These should come via commandline arguments
        String configPath = "assets/config.xml";

        Log.debug = true;

        // Load config
        config = new ConfigValues();
        config.load(configPath);
        Log.dLog(config);

//        runTests();
        run();

//        parseEdgesAndVertices(""); // Parse xml input data to vertex/edge data
//        ArrayList<Vertex> hullVertices = null; // Get hull vertices from vertices/edges
    }

    private static void run() {
        String dataPath = "assets/input/Rectangle.json";
        float quote = QuoteGenerator.getQuote(config, dataPath);
    }

    private static void runTests() {
        HullTester.test();
//        GeneralTests.testArcHulls();
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