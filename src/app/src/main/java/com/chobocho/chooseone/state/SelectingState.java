
package com.chobocho.chooseone.state;

import androidx.annotation.NonNull;
import com.chobocho.chooseone.manager.ChooseManager;

/**
 * 
 */
public class SelectingState extends IState {
    final int NEXT_TICK = 3;

    /**
     * Default constructor
     */
    public SelectingState(ChooseManager manager) {
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

        mPointNum = point;
        if (mPointNum < 2) {
            manager.transit(IState.IDLE);
        }
        mTick = 0;
    }

    @Override
    @NonNull
    public String toString(){
        return "SelectingState";
    }
}