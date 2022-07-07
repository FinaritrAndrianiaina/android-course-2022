package me.finaritra.paints.view.drawable.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.List;

import me.finaritra.paints.view.PointCoordinate;
import me.finaritra.paints.view.drawable.Drawable;

public class Line extends Drawable {


    public Line(PointCoordinate startPoint, PointCoordinate stopPoint) {
        super(startPoint,stopPoint);
    }

    public Line() {
        super();
    }

    @Override
    public void drawToCanvas(Canvas canvas, Paint paint) {
        canvas.drawLine(
                this.getStartPoint().getX(),
                this.getStartPoint().getY(),
                this.getStopPoint().getX(),
                this.getStopPoint().getY(),
                paint);
    }

    @Override
    public Line clone() {
        return new Line(this.getStartPoint(),this.getStopPoint());
    }

    @Override
    public Drawable newInstance() {
        return new Line();
    }
}
