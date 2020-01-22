package com.chobocho.chooseone.state;

import androidx.annotation.NonNull;

import com.chobocho.chooseone.manager.ChooseManager;

public class AlertingState extends IState {
    private final int NEXT_TICK = 1;

    /**
     * Default constructor
     */
    public AlertingState(ChooseManager manager) {
        this.manager = manager;
        super.Init();
    }
    
    public void tick() {
        mTick++;
        if (mTick > NEXT_TICK) {
            manager.transit(IState.SELECTED);
        }
    }

    @Override
    public void updatePointList(int point) {
        if (mPointNum == point) {
            return;
        }

        if (point < 2) {
            manager.transit(IState.IDLE);
            mTick = 0;
            return;
        }

        if (mPointNum !=  point) {
            manager.transit(IState.SELECTING);
            mPointNum = point;
        }
        mTick = 0;
    }

    @Override
    @NonNull
    public String toString(){
        return "AlertingState";
    }
}
