package com.chobocho.chooseone.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.chobocho.chooseone.manager.CPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlertingView implements ChooseView{
    final  String LOG_TAG = this.getClass().getSimpleName();
    private int screenWidth;
    private int screenHeight;
    private int FINGER_RADIUS;
    int tick;
    int direction = 1;
    List<CPoint> list;
    Bitmap mBigNumber;

    Paint mPaint4BigNumber;
    public AlertingView(int screenWidth, int screenHeight, Bitmap bigNumber) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        FINGER_RADIUS = (int)this.screenWidth / 6;

        if (bigNumber != null) {
            // bigNumber.getWidth() : bigNumber.getHeight() = screenWidth / 3 : ?
            int nx = screenWidth / 2;
            int ny = (bigNumber.getHeight() * nx) / bigNumber.getWidth();
            mBigNumber = Bitmap.createScaledBitmap(bigNumber, nx, ny, true);
        }

        mPaint4BigNumber = new Paint();
        mPaint4BigNumber.setColor(Color.WHITE);
        mPaint4BigNumber.setStrokeWidth(3);
        mPaint4BigNumber.setAntiAlias(true);
        mPaint4BigNumber.setAlpha(80);
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

        if (mBigNumber != null) {
            int textX = (screenWidth - mBigNumber.getWidth()) / 2;
            int textY = (screenHeight - mBigNumber.getHeight()) / 2;

            canvas.drawBitmap(mBigNumber, textX,  textY, mPaint4BigNumber);
        }
        
        Paint paint = new Paint();

        int radius = FINGER_RADIUS + tick;
        int smallRadius = radius - 30;

        for (CPoint point : list) {
            Log.d(LOG_TAG, point.toString());

            paint.setColor(colorTable[point.id]);
            canvas.drawCircle(point.x, point.y, radius, paint);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(point.x, point.y, smallRadius, paint);
        }
    }
}
