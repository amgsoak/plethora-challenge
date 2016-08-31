import java.lang.reflect.Field;

/**
 * Created by Adam on 8/31/2016.
 */
public class Log {
    public static void l(Object obj) {
        System.out.println(toString(obj));
    }

    public static void pwd() {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
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
}
