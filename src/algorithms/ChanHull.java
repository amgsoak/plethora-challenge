package algorithms;

import models.Point;

import java.util.ArrayList;
import java.util.List;

public class ChanHull {
    public static final int INF = 10000;

    /**
     * Calculates the points on the convex hull of a set of points
     * @param points
     * @return
     */
    public static List<Point> calcHull(List<Point> points) {
        int n = points.size();
        // Iterate on powers of m, until it is at least as big as the size of the hull
        for (int t = 1; t < n; t++) {
            int m = (int) Math.min(Math.pow(2, Math.pow(2, t)), n);
            List<Point> hullPoints = hull(points, m);
            if (hullPoints != null) {
                return hullPoints;
            }
        }
        return null;
    }

    static private double getAngle(Point a, Point b, Point c) {
        if (a == b || a == c || b == c) {
            return -INF;
        }

        double Ax = a.x - b.x;
        double Ay = a.y - b.y;
        double Bx = c.x - b.x;
        double By = c.y - b.y;

        double modA = Math.sqrt(Ax*Ax + Ay*Ay);
        double modB = Math.sqrt(Bx*Bx + By*By);

        return Math.acos((Ax*Bx + Ay*By) / (modA * modB));
    }

    static Point findMax(Point p0, Point p1, List<Point> l) {
        Point qmax = l.get(0);
        double maxAngle = getAngle(p0, p1, qmax);

        for (int i=1; i<l.size(); i++) {
            Point q = l.get(i);
            double curAngle = getAngle(p0, p1, q);
            if (curAngle > maxAngle ||
                    (curAngle == maxAngle && (Math.pow(q.x - p1.x,2)+Math.pow(q.y - p1.y,2)) <
                            (Math.pow(qmax.x - p1.x,2)+Math.pow(qmax.y - p1.y,2)) )) {
                maxAngle = curAngle;
                qmax = q;
            }
        }
        return qmax;
    }

    static List<Point> hull(List<Point> points, int m) {
        int n = points.size();
        int r = n / m;

        // Break point set into ~n/m subLists
        List<List<Point>> subLists = new ArrayList();
        for (int i = 0; i < r ; i++) {
            if ( (i+1) * m < n ) {
                subLists.add(points.subList(i * m, (i + 1) * m));
            } else {
                subLists.add(points.subList(i * m, n));
            }
        }

        // Do a graham calcHull of each subList, and collect all hull points
        List<List<Point>> hulls = new ArrayList();
        for (int i = 0; i < r; i++) {
            hulls.add(GrahamScan.calcHull(subLists.get(i)));
        }

        // Find the bottom left point
        Point p0 = points.get(0);
        for (Point p : points) {
            if (p0.y > p.y) {
                p0 = p;
            } else if (p0.y == p.y) {
                if (p0.x > p.x) {
                    p0 = p;
                }
            }
        }

        List<Point> hull = new ArrayList();
        hull.add(new Point(-INF, 0));
        hull.add(p0);

        for (int k=1; k <= m; k++ ) {
            List<Point> qlist = new ArrayList();

            for (int i = 0; i < r; i++) {
                qlist.add(findMax(hull.get(k-1), hull.get(k), hulls.get(i)));
            }

            Point qmax = findMax(hull.get(k-1), hull.get(k), qlist);

            // We've reached the starting point.
            if (qmax == hull.get(1)){
                hull.remove(0);
                return hull;
            }

            hull.add(qmax);
        }

        // Wrapped all subhulls and never arrived back at the original point
        return null;
    }
}
