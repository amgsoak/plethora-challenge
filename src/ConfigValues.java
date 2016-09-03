import debug.Log;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Loads and stores configuration values
 */
class ConfigValues {
    public float materialPadding; // Inches
    public float materialCost; // Dollars per square inch
    public float maxLaserSpeed; // Inches per second
    public float machineTimeCost; // Dollars per second
    public String dataDirPath; // Relative location of data files

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
            dataDirPath = getRequiredString(doc, "DATA_FILE_DIRECTORY");
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

    public String toString() {
        return Log.toString(this);
    }
}