package models;

import debug.Log;

import java.util.List;

public class CircularArc extends Edge {
    public Point center;
    public Point startPoint;
    public Point endPoint;
    public double radius;

    public CircularArc(int id, Point center, Point startPoint, Point endPoint) {
        super(EdgeType.Curved, id);
        this.center = center;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        radius = center.getDistTo(startPoint);
    }

    public double getLength() {
        return angleBetweenPoints(startPoint, endPoint) * radius;
    }

    private double angleBetweenPoints(Point p1, Point p2) {
        double distSquared = p1.getDistSquaredTo(p2);
        return Math.acos(1 - distSquared/(2* radius * radius));
    }

    private double angleOfPointOnCircle(Point p) {
        return Math.atan2(p.y - center.y, p.x - center.x);
//        double distSquared = p1.getDistSquaredTo(p2);
//        return Math.acos(1 - distSquared/(2* radius * radius));
    }

    public void pushAdditionalVertices(List<Point> vertices, double precision) {
        // Push approximation of outer edge of arc
        // Determine angle increment necessary to ensure the point-based approximation
        // of the arc is never off by more than the value of the precision
        double maxSag = precision;
        double angleIncrement = 2 * Math.asin(Math.sqrt(2*radius*maxSag - maxSag*maxSag)/radius);
        Point origin = new Point(center.x + radius, center.y);
        double startAngle = angleOfPointOnCircle(startPoint);
        double endAngle = angleOfPointOnCircle(endPoint);
        if (endAngle > startAngle) endAngle -= 2*Math.PI;
        Log.log(angleIncrement + " " + startAngle + " " + endAngle);
        for (double i = startAngle - angleIncrement; i > endAngle; i -= angleIncrement) {
            Point p = new Point(center.x + Math.cos(i)*radius, center.y + Math.sin(i)*radius);
            Log.log("Arc point: " + p);
            vertices.add(p);
        }
    }
}