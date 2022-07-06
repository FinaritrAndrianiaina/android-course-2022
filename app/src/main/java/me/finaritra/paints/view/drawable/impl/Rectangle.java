package me.finaritra.paints.view.drawable.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import me.finaritra.paints.view.PointCoordinate;
import me.finaritra.paints.view.drawable.Drawable;

public class Rectangle extends Drawable {
    public Rectangle(PointCoordinate startPoint, PointCoordinate stopPoint) {
        super(startPoint, stopPoint);
    }

    public Rectangle() {
        super();
    }

    @Override
    public void drawToCanvas(Canvas canvas, Paint paint) {
        Rect rect = new Rect(this.getStartPoint().getX(),this.getStartPoint().getY(),this.getStopPoint().getX(), this.getStopPoint().getY());
        canvas.drawRect(rect,paint);
    }

    @Override
    public Rectangle clone() {
        return new Rectangle(this.getStartPoint(),this.getStopPoint());
    }
}
