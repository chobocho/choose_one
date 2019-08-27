
package com.chobocho.chooseone.state;

import com.chobocho.chooseone.manager.ChooseManager;

/**
 * 
 */
public class IdleState extends IState {

    /**
     * Default constructor
     */
    public IdleState(ChooseManager manager) {
        this.manager = manager;
        super.Init();
    }

    @Override
    public boolean isIdleState() {
        return true;
    }

    @Override
    public void updatePointList(int point) {
        mPointNum = point;
        if (mPointNum >= 2) {
            manager.transit(IState.SELECTING);
        }
    }

    @Override
    public String toString(){
        return "IdleState";
    }
}