package me.finaritra.paints.view.drawable.impl;

import me.finaritra.paints.view.PointCoordinate;
import me.finaritra.paints.view.drawable.Drawable;

public class Point extends Drawable {

    public Point() {
        super();
    }
    public Point(PointCoordinate startPoint, PointCoordinate stopPoint) {
        super(startPoint,startPoint);
    }


    @Override
    public Point clone() {
        return new Point(this.getStartPoint(),this.getStartPoint());
    }

    @Override
    public Drawable newInstance() {
        return new Point();
    }
}
