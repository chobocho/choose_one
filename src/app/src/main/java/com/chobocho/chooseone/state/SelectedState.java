
package com.chobocho.chooseone.state;

import androidx.annotation.NonNull;
import com.chobocho.chooseone.manager.ChooseManager;

/**
 * 
 */
public class SelectedState extends IState {
    private final int NEXT_TICK = 2;

    /**
     * Default constructor
     */
    public SelectedState(ChooseManager manager) {
        this.manager = manager;
        super.Init();
    }

    @Override
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
    @NonNull
    public String toString(){
        return "SelectedState";
    }
}