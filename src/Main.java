import algorithms.ActiveAlgs;
import algorithms.GrahamScan2;
import algorithms.RotatingCalipers;
import debug.Log;
import tests.RectTester;

import java.io.Console;
import java.io.File;

public class Main {
    private static ConfigValues config;

    public static void main(String[] args) {
        ActiveAlgs.grahamHullGenerator = new GrahamScan2();
        ActiveAlgs.minRectGenerator = new RotatingCalipers();
//        ActiveAlgs.hullGenerator =

        String configPath = "assets/config.xml";

//        Log.debug = true;

        // Load config
//TODO: These should come via commandline arguments
        config = new ConfigValues();
        config.load(configPath);
        Log.dLog(config);

//        runTests();
        runAll();

//        parseEdgesAndVertices(""); // Parse xml input data to vertex/edge data
//        ArrayList<Vertex> hullVertices = null; // Get hull vertices from vertices/edges
/*        try {
            Thread.sleep(4000);
        } catch(Exception e){}*/


        waitForEnter("Operation Complete");
    }

    public static void waitForEnter(String message, Object... args) {
        Console c = System.console();
        if (c != null) {
            if (message != null)
                c.format(message, args);
            c.format("\nPress ENTER to quit.\n");
            c.readLine();
        }
    }

    private static void runAll() {
        String assetsDirPath = "assets/input/";
        File[] files = new File(assetsDirPath).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                generateQuoteForFile(assetsDirPath + fileName);
            }
        }
    }

    private static float generateQuoteForFile(String filePath) {
        Log.separator();
        Log.log("Generating quote for file: " + filePath);
        return QuoteGenerator.getQuote(config, filePath);
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