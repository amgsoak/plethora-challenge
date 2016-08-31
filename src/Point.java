public class Point implements Comparable<Point> {
    private double x, y;
    private static Point p0 = null;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static void setP0(Point p) {
        p0 = p;
    }

    public double getAngleC() {
        return (x - p0.getX()) / (y - p0.getY());
    }

    public double getDistSquared() {
        return Math.pow((x - p0.getX()), 2) + Math.pow((y - p0.getY()), 2);
    }

    public double getDistTo(Point p) {
        return Math.sqrt(Math.pow((p.x - p0.getX()), 2) + Math.pow((p.y - p0.getY()), 2));
    }

    @Override
    public int compareTo(Point point) {
        double angle1 = this.getAngleC();
        double angle2 = point.getAngleC();

        if (angle1 > angle2) {
            return -1;
        } else if (angle1 < angle2) {
            return 1;
        } else {
            double dist1 = this.getDistSquared();
            double dist2 = point.getDistSquared();

            if (dist1 > dist2) {
                return 1;
            } else if (dist1 < dist2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "[" + x + "; " + y + "]";
    }
}
