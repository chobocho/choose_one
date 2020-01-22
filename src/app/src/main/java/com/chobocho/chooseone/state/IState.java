package com.chobocho.chooseone.state;

import com.chobocho.chooseone.manager.ChooseManager;

/**
 * 
 */
public abstract class IState {
    public static final int IDLE = 0;
    public static final int SELECTING = 1;
    public static final int ALERTING = 2;
    public static final int SELECTED = 3;

    ChooseManager manager;
    int mTick;
    int mPointNum;

    IState() {

    }

    public void Init() {
        mTick = 0;
    }

    public void tick() {
        // TODO implement here
    }

    public void updatePointList(int point) {
        // TODO implement here
    }

}