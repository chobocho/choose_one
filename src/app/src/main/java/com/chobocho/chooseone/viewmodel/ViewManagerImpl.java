package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;
import android.graphics.Color;

import com.chobocho.chooseone.manager.CPoint;
import com.chobocho.chooseone.manager.ViewObserver;

import java.util.List;
import java.util.Random;

public class ViewManagerImpl implements ViewManager, ViewObserver {
    ChooseView view;
    ChooseView idleView;
    ChooseView selectingView;
    ChooseView selectedView;

    int [] colorTable = new int[20];

    public ViewManagerImpl() {
        generatorColorTable();
        idleView = new IdleView();
        selectingView = new SelectingView();
        selectedView = new SelectedView();
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

}
