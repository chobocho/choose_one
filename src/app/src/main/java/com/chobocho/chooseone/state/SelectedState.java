
package com.chobocho.chooseone.state;

import com.chobocho.chooseone.manager.ChooseManager;

/**
 * 
 */
public class SelectedState extends IState {
    final int NEXT_TICK = 4;

    /**
     * Default constructor
     */
    public SelectedState(ChooseManager manager) {
        this.manager = manager;
        super.Init();
    }

    public void Init() {
        super.Init();
        this.manager.choosePoint();
    }

    public void tick() {
        mTick++;
        if (mTick > NEXT_TICK && mPointNum < 2) {
            manager.transit(IState.IDLE);
        }
    }

    @Override
    public void updatePointList(int point) {
        mPointNum = point;
    }

    @Override
    public String toString(){
        return "SelectedState";
    }
}