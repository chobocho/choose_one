package com.chobocho.chooseone.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.chobocho.chooseone.R;
import com.chobocho.chooseone.manager.CPoint;
import com.chobocho.chooseone.manager.ViewObserver;

import java.util.List;
import java.util.Random;

public class ViewManagerImpl implements ViewManager, ViewObserver {
    ChooseView view;
    final ChooseView idleView;
    final ChooseView selectingView;
    final ChooseView alertingView;
    final ChooseView selectedView;

    final int [] colorTable = new int[20];
    final Bitmap[] mBigNumber = new Bitmap[2];

    public ViewManagerImpl(Context context, int screenWidth, int screenHeight) {
        generatorColorTable();

        String[] message = new String[2];
        message[0] = context.getResources().getString(R.string.raise_finger_msg1);
        message[1] = context.getResources().getString(R.string.raise_finger_msg2);

        mBigNumber[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.n01);
        mBigNumber[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.n02);

        idleView = new IdleView(screenWidth, message);
        selectingView = new SelectingView(screenWidth, screenHeight, mBigNumber[1]);
        alertingView = new AlertingView(screenWidth, screenHeight,  mBigNumber[0]);
        selectedView = new SelectedView(context, screenWidth);
        view = idleView;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        if (view == null) {
            return;
        }
        view.OnDraw(canvas, colorTable);
    }

    @Override
    public void OnSetIdleMode() {
        generatorColorTable();
        view = idleView;
        view.init();
    }

    @Override
    public void OnSetSelectingMode() {
        view = selectingView;
        view.init();
    }

    @Override
    public void OnSetAlertingMode() {
        view = alertingView;
        view.init();
    }

    @Override
    public void OnSetSelectedMode() {
        view = selectedView;
        view.init();
    }

    @Override
    public void updatePointList(List<CPoint> list) {
        view.updatePointList(list);
    }


    public void generatorColorTable() {
        for (int i = 0; i < 20; i++) {
            colorTable[i] = getRandomColor();
        }
    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, 56 + rnd.nextInt(200), 56 + rnd.nextInt(200), 56 + rnd.nextInt(200));
    }

    public void recycle() {
        if (mBigNumber[0] != null) {
            mBigNumber[0].recycle();
            mBigNumber[0] = null;
        }
        if (mBigNumber[1] != null) {
            mBigNumber[1].recycle();
            mBigNumber[1] = null;
        }
    }

}
