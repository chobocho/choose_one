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
    public static final String CHOOSE_MANAGER = "ChooseManager";
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
                if (observer != null) {
                    observer.OnSetIdleMode();
                }
                break;
            case IState.SELECTING:
                setState(selectingState);
                if (observer != null) {
                    observer.OnSetSelectingMode();
                    observer.updatePointList(pointList);
                }
                break;
            case IState.ALERTING:
                setState(alertingState);
                if (observer != null) {
                    observer.OnSetAlertingMode();
                    observer.updatePointList(pointList);
                }
                break;
            case IState.SELECTED:
                setState(selectedState);
                if (observer != null) {
                    observer.OnSetSelectedMode();
                    observer.updatePointList(pointList);
                }
                break;
        }
        // Log.d(CHOOSE_MANAGER, "State: " + state);
    }

    public void updatePoint(int pointCount, List<CPoint> list) {
        Log.d(CHOOSE_MANAGER, "updatePoint " + pointCount + " " + list.size());
        if (state == selectedState) {
            state.updatePointList(pointCount);
            return;
        }
        state.updatePointList(pointCount);
        pointList = list;
        observer.updatePointList(list);
    }

    private void setState(IState nextState) {
        // Log.d(CHOOSE_MANAGER, nextState.toString());
        state = nextState;
        state.Init();
    }

    public void choosePoint() {
        Random rnd = new Random();
        if (pointList == null || pointList.isEmpty()) {
            return;
        }

        int rndCount  = rnd.nextInt(10) + 5;
        int selectNum = rnd.nextInt(pointList.size());

        while (--rndCount > 0) {
            selectNum = rnd.nextInt(pointList.size());
        }

        CPoint chosenPoint = new CPoint(pointList.get(selectNum));
        chosenPoint.color = selectNum;

        pointList.clear();
        pointList.add(chosenPoint);

        Log.d(CHOOSE_MANAGER, "ChoosePoint : " + chosenPoint.toString());
    }

    public void registerObserver(ViewObserver observer) {
        this.observer = observer;
    }

    @Override
    public String toString() {
        if (state == null) {
            return "";
        }
        return state.toString();
    }
}
