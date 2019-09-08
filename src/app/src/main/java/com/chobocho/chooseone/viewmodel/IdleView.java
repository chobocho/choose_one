package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;
import com.chobocho.chooseone.manager.CPoint;
import java.util.List;

public class IdleView implements ChooseView {
    private int screenWidth;

    public IdleView(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    @Override
    public void init(){

    }

    @Override
    public void updatePointList(List<CPoint> list) {

    }

    @Override
    public void OnDraw(Canvas canvas, int[] colorTable) {

    }
}
