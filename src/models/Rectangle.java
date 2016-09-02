package models;

public class Rectangle {
    public double area;
    public int index[]; // Bottom, right, top, left
    public Point U[];

    public Rectangle() {
        index = new int[4];
        U = new Point[2];
    }
}
