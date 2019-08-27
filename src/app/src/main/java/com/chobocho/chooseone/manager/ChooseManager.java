package com.chobocho.chooseone.manager;

import com.chobocho.chooseone.state.IState;

import java.util.List;

public interface ChooseManager {
    public void tick();
    public void transit(int state);
    public void updatePoint(int pointCount, List<CPoint> lists);
    public List<CPoint> getPointList();
    public boolean hasChoosen();
    public void choosePoint();
    public CPoint getChosenPoint();
}
