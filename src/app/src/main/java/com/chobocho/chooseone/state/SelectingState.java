
package com.chobocho.chooseone.state;

import androidx.annotation.NonNull;
import com.chobocho.chooseone.manager.ChooseManager;

/**
 * 
 */
public class SelectingState extends IState {
    private final int NEXT_TICK = 1;

    /**
     * Default constructor
     */
    public SelectingState(ChooseManager manager) {
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
            manager.transit(IState.ALERTING);
        }
    }

    @Override
    public void updatePointList(int point) {
        if ((mPointNum > 0) && (mPointNum == point)) {
            return;
        }

        mTick = 0;
        mPointNum = point;

        if (mPointNum < 2) {
            manager.transit(IState.IDLE);
        }

    }

    @Override
    @NonNull
    public String toString(){
        return "SelectingState";
    }
}