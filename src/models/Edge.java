package models;

import debug.Log;

import java.util.List;

/**
 * Created by Adam on 8/31/2016.
 */
public abstract class Edge {
    public EdgeType type;
    public int id;

    public Edge(EdgeType type, int id) {
        this.type = type;
        this.id = id;
    }

    public abstract double getLength();

    public String toString() {
        return Log.toString(this);
    }

    public abstract void pushHullVertices(List<Point> vertices, double precision);
}
