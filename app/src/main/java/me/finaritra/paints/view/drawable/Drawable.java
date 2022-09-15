package me.finaritra.paints.view.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import me.finaritra.paints.view.PointCoordinate;

public abstract class Drawable {
    private PointCoordinate startPoint;
    private PointCoordinate stopPoint;

    public Drawable(PointCoordinate startPoint, PointCoordinate stopPoint) {
        this.startPoint = startPoint;
        this.stopPoint = stopPoint;
    }

    public Drawable() {
        this(new PointCoordinate(),new PointCoordinate());
    }


    public void setStartPoint(int x, int y) {
        this.startPoint.set(x, y);
    }

    public void setStartPoint(PointCoordinate startPoint) {
        this.startPoint = startPoint;
    }

    public void setStopPoint(PointCoordinate stopPoint) {
        this.stopPoint = stopPoint;
    }

    public void setStopPoint(int x, int y) {
        this.stopPoint.set(x, y);
    }

    public PointCoordinate getStartPoint() {
        return startPoint;
    }

    public PointCoordinate getStopPoint() {
        return stopPoint;
    }

    public void drawToCanvas(Canvas canvas, Paint paint){
        canvas.drawPoint(startPoint.getX(),startPoint.getY(),paint);
    };

    public abstract Drawable clone();
    public abstract Drawable newInstance();

}
