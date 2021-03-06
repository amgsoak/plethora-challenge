/*
 * Copyright (c) 2010, Bart Kiers
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package algorithms;

import models.Point;

import java.util.*;

public final class RotatingCalipers implements IMinRectGenerator {

    protected static enum Corner { UPPER_RIGHT, UPPER_LEFT, LOWER_LEFT, LOWER_RIGHT }

    public double getArea(Point[] rectangle) {

        double deltaXAB = rectangle[0].x - rectangle[1].x;
        double deltaYAB = rectangle[0].y - rectangle[1].y;

        double deltaXBC = rectangle[1].x - rectangle[2].x;
        double deltaYBC = rectangle[1].y - rectangle[2].y;

        double lengthAB = Math.sqrt((deltaXAB * deltaXAB) + (deltaYAB * deltaYAB));
        double lengthBC = Math.sqrt((deltaXBC * deltaXBC) + (deltaYBC * deltaYBC));

        return lengthAB * lengthBC;
    }

    public List<Point[]> getAllBoundingRectangles(List<Point> convexHull) throws IllegalArgumentException {

        List<Point[]> rectangles = new ArrayList<Point[]>();

        Caliper I = new Caliper(convexHull, getIndex(convexHull, Corner.UPPER_RIGHT), 90);
        Caliper J = new Caliper(convexHull, getIndex(convexHull, Corner.UPPER_LEFT), 180);
        Caliper K = new Caliper(convexHull, getIndex(convexHull, Corner.LOWER_LEFT), 270);
        Caliper L = new Caliper(convexHull, getIndex(convexHull, Corner.LOWER_RIGHT), 0);

        while(L.currentAngle < 90.0) {

            rectangles.add(new Point[]{
                    L.getIntersection(I),
                    I.getIntersection(J),
                    J.getIntersection(K),
                    K.getIntersection(L)
            });

            double smallestTheta = getSmallestTheta(I, J, K, L);

            I.rotateBy(smallestTheta);
            J.rotateBy(smallestTheta);
            K.rotateBy(smallestTheta);
            L.rotateBy(smallestTheta);
        }

        return rectangles;
    }

    public Point[] getMinimumBoundingRectangle(List<Point> points) throws IllegalArgumentException {

        List<Point[]> rectangles = getAllBoundingRectangles(points);

        Point[] minimum = null;
        double area = Long.MAX_VALUE;

        for (Point[] rectangle : rectangles) {

            double tempArea = getArea(rectangle);

            if (minimum == null || tempArea < area) {
                minimum = rectangle;
                area = tempArea;
            }
        }

        return minimum;
    }

    private double getSmallestTheta(Caliper I, Caliper J, Caliper K, Caliper L) {

        double thetaI = I.getDeltaAngleNextPoint();
        double thetaJ = J.getDeltaAngleNextPoint();
        double thetaK = K.getDeltaAngleNextPoint();
        double thetaL = L.getDeltaAngleNextPoint();

        if(thetaI <= thetaJ && thetaI <= thetaK && thetaI <= thetaL) {
            return thetaI;
        }
        else if(thetaJ <= thetaK && thetaJ <= thetaL) {
            return thetaJ;
        }
        else if(thetaK <= thetaL) {
            return thetaK;
        }
        else {
            return thetaL;
        }
    }

    protected int getIndex(List<Point> convexHull, Corner corner) {

        int index = 0;
        Point point = convexHull.get(index);

        for(int i = 1; i < convexHull.size() - 1; i++) {

            Point temp = convexHull.get(i);
            boolean change = false;

            switch(corner) {
                case UPPER_RIGHT:
                    change = (temp.x > point.x || (temp.x == point.x && temp.y > point.y));
                    break;
                case UPPER_LEFT:
                    change = (temp.y > point.y || (temp.y == point.y && temp.x < point.x));
                    break;
                case LOWER_LEFT:
                    change = (temp.x < point.x || (temp.x == point.x && temp.y < point.y));
                    break;
                case LOWER_RIGHT:
                    change = (temp.y < point.y || (temp.y == point.y && temp.x > point.x));
                    break;
            }

            if(change) {
                index = i;
                point = temp;
            }
        }

        return index;
    }

    protected static class Caliper {

        final static double SIGMA = 0.00000000001;

        final List<Point> convexHull;
        int pointIndex;
        double currentAngle;

        Caliper(List<Point> convexHull, int pointIndex, double currentAngle) {
            this.convexHull = convexHull;
            this.pointIndex = pointIndex;
            this.currentAngle = currentAngle;
        }

        double getAngleNextPoint() {

            Point p1 = convexHull.get(pointIndex);
            Point p2 = convexHull.get((pointIndex + 1) % convexHull.size());

            double deltaX = p2.x - p1.x;
            double deltaY = p2.y - p1.y;

            double angle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;

            return angle < 0 ? 360 + angle : angle;
        }

        double getConstant() {

            Point p = convexHull.get(pointIndex);

            return p.y - (getSlope() * p.x);
        }

        double getDeltaAngleNextPoint() {

            double angle = getAngleNextPoint();

            angle = angle < 0 ? 360 + angle - currentAngle : angle - currentAngle;

            return angle < 0 ? 360 : angle;
        }

        Point getIntersection(Caliper that) {

            // the x-intercept of 'this' and 'that': x = ((c2 - c1) / (m1 - m2))
            double x;
            // the y-intercept of 'this' and 'that', given 'x': (m*x) + c
            double y;

            if(this.isVertical()) {
                x = convexHull.get(pointIndex).x;
            }
            else if(this.isHorizontal()) {
                x = that.convexHull.get(that.pointIndex).x;
            }
            else {
                x = (that.getConstant() -  this.getConstant()) / (this.getSlope() - that.getSlope());
            }

            if(this.isVertical()) {
                y = that.getConstant();
            }
            else if(this.isHorizontal()) {
                y = this.getConstant();
            }
            else {
                y = (this.getSlope() * x) + this.getConstant();
            }

            return new Point(x, y);
        }

        double getSlope() {
            return Math.tan(Math.toRadians(currentAngle));
        }

        boolean isHorizontal() {
            return (Math.abs(currentAngle) < SIGMA) || (Math.abs(currentAngle - 180.0) < SIGMA);
        }

        boolean isVertical() {
            return (Math.abs(currentAngle - 90.0) < SIGMA) || (Math.abs(currentAngle - 270.0) < SIGMA);
        }

        void rotateBy(double angle) {

            if(this.getDeltaAngleNextPoint() == angle) {
                pointIndex++;
            }

            this.currentAngle += angle;
        }
    }
}