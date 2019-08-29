package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.FloatMath;
import android.util.Log;

import com.chobocho.chooseone.manager.CPoint;

import java.util.List;

public class SelectedView implements ChooseView {
    final String LOG_TAG = this.getClass().getSimpleName();
    int tick;
    int direction = 1;

    @Override
    public void init(){
        tick = 0;
        direction = 1;
    }

    @Override
    public void OnDraw(Canvas canvas, List<CPoint> list, int colorTable[]) {
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

        int radius = 200 + tick;
        int smallRadius = radius - 30;

        canvas.drawCircle(point.x, point.y, radius, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(point.x, point.y, smallRadius, paint);
    }
}