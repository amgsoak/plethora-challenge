package models;

import java.util.List;

/**
 * Created by Adam on 8/31/2016.
 */
public class LineSegment extends Edge {
    public Point v1, v2;

    public LineSegment(int id, Point v1, Point v2) {
        super(EdgeType.Straight, id);
        this.v1 = v1;
        this.v2 = v2;
    }

    public double getLength() {
        return v1.getDistTo(v2);
    }

    public void pushHullVertices(List<Point> vertices, double precision) {
        vertices.add(v1);
        vertices.add(v2);
    }
}

