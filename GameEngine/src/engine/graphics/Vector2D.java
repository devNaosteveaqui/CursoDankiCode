package engine.graphics;

import java.awt.*;

public class Vector2D {
    public double x,y;
    public Vector2D(double x, double y) {

        this.x = x;
        this.y = y;
    }
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }
    public static double distance(Vector2D point1, Vector2D point2) {
        double distx = point1.x - point2.x;
        double disty = point1.y - point2.y;
        double dist = Math.sqrt(distx*distx + disty*disty);
        return dist;
    }}
