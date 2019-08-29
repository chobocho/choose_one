package com.chobocho.chooseone.viewmodel;

import com.chobocho.chooseone.manager.CPoint;
import android.graphics.Canvas;
import java.util.List;

interface ChooseView {
    void init();
    void OnDraw(Canvas canvas, List<CPoint> lists, int colorTable[]);
}
