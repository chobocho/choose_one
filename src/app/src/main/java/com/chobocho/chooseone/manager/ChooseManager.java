package com.chobocho.chooseone.manager;
import java.util.List;

public interface ChooseManager {
    void tick();
    void transit(int state);
    void updatePoint(int pointCount, List<CPoint> lists);
    List<CPoint> getPointList();
    void choosePoint();
    void registerObserver(ViewObsevrer obsevrer);
}
