import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChanHull {
    public static final int INF = 10000;

    static List<Point> graham(List<Point> pts) {
        List<Point> points = new ArrayList(pts);
        //find p0 (with min y)i
        Point p0 = points.get(0);

        for (Point p : points) {
            if (p0.getY() > p.getY()) {
                p0 = p;
            } else if (p0.getY() == p.getY()) {
                if (p0.getX() > p.getX()) {
                    p0 = p;
                }
            }
        }

        Point.setP0(p0);
        points.remove(p0);
        Collections.sort(points);

        List<Point> result = new ArrayList();
        result.add(p0);
        result.add(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            Point a = result.get(result.size()-2);
            Point b = result.get(result.size()-1);
            Point c = points.get(i);

            while ( (b.getX()-a.getX()) * (c.getY()-a.getY())
                    - (b.getY()-a.getY()) * (c.getX()-a.getX()) < 0){
                result.remove(result.size()-1);
                a = result.get(result.size()-2);
                b = result.get(result.size()-1);
            }

            result.add(points.get(i));
        }
        return result;
    }

    static private double getAngle(Point a, Point b, Point c) {
        if (a == b || a == c || b == c) {
            return -INF;
        }

        double Ax = a.getX() - b.getX();
        double Ay = a.getY() - b.getY();
        double Bx = c.getX() - b.getX();
        double By = c.getY() - b.getY();

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
                    (curAngle == maxAngle && (Math.pow(q.getX() - p1.getX(),2)+Math.pow(q.getY() - p1.getY(),2)) <
                            (Math.pow(qmax.getX() - p1.getX(),2)+Math.pow(qmax.getY() - p1.getY(),2)) )) {
                maxAngle = curAngle;
                qmax = q;
            }
        }
        return qmax;
    }

    static List<Point> hull(List<Point> points, int m) {
        int n = points.size();
        int r = n / m;

        List<List<Point>> sublists = new ArrayList();
        for (int i = 0; i < r ; i++) {
            if ( (i+1) * m < n ) {
                sublists.add(points.subList(i*m, (i+1)*m));
            } else {
                sublists.add(points.subList(i*m, n));
            }
        }

        List<List<Point>> hulls = new ArrayList();
        for (int i = 0; i < r; i++) {
            hulls.add(graham(sublists.get(i)));
        }

        Point p0 = points.get(0);

        for (Point p : points) {
            if (p0.getY() > p.getY()) {
                p0 = p;
            } else if (p0.getY() == p.getY()) {
                if (p0.getX() > p.getX()) {
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

            if (qmax == hull.get(1)){
                hull.remove(0);
                return hull;
            }

            hull.add(qmax);
        }

        return null;
    }

    public static List<Point> calcHull(List<Point> points) {
        int n = points.size();
        for (int t = 1; t < n; t++) {
            int m = (int) Math.min(Math.pow(2, Math.pow(2, t)), n);
            List<Point> L = hull(points, m);
            if (L != null) {
                return L;
            }
        }
        return null;
    }
}
