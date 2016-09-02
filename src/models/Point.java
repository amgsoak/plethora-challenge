package models;

public class Point {
    public static final Point ORIGIN = new Point(0, 0);
    private static final double EPSILON = .00001;

    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistTo(Point p) {
        return Math.sqrt(Math.pow((p.x - x), 2) + Math.pow((p.y - y), 2));
    }

    public double getDistSquaredTo(Point p) {
        return Math.pow((p.x - x), 2) + Math.pow((p.y - y), 2);
    }

    public boolean equals(Point p) {
        return Math.abs(p.x - x) < EPSILON && Math.abs(p.y - y) < EPSILON;
    }

    public Point minus(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    public void times(double m) {
        x *= m;
        y *= m;
    }

    public void normalize() {
        double length = getDistTo(ORIGIN);
        x /= length;
        y /= length;
    }

    public Point getCWPerpendicular() {
        return new Point(y*-1, x);
    }

    public double dot(Point p) {
        return x*p.x + y*p.y;
    }

    @Override
    public String toString() {
        return "[" + x + "; " + y + "]";
    }
}
