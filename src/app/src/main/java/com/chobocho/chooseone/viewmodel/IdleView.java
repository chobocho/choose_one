package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.chobocho.chooseone.R;
import com.chobocho.chooseone.manager.CPoint;

import java.util.ArrayList;
import java.util.List;

public class IdleView implements ChooseView {
    private int screenWidth;
    private String[] raise_finger;
    private int tick;
    private int direction = 1;
    private List<CPoint> list;
    private int FINGER_RADIUS = 0;

    public IdleView(int screenWidth, String[] message) {
        raise_finger = new String[2];
        this.screenWidth = screenWidth;
        raise_finger[0] = message[0];
        raise_finger[1] = message[1];
        FINGER_RADIUS = (int) this.screenWidth / 6;
    }

    @Override
    public void init(){
        tick = 0;
        direction = 1;
        this.list = new ArrayList<>();
    }

    @Override
    public void updatePointList(List<CPoint> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public void OnDraw(Canvas canvas, int[] colorTable) {
        tick += direction;
        if (tick > 20) {
            direction = -1;
        } else if (tick <= 0) {
            direction = 1;
        }

        Paint paint = new Paint();
        paint.setColor(Color.CYAN);

        int textSize = screenWidth/20;
        int textX = screenWidth/6 + tick;
        int textY = screenWidth/2 - tick;
        paint.setTextSize(textSize);
        canvas.drawText(raise_finger[0], textX, textY, paint);
        canvas.drawText(raise_finger[1], textX, textY+textSize*2, paint);

        if ((list == null) || (list.size() == 0)) {
            return;
        }

        int radius = FINGER_RADIUS + tick;
        int smallRadius = radius - 30;

        for (CPoint point : list) {
            paint.setColor(colorTable[point.id]);
            RectF rect = new RectF(point.x-radius, point.y-radius,point.x+radius, point.y + radius);
            canvas.drawArc(rect, 270, tick*18, true, paint);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(point.x, point.y, smallRadius, paint);
        }
    }
}
