import java.util.Arrays;
import java.util.List;

/**
 * Created by Adam on 8/31/2016.
 */
public class ChanHullTester {
    public static void test() {
        testSet(
                new Point[] {
                        new Point(0, 0),
                        new Point(10, 0),
                        new Point(10, 10),
                        new Point(0, 10),
                        new Point(5, 5)
                }
        );
    }

    public static void testSet(Point[] points) {
        List<Point> calculateHull = ChanHull.calcHull(Arrays.asList(points));
        //System.out.println("Expected hull:" + expectedHull);
        System.out.println("Calculated hull:" + calculateHull);
    }
}
