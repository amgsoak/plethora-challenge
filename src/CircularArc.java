/**
 * Created by Adam on 8/31/2016.
 */
public class CircularArc extends Edge {
    public Point center;
    public Point startPoint;
    public Point endPoint;

    public CircularArc(int id, Point center, Point startPoint, Point endPoint) {
        super(EdgeType.Curved, id);
        this.center = center;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public double getLength() {
        return center.getDistTo(startPoint);
    }
}
