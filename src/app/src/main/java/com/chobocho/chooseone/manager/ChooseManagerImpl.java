package com.chobocho.chooseone.manager;

import android.util.Log;

import com.chobocho.chooseone.state.IState;
import com.chobocho.chooseone.state.IdleState;
import com.chobocho.chooseone.state.SelectedState;
import com.chobocho.chooseone.state.SelectingState;

import java.util.List;
import java.util.Random;


public class ChooseManagerImpl implements ChooseManager {
    private IState state;
    private IState idleState;
    private IState SelectingState;
    private IState SelectedState;
    private ViewObsevrer observer;
    List<CPoint> pointList;
    CPoint chosenePoint;

    public ChooseManagerImpl() {
        idleState = new IdleState(this);
        SelectingState = new SelectingState(this);
        SelectedState = new SelectedState(this);
        state = idleState;
    }

    public void tick() {
        if (state == null) {
            return;
        }
        state.tick();
    }

    public void transit(int state) {
        switch (state) {
            case IState.IDLE:
                setState(idleState);
                observer.OnSetIdleMode();
                break;
            case IState.SELECTING:
                setState(SelectingState);
                observer.OnSetSelectingMode();
                break;
            case IState.SELECTED:
                setState(SelectedState);
                observer.OnSetSelectedMode();
                break;
        }
    }

    public void updatePoint(int pointCount, List<CPoint> list) {
        Log.d("ChooseManager", "updatePoint " + pointCount);
        if (state == SelectedState) {
            return;
        }
        state.updatePointList(pointCount);
        pointList = list;
    }

    private void setState(IState nextState) {
        Log.d("ChooseManager", nextState.toString());
        state = nextState;
        state.Init();
    }

    public List<CPoint> getPointList() {
        return pointList;
    }

    public void choosePoint() {
        Random rnd = new Random();
        int selectNum = rnd.nextInt(pointList.size());
        chosenePoint = new CPoint(pointList.get(selectNum));
        chosenePoint.color = selectNum;

        pointList.clear();
        pointList.add(chosenePoint);

        Log.d("ChooseManager", "ChoosePoint : " + chosenePoint.toString());
    }

    public void registerObserver(ViewObsevrer obsevrer) {
        this.observer = obsevrer;
    }
}
