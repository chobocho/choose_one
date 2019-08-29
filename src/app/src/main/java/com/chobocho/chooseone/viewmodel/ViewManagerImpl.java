package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;

import com.chobocho.chooseone.manager.CPoint;
import com.chobocho.chooseone.manager.ViewObsevrer;

import java.util.List;

public class ViewManagerImpl implements ViewManager, ViewObsevrer {
    ChooseView view;
    ChooseView idleView;
    ChooseView selectingView;
    ChooseView selectedView;

    public ViewManagerImpl() {
        idleView = new IdleView();
        selectingView = new SelectingView();
        selectedView = new SelectedView();
        view = idleView;
    }

    @Override
    public void OnDraw(Canvas canvas, List<CPoint> list, int colorTable[]) {
        if (view == null) {
            return;
        }
        view.OnDraw(canvas, list, colorTable);
    }

    @Override
    public void OnSetIdleMode() {
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
}
