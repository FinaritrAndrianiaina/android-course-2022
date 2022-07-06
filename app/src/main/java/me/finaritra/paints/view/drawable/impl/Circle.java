package me.finaritra.paints.view.drawable.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import me.finaritra.paints.view.PointCoordinate;
import me.finaritra.paints.view.drawable.Drawable;

public class Circle extends Drawable {

    public Circle(PointCoordinate startPoint, PointCoordinate stopPoint) {
        super(startPoint,stopPoint);
    }

    public Circle() {
        super();
    }

    @Override
    public void drawToCanvas(Canvas canvas, Paint paint) {
        PointCoordinate startPoint = this.getStartPoint();
        PointCoordinate stopPoint = this.getStopPoint();
        RectF rectF = new RectF(startPoint.getX(),startPoint.getY(),stopPoint.getX(),stopPoint.getY());
        canvas.drawOval(rectF,paint);
    }

    @Override
    public Circle clone() {
        return new Circle(this.getStartPoint(),this.getStopPoint());
    }
}
