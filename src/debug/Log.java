package debug;

import models.Point;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Adam on 8/31/2016.
 */
public class Log {
    // Set to true to show debug logging
    public static boolean debug = false;

    public static void logMembers(Object obj) {
        System.out.println(toString(obj));
    }

    public static void dLogMembers(Object obj) {
        if (debug) logMembers(obj);
    }

    public static void separator() {
        log("=============");
        log("");
    }

    public static void log(Object msg) {
        System.out.println(msg);
    }

    public static void dLog(Object msg) {
        if (debug) log(msg);
    }

    public static String toString(Object obj) {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( obj.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = obj.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(obj) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

    public static void listPoints(List<Point> points) {
        for (Point point : points) {
            System.out.println(point.x + "," + point.y);
        }
    }
}
