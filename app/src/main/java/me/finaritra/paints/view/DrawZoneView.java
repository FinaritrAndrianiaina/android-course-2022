package me.finaritra.paints.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.finaritra.paints.view.drawable.Drawable;
import me.finaritra.paints.view.drawable.impl.Circle;
import me.finaritra.paints.view.drawable.impl.Line;

public class DrawZoneView extends View {

    int touchX = -1;
    int touchY = -1;
    List<Pair<Drawable,Paint>> drawableList = new ArrayList<>();
    Drawable tmpDrawable = new Line();

    Paint tmpPaint = new Paint();
    String CLASS_NAME = this.getClass().getName();
    boolean toggle = false;
    int[] colorList = new int[]{Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.GRAY, Color.CYAN};


    public DrawZoneView(Context context) {
        super(context);
        //rectList.add(new Rect(0, 0, 500, 500));
        tmpPaint.setColor(Color.BLACK);
        tmpPaint.setStyle(Paint.Style.STROKE);
        tmpPaint.setStrokeWidth(5);
    }

    public DrawZoneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tmpPaint.setColor(Color.BLACK);
        tmpPaint.setStyle(Paint.Style.STROKE);
        tmpPaint.setStrokeWidth(5);
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
                drawableList.add( new Pair<>(tmpDrawable.clone(),new Paint(tmpPaint)));
                toggle= !toggle;
                if(toggle) {
                    tmpDrawable = new Circle();
                } else {
                    tmpDrawable = new Line();
                }
                break;
        }
        return true;
    }


}
