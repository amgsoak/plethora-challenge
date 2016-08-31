/**
 * Created by Adam on 8/31/2016.
 */
public class LineSegment extends Edge {
    public Point v1, v2;

    public LineSegment(long id, Point v1, Point v2) {
        super(EdgeType.Straight, id);
    }
}

