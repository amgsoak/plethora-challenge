import algorithms.ActiveAlgs;
import algorithms.ChanHull;
import algorithms.GrahamScan;
import algorithms.RotatingCalipers;
import debug.Log;
import tests.HullTester;

import java.io.Console;
import java.io.File;

public class Main {
    private static final String CONFIG_PATH = "assets/config.xml";
    private static final String ASSETS_DIR_PATH = "assets/input/";
    private static ConfigValues config;

    public static void main(String[] args) {
//        Log.debug = true;

        // Load config
        config = new ConfigValues();
        config.load(CONFIG_PATH);
        Log.dLog(config);

        ActiveAlgs.grahamHullGenerator = new GrahamScan();
//        ActiveAlgs.hullGenerator = new ChanHull();
        ActiveAlgs.minRectGenerator = new RotatingCalipers();

        if (args.length > 0) {
            String fileName = args[0];
            generateQuoteForFile(fileName);
        } else {
            runAll();
//            runTests();
        }

        waitForEnter("Operation Complete");
    }

    public static void waitForEnter(String message, Object... args) {
        Console c = System.console();
        if (c != null) {
            if (message != null)
                c.format(message, args);
            c.format("\n\nPress ENTER to quit.\n");
            c.readLine();
        }
    }

    private static void runAll() {
        File[] files = new File(ASSETS_DIR_PATH).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                generateQuoteForFile(fileName);
            }
        }
    }

    private static float generateQuoteForFile(String fileName) {
        Log.separator();
        Log.log("Generating quote for file: " + fileName);
        return QuoteGenerator.getQuote(config, ASSETS_DIR_PATH + fileName);
    }

    private static void runTests() {
        HullTester.test(config.dataDirPath);
//        GeneralTests.testArcHulls();
    }
}