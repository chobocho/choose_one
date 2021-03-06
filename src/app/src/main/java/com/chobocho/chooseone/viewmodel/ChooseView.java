package com.chobocho.chooseone.viewmodel;

import com.chobocho.chooseone.manager.CPoint;
import android.graphics.Canvas;
import java.util.List;

interface ChooseView {
    void init();
    void OnDraw(Canvas canvas, int[] colorTable);
    void updatePointList(List<CPoint> list);
}
