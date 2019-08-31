package com.chobocho.chooseone.manager;

import java.util.List;

public interface ChooseManagerObserver {
    void tick();
    void updatePoint(int pointCount, List<CPoint> lists);
    void registerObserver(ViewObserver observer);
}

