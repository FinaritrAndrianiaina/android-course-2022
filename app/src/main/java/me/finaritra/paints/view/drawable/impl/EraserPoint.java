package me.finaritra.paints.view.drawable.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import me.finaritra.paints.view.PointCoordinate;
import me.finaritra.paints.view.drawable.Drawable;

public class EraserPoint extends Drawable {
    @Override
    public void drawToCanvas(Canvas canvas, Paint paint){
        Paint p1 = new Paint();
        p1.setStyle(Paint.Style.FILL_AND_STROKE);
        p1.setColor(Color.rgb(255,255,255));
        p1.setAlpha(255);
        p1.setStrokeWidth(paint.getStrokeWidth());
        canvas.drawPoint(this.getStartPoint().getX(),this.getStartPoint().getY(),p1);
    }


    public EraserPoint() {
        super();
    }
    public EraserPoint(PointCoordinate startPoint, PointCoordinate stopPoint) {
        super(startPoint,startPoint);
    }


    @Override
    public EraserPoint clone() {
        return new EraserPoint(this.getStartPoint(),this.getStartPoint());
    }

    @Override
    public Drawable newInstance() {
        return new EraserPoint();
    }
}
