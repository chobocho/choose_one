package com.chobocho.chooseone.viewmodel;

import android.graphics.Canvas;
import com.chobocho.chooseone.manager.CPoint;
import java.util.List;

public interface ViewManager {
    void OnDraw(Canvas canvas, List<CPoint> list, int colorTable[]);
}
