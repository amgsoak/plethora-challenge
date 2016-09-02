import algorithms.ActiveAlgs;
import algorithms.GrahamScan2;
import algorithms.RotatingCalipers;
import debug.Log;
import org.omg.PortableInterceptor.ACTIVE;
import tests.HullTester;
import tests.RectTester;

import java.io.File;

public class Main {
    private static ConfigValues config;

    public static void main(String[] args) {
        ActiveAlgs.grahamHullGenerator = new GrahamScan2();
        ActiveAlgs.minRectGenerator = new RotatingCalipers();
//        ActiveAlgs.hullGenerator =

        String configPath = "assets/config.xml";

        Log.debug = true;

        // Load config
//TODO: These should come via commandline arguments
        config = new ConfigValues();
        config.load(configPath);
        Log.dLog(config);

//        runTests();
//        run();
        runAll();

//        parseEdgesAndVertices(""); // Parse xml input data to vertex/edge data
//        ArrayList<Vertex> hullVertices = null; // Get hull vertices from vertices/edges
    }

    private static void run() {
        String dataPath = "assets/input/Rectangle.json";
        float quote = QuoteGenerator.getQuote(config, dataPath);
    }

    private static void runAll() {
        String assetsDirPath = "assets/input/";
        File[] files = new File(assetsDirPath).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                System.out.println("Generating quote for file: " + fileName);
                QuoteGenerator.getQuote(config, assetsDirPath + fileName);
            }
        }
    }

    private static void runTests() {
//        HullTester.test();
//        GeneralTests.testArcHulls();
        RectTester.test();
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