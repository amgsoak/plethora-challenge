package algorithms;

import models.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GrahamScan2 implements IHullGenerator {
    public List<Point> getConvexHull(List<Point> points) throws IllegalArgumentException {
        // Find bottom left point
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
        final Point bottomLeftPt = p0; // Declared final so it can be used within inner comparator class.
        points.remove(bottomLeftPt);

        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                double angle1 = (o1.x - bottomLeftPt.x) / (o1.y - bottomLeftPt.y);
                double angle2 = (o2.x - bottomLeftPt.x) / (o2.y - bottomLeftPt.y);
                if (angle1 > angle2) return -1;
                if (angle1 < angle2) return 1;
                double dist1 = Math.pow((o1.x - bottomLeftPt.x), 2) + Math.pow((o1.y - bottomLeftPt.y), 2);
                double dist2 = Math.pow((o2.x - bottomLeftPt.x), 2) + Math.pow((o2.y - bottomLeftPt.y), 2);
// TODO: double equality. epsilon?
                if (dist1 > dist2) return 1;
                if (dist1 < dist2) return -1;
                return 0;
            }
        });

        List<Point> result = new ArrayList();
        result.add(p0);
        result.add(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            Point a = result.get(result.size()-2);
            Point b = result.get(result.size()-1);
            Point c = points.get(i);

            while ( (b.x-a.x) * (c.y-a.y)
                    - (b.y-a.y) * (c.x-a.x) < 0){
                result.remove(result.size()-1);
                a = result.get(result.size()-2);
                b = result.get(result.size()-1);
            }

            result.add(points.get(i));
        }
        return result;
    }
}
