package com.chobocho.chooseone.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import com.chobocho.chooseone.ChooseView;
import com.chobocho.chooseone.manager.CPoint;

import java.util.List;

public interface DrawEngine {
    void resumeEngnine();
    void setActivity(AppCompatActivity ac);
    void setListener(ChooseView.ViewListener listener);
    void stopEngnine();
    void updatePoint(int pointCount, List<CPoint> lists);
    void destroy();
}
