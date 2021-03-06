package com.chobocho.chooseone.viewmodel;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chobocho.chooseone.ChooseView;
import com.chobocho.chooseone.manager.CPoint;
import com.chobocho.chooseone.manager.ChooseManagerObserver;

import java.util.ArrayList;
import java.util.List;

public class DrawEngineImpl implements DrawEngine {
    private final  String LOG_TAG = this.getClass().getSimpleName();

    private final int REFRESH_TIMER = 30;
    private final int TICK_TIMER = 800;
    private final int NO_TOUCH_TIMER = 60000;

    private final int UPDATE_SCREEN = 1001;
    private final int PRESS_KEY = 1002;
    private final int UPDATE_TICK = 1003;
    private final int NO_TOUCH_TIMER_EXPIRED = 1004;

    private AppCompatActivity activity;
    private final ChooseManagerObserver manager;
    private ChooseView.ViewListener listener;
    private final Context mContext;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d(LOG_TAG, "There is event : " + msg.what);
            switch(msg.what) {
                case PRESS_KEY:
                    int pointCount = msg.arg1;
                    List<CPoint> list = (ArrayList<CPoint>) msg.obj;
                    manager.updatePoint(pointCount, list);
                    if (!mHandler.hasMessages(UPDATE_SCREEN)) {
                        update();
                    }
                    if (!mHandler.hasMessages(UPDATE_TICK)) {
                        increaseTick();
                    }
                    if (pointCount <= 1) {
                        if (!mHandler.hasMessages(NO_TOUCH_TIMER_EXPIRED)) {
                            Log.d(LOG_TAG, "Start timer: NO_TOUCH_TIMER_EXPIRED");
                            Message message= new Message();
                            message.what = NO_TOUCH_TIMER_EXPIRED;
                            mHandler.sendMessageDelayed(message, NO_TOUCH_TIMER);
                        }
                    } else {
                        if (mHandler.hasMessages(NO_TOUCH_TIMER_EXPIRED)) {
                            mHandler.removeMessages(NO_TOUCH_TIMER_EXPIRED);
                            Log.d(LOG_TAG, "Remove timer: NO_TOUCH_TIMER_EXPIRED");
                        }
                    }
                    break;
                case UPDATE_SCREEN:
                    if (manager == null) {
                        return;
                    }
                    update();
                    break;
                case UPDATE_TICK:
                    manager.tick();
                    increaseTick();
                    break;
                case NO_TOUCH_TIMER_EXPIRED:
                    Log.d(LOG_TAG, "Timer expired: NO_TOUCH_TIMER_EXPIRED");
                    finishApp();
                    break;
            }
        }
    };

    public DrawEngineImpl(Context context, ChooseManagerObserver manager) {
        this.mContext = context;
        this.manager = manager;
    }

    @Override
    public void resumeEngnine(){
        Log.d(LOG_TAG, "Resume drawEngine");
        if (mHandler == null) {
            return;
        }

        if (!mHandler.hasMessages(UPDATE_SCREEN)) {
            update();
        }

        if (!mHandler.hasMessages(UPDATE_TICK)) {
            increaseTick();
        }
    }


    @Override
    public void setListener(ChooseView.ViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void setActivity(AppCompatActivity ac) {
        this.activity = ac;
    }

    @Override
    public void stopEngnine(){
        if (mHandler == null) {
            return;
        }

        if (mHandler.hasMessages(UPDATE_SCREEN)) {
            mHandler.removeMessages(UPDATE_SCREEN);
        }

        if (mHandler.hasMessages(UPDATE_TICK)) {
            mHandler.removeMessages(UPDATE_TICK);
        }
    }

    @Override
    public void updatePoint(int pointCount, List<CPoint> pointList) {
        if (mHandler == null) {
            return;
        }
        Message msg = new Message();
        msg.what = PRESS_KEY;
        msg.arg1 = pointCount;
        msg.obj = pointList;
        mHandler.sendMessage(msg);
    }

    private void increaseTick() {
        Log.d(LOG_TAG, "increaseTick()");
        Message message= new Message();
        message.what = UPDATE_TICK;
        mHandler.sendMessageDelayed(message, TICK_TIMER);
    }

    private void update() {
        if (listener == null) {
            return;
        }
        listener.update();

        if (mHandler == null) {
            return;
        }

        Message message= new Message();
        message.what = UPDATE_SCREEN;
        mHandler.sendMessageDelayed(message, REFRESH_TIMER);
    }

    private void finishApp() {
        Toast.makeText(mContext, "There is no touch for 60 seconds. Finish this app!", Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, "There is no touch for 60 seconds. Finish this app!");
        stopEngnine();
        mHandler = null;
        this.listener = null;
        activity.finish();
    }

    @Override
    public void destroy() {
        Toast.makeText(mContext, "Finish this app!", Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, "Finish this app!");
        stopEngnine();
        mHandler = null;
        this.listener = null;
    }
}
