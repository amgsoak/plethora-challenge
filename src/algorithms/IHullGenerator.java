package algorithms;

import models.Point;

import java.util.List;

public interface IHullGenerator {
    List<Point> getConvexHull(List<Point> points) throws IllegalArgumentException;
}
