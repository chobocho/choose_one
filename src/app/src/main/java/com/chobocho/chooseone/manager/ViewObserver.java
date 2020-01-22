package com.chobocho.chooseone.manager;

import java.util.List;

public interface ViewObserver {
    void OnSetIdleMode();
    void OnSetSelectingMode();
    void OnSetAlertingMode();
    void OnSetSelectedMode();
    void updatePointList(List<CPoint> list);
}
