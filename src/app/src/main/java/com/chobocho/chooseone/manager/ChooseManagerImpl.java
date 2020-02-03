package com.chobocho.chooseone.manager;

import android.util.Log;

import com.chobocho.chooseone.state.AlertingState;
import com.chobocho.chooseone.state.IState;
import com.chobocho.chooseone.state.IdleState;
import com.chobocho.chooseone.state.SelectedState;
import com.chobocho.chooseone.state.SelectingState;

import java.util.List;
import java.util.Random;

public class ChooseManagerImpl implements ChooseManager, ChooseManagerObserver {
    private IState state;
    private final IState idleState;
    private final IState selectingState;
    private final IState alertingState;
    private final IState selectedState;
    private ViewObserver observer;
    private List<CPoint> pointList;

    public ChooseManagerImpl() {
        idleState = new IdleState(this);
        selectingState = new SelectingState(this);
        alertingState = new AlertingState(this);
        selectedState = new SelectedState(this);
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
                setState(selectingState);
                observer.OnSetSelectingMode();
                observer.updatePointList(pointList);
                break;
            case IState.ALERTING:
                setState(alertingState);
                observer.OnSetAlertingMode();
                observer.updatePointList(pointList);
                break;
            case IState.SELECTED:
                setState(selectedState);
                observer.OnSetSelectedMode();
                observer.updatePointList(pointList);
                break;
        }
    }

    public void updatePoint(int pointCount, List<CPoint> list) {
        Log.d("ChooseManager", "updatePoint " + pointCount);
        if (state == selectedState) {
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

    public void choosePoint() {
        Random rnd = new Random();
        if (pointList == null || pointList.isEmpty()) {
            return;
        }
        int selectNum = rnd.nextInt(pointList.size());
        CPoint chosenPoint = new CPoint(pointList.get(selectNum));
        chosenPoint.color = selectNum;

        pointList.clear();
        pointList.add(chosenPoint);

        Log.d("ChooseManager", "ChoosePoint : " + chosenPoint.toString());
    }

    public void registerObserver(ViewObserver observer) {
        this.observer = observer;
    }
}
