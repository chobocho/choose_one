package com.chobocho.chooseone.manager;

import java.util.List;

public interface ViewObsevrer {
    void OnSetIdleMode();
    void OnSetSelectingMode();
    void OnSetSelectedMode();
    void updatePointList(List<CPoint> list);
}
