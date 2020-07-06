package com.thecodinginterface.sorting;

import javafx.geometry.Bounds;

public class MyPoint implements Comparable<MyPoint> {
    private final double x;
    private final double y;
    private final double distance;
    
    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
        distance = Math.sqrt((x * x) + (y * y));
    }
    
    final double getX() {
        return x;
    }
    
    double getY() {
        return y;
    }
    
    double getDistance() {
        return distance;
    }
    
    boolean isEvenDistance() {
        return (int)distance % 2 == 0;
    }

    MyPoint toGraphicsCoordinates(Bounds bounds) {
        var centerX = bounds.getCenterX();
        var centerY = bounds.getCenterY();
        var newX = bounds.getCenterX() + x;
        var newY = bounds.getMaxY() - (bounds.getCenterY() + y);
        return new MyPoint(newX, newY);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyPoint other = (MyPoint) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

    @Override
    public int compareTo(MyPoint pt) {
        int result = Double.compare(x, pt.x);
        return result == 0 ? Double.compare(y, pt.y) : result;
    }
    
    @Override
    public String toString() {
        return String.format(
            "{%s: x=%d, y=%d, distance=%d}",
            getClass().getSimpleName(),
            (int)x,
            (int)y,
            (int)distance
        );
    }
}
