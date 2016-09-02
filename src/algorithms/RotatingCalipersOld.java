package algorithms;

import models.Point;
import models.Rectangle;

import java.util.ArrayList;

public class RotatingCalipersOld {
    public static Rectangle buildRectangle(int j0, int j1, ArrayList<Point> vertices) {
        Rectangle rect = new Rectangle();
        rect.U[0] = vertices.get(j1).minus(vertices.get(j0));
        rect.U[0].normalize();
        rect.U[1] = rect.U[0].getCWPerpendicular();
        rect.index = new int[]{j1, j1, j1 ,j1};
        Point origin = vertices.get(j1);
        Point[] support = new Point[]{Point.ORIGIN, Point.ORIGIN, Point.ORIGIN, Point.ORIGIN};

        for (int i = 0; i < vertices.size(); i++) {
            Point diff = vertices.get(i).minus(origin);
            Point v = new Point(rect.U[0].dot(diff), rect.U[1].dot(diff));

            if (v.x > support[1].x || (v.x == support[1].x && v.y > support[1].y)) {
                rect.index[1] = i;
                support[1] = v;
            }
            if (v.y > support[2].y || (v.y == support[2].y && v.x > support[1].x)) {
                rect.index[2] = i;
                support[2] = v;
            }
            if (v.x > support[3].x || (v.x == support[3].x && v.y > support[3].y)) {
                rect.index[3] = i;
                support[3] = v;
            }
        }

        rect.area = (support[1].x - support[3].x) * support[2].y;

        return rect;
    }
}
