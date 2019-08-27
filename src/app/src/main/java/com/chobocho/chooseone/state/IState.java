
package com.chobocho.chooseone.state;

import com.chobocho.chooseone.manager.ChooseManager;

/**
 * 
 */
public abstract class IState {
    public static final int IDLE = 0;
    public static final int SELECTING = 1;
    public static final int SELECTED = 2;

    ChooseManager manager;
    int mTick;
    int mPointNum;

    /**
     * Default constructor
     */
    public IState() {

    }

    public void Init() {
        mTick = 0;
        mPointNum = 0;
    }

    /**
     * 
     */
    public void tick() {
        // TODO implement here
    }

    /**
     * 
     */
    public void updatePointList() {
        // TODO implement here
    }

    /**
     * 
     */
    public boolean isIdleState() {
        // TODO implement here
        return false;
    }

    /**
     * 
     */
    public boolean IsSelectingState() {
        // TODO implement here
        return false;
    }

    public boolean IsSelectedState() {
        // TODO implement here
        return false;
    }

    public void updatePointList(int point) {
        // TODO implement here
    }

    public boolean hasChoosen() {
        return false;
    }
}