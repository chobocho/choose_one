package com.chobocho.chooseone.manager;

import android.util.Log;

import com.chobocho.chooseone.state.IState;
import com.chobocho.chooseone.state.IdleState;
import com.chobocho.chooseone.state.SelectedState;
import com.chobocho.chooseone.state.SelectingState;

import java.util.List;
import java.util.Random;

public class ChooseManagerImpl implements ChooseManager, ChooseManagerObserver {
    private IState state;
    private IState idleState;
    private IState SelectingState;
    private IState SelectedState;
    private ViewObsevrer observer;
    List<CPoint> pointList;

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
                observer.updatePointList(pointList);
                break;
            case IState.SELECTED:
                setState(SelectedState);
                observer.OnSetSelectedMode();
                observer.updatePointList(pointList);
                break;
        }
    }

    public void updatePoint(int pointCount, List<CPoint> list) {
        Log.d("ChooseManager", "updatePoint " + pointCount);
        if (state == SelectedState) {
            state.updatePointList(pointCount);
            return;
        }
        state.updatePointList(pointCount);
        pointList = list;
        observer.updatePointList(list);
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
        CPoint chosenPoint = new CPoint(pointList.get(selectNum));
        chosenPoint.color = selectNum;

        pointList.clear();
        pointList.add(chosenPoint);

        Log.d("ChooseManager", "ChoosePoint : " + chosenPoint.toString());
    }

    public void registerObserver(ViewObsevrer obsevrer) {
        this.observer = obsevrer;
    }
}
