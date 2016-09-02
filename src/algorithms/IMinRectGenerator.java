package algorithms;

import models.Point;

import java.util.List;

public interface IMinRectGenerator {
    Point[] getMinimumBoundingRectangle(List<Point> points) throws IllegalArgumentException;
}
