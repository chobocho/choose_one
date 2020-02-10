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

    @Override
    public void Init() {
        super.Init();
        mPointNum = 0;
    }

    public void tick() {
        mTick++;
        if (mTick > NEXT_TICK) {
            manager.transit(IState.SELECTED);
        }
    }

    @Override
    public void updatePointList(int point) {
        if ((mPointNum > 0) && (mPointNum == point)) {
            return;
        }

        mTick = 0;

        if (point < 2) {
            manager.transit(IState.IDLE);
            mTick = 0;
            return;
        }

        if (mPointNum == 0) {
            mPointNum = point;
        } else if (mPointNum !=  point) {
            manager.transit(IState.SELECTING);
        }
    }

    @Override
    @NonNull
    public String toString(){
        return "AlertingState";
    }
}
