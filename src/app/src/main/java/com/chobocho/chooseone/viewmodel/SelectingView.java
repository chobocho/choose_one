package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.chobocho.chooseone.manager.CPoint;

import java.util.ArrayList;
import java.util.List;

public class SelectingView implements ChooseView{
    final  String LOG_TAG = this.getClass().getSimpleName();
    private int screenWidth;
    private int FINGER_RADIUS;
    int tick;
    int direction = 1;
    List<CPoint> list;

    public SelectingView(int screenWidth) {
        this.screenWidth = screenWidth;
        FINGER_RADIUS = (int)this.screenWidth / 6;
    }

    @Override
    public void init(){
        tick = 0;
        direction = 1;
    }

    @Override
    public void updatePointList(List<CPoint> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public void OnDraw(Canvas canvas, int[] colorTable) {
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

        int radius = FINGER_RADIUS + tick;
        int smallRadius = radius - 30;

        for (CPoint point : list) {
            Log.d(LOG_TAG, point.toString());

            paint.setColor(colorTable[point.id]);
            canvas.drawCircle(point.x, point.y, radius+30, paint);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(point.x, point.y, smallRadius+40, paint);

            paint.setColor(colorTable[point.id]);
            canvas.drawCircle(point.x, point.y, radius, paint);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(point.x, point.y, smallRadius, paint);
        }
    }
}
