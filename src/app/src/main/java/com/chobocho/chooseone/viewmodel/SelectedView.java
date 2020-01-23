package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.chobocho.chooseone.manager.CPoint;

import java.util.ArrayList;
import java.util.List;

public class SelectedView implements ChooseView {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private final static int CENTER_TYPE = 0;
    private final static int HOLE_TYPE = 1;
    private int screenWidth;
    private int FINGER_RADIUS;
    private int tick;
    private int direction = 1;
    private List<CPoint> list;
    private int drawType = CENTER_TYPE;

    public SelectedView(int screenWidth) {
        this.screenWidth = screenWidth;
        FINGER_RADIUS = (int)this.screenWidth / 5;
    }

    @Override
    public void init(){
        tick = 0;
        direction = 1;
        drawType =  (Math.random() > 0.61) ?  CENTER_TYPE : HOLE_TYPE;
    }

    @Override
    public void updatePointList(List<CPoint> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public void OnDraw(Canvas canvas, int[] colorTable) {
        if (drawType == CENTER_TYPE) {
            OnDrawCenterType(canvas, colorTable);
        } else {
            OnDrawHoleType(canvas, colorTable);
        }
    }

    private void OnDrawCenterType(Canvas canvas, int[] colorTable) {
        if ((list == null) || (list.size() == 0)) {
            return;
        }

        tick += direction;

        if (tick > 20) {
            direction = -1;
        } else if (tick <= 0) {
            direction = 1;
        }

        Paint paint = new Paint();

        CPoint point = list.get(0);
        Log.d(LOG_TAG, "Has chosen!" + point.toString());

        int circleWidth = 30;
        int radius = FINGER_RADIUS * 3 + tick;
        int smallRadius = radius - circleWidth;
        int gap = FINGER_RADIUS / 10;

        for (int i = 8; i >= 0; --i) {
            paint.setColor(colorTable[point.color]);
            canvas.drawCircle(point.x, point.y, radius, paint);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(point.x, point.y, smallRadius, paint);
            radius = radius - circleWidth - gap;
            smallRadius = radius - circleWidth;

            if (radius < 0 || smallRadius < 0) {
                break;
            }
        }
    }

    private void OnDrawHoleType(Canvas canvas, int[] colorTable) {
        if ((list == null) || (list.size() == 0)) {
            return;
        }

        tick += direction;

        if (tick > 20) {
            direction = -1;
        } else if (tick <= 0) {
            direction = 1;
        }

        Paint paint = new Paint();

        CPoint point = list.get(0);
        Log.d(LOG_TAG, "Has chosen!" + point.toString());
        paint.setColor(colorTable[point.color]);

        int width = 3000;

        for (int angle = 0; angle < 360; angle += 15) {
            float radianAngle = (float) Math.toRadians(angle);

            float endX = point.x + width * (float)Math.cos(radianAngle);
            float endY = point.y + width * (float)Math.sin(radianAngle);
            canvas.drawLine(point.x, point.y, endX, endY, paint);
        }

        int radius = FINGER_RADIUS + tick;
        int smallRadius = radius - 30;

        canvas.drawCircle(point.x, point.y, radius, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(point.x, point.y, smallRadius, paint);
    }
}
