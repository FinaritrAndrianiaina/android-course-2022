package me.finaritra.paints.view;

public class PointCoordinate {

    int x = 0;
    int y = 0;

    public PointCoordinate(int x, int y) {
        this.set(x, y);
    }

    public PointCoordinate() {
        this(0, 0);
    }

    public PointCoordinate(PointCoordinate pointCoordinate) {
        this(pointCoordinate.x, pointCoordinate.y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PointCoordinate copy() {
        return new PointCoordinate(this);
    }
}
