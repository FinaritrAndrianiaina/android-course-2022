package me.finaritra.paints.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import me.finaritra.paints.view.drawable.Drawable;
import me.finaritra.paints.view.drawable.impl.Circle;
import me.finaritra.paints.view.drawable.impl.Line;

public class DrawZoneView extends View {

    int touchX = -1;
    int touchY = -1;
    LinkedList<Pair<Drawable, Paint>> drawableList = new LinkedList<>();
    public static Drawable tmpDrawable = new Line();
    public Paint tmpPaint = new Paint();

    String CLASS_NAME = this.getClass().getName();
    int[] colorList = new int[]{Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.GRAY, Color.CYAN};
    GestureDetector gestureDetector;
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
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.d(CLASS_NAME, "DOUBLE TAP");
                if(!drawableList.isEmpty()) {
                    drawableList.pop();
                }
                self.invalidate();
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        tmpDrawable.drawToCanvas(canvas,tmpPaint);
        for (Pair<Drawable, Paint> drawablePair : drawableList) {
            drawablePair.first.drawToCanvas(canvas, drawablePair.second);
        }
    }

    public int getRandomColor() {
        Random random = new Random();
        return colorList[random.nextInt(colorList.length)];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchY = (int) event.getY();
        touchX = (int) event.getX();
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tmpPaint.setColor(this.getRandomColor());
                tmpDrawable.setStartPoint(touchX,touchY);
                tmpDrawable.setStopPoint(touchX,touchY);
            case MotionEvent.ACTION_MOVE:
                tmpDrawable.setStopPoint(touchX,touchY);
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(CLASS_NAME, "ACTION WAS UP");
                drawableList.push(new Pair<>(tmpDrawable.clone(),new Paint(tmpPaint)));
                tmpDrawable = tmpDrawable.newInstance();
                break;
        }
        return true;
    }



}
