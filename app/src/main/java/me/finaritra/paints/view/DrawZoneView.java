package me.finaritra.paints.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import me.finaritra.paints.view.drawable.Drawable;
import me.finaritra.paints.view.drawable.impl.Circle;
import me.finaritra.paints.view.drawable.impl.EraserPoint;
import me.finaritra.paints.view.drawable.impl.Line;
import me.finaritra.paints.view.drawable.impl.Point;

public class DrawZoneView extends View {

    int touchX = -1;
    int touchY = -1;
    public LinkedList<Pair<Drawable, Paint>> drawableList = new LinkedList<>();
    public static Drawable tmpDrawable = new Line();
    public Paint tmpPaint = new Paint();

    String CLASS_NAME = this.getClass().getName();
    int[] colorList = new int[]{Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.GRAY, Color.CYAN};
    DrawZoneView self;


    public DrawZoneView(Context context) {
        super(context);
        //rectList.add(new Rect(0, 0, 500, 500));
    }

    public DrawZoneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tmpPaint.setColor(Color.BLACK);
        tmpPaint.setStyle(Paint.Style.STROKE);
        tmpPaint.setStrokeWidth(5);
        self = this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Pair<Drawable, Paint> drawablePair : drawableList) {
            drawablePair.first.drawToCanvas(canvas, drawablePair.second);
        }
        tmpDrawable.drawToCanvas(canvas, tmpPaint);
    }

    public int getRandomColor() {
        Random random = new Random();
        return colorList[random.nextInt(colorList.length)];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchY = (int) event.getY();
        touchX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tmpDrawable.setStartPoint(touchX, touchY);
                tmpDrawable.setStopPoint(touchX, touchY);
                if ((tmpDrawable instanceof Point) || (tmpDrawable instanceof EraserPoint)) {
                    drawableList.add(new Pair<>(tmpDrawable.clone(), new Paint(tmpPaint)));
                    this.invalidate();
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                if (!((tmpDrawable instanceof Point) || (tmpDrawable instanceof EraserPoint))) {
                    tmpDrawable.setStopPoint(touchX, touchY);
                } else {
                    tmpDrawable.setStartPoint(touchX, touchY);
                    drawableList.add(new Pair<>(tmpDrawable.clone(), new Paint(tmpPaint)));
                    tmpDrawable = tmpDrawable.newInstance();
                }
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(CLASS_NAME, "ACTION WAS UP");
                drawableList.add(new Pair<>(tmpDrawable.clone(), new Paint(tmpPaint)));
                tmpDrawable = tmpDrawable.newInstance();
                break;
        }
        return true;
    }

    LinkedList<Pair<Drawable, Paint>> redoList = new LinkedList<>();

    public void undo() {
        if (this.drawableList.size() > 0) {
            redoList.add(this.drawableList.removeLast());
            this.invalidate();
        }
    }

    public void redo() {
        if (redoList.size() > 0) {
            drawableList.add(this.redoList.removeLast());
            this.invalidate();
        }
    }

    public void clean() {
        drawableList = new LinkedList<>();
        tmpDrawable = new Line();
        this.invalidate();
    }
}
