package com.chobocho.chooseone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chobocho.chooseone.manager.CPoint;
import com.chobocho.chooseone.manager.ChooseManagerObserver;
import com.chobocho.chooseone.viewmodel.ViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseView extends View  {
	final  String LOG_TAG = this.getClass().getSimpleName();
	Context mContext;
	ChooseManagerObserver manager;
	ViewManager viewManager;
	AppCompatActivity activity;


	int [] colorTable = new int[20];

	final int REFRESH_TIMER = 30;
	final int TICK_TIMER = 800;
	final int NO_TOUCH_TIMER = 60000;

	final int UPDATE_SCREEN = 1001;
	final int PRESS_KEY = 1002;
	final int UPDATE_TICK = 1003;
    final int NO_TOUCH_TIMER_EXPIRED = 1004;

	private int screenWidth;
	private int screenHeight;

	public ChooseView(Context context) {
		super(context);
		this.mContext = context;

		for (int i = 0; i < 20; i++) {
			colorTable[i] = getRandomColor();
		}
	}

	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);

		if (manager == null) {
			return;
		}
		viewManager.OnDraw(canvas, colorTable);
	}

	public void setScreenSize(int w, int h) {
		this.screenWidth = w;
		this.screenHeight = h;
	}

	public void setActivity(AppCompatActivity ac) {
	    this.activity = ac;
	}

	public void setManager(ChooseManagerObserver manager) {
		this.manager = manager;
	}

	public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
	}

	public void increaseTick() {
		Log.d(LOG_TAG, "increaseTick()");
		Message message= new Message();
		message.what = UPDATE_TICK;
		mHandler.sendMessageDelayed(message, TICK_TIMER);
	}

	public void update() {
		Log.d(LOG_TAG, "View.update()");
		Message message= new Message();
		message.what = UPDATE_SCREEN;
        mHandler.sendMessageDelayed(message, REFRESH_TIMER);
	}

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
					invalidate();
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

	public void finishApp() {
		Toast.makeText(mContext, "There is no touch for 60 seconds. Finish this app!", Toast.LENGTH_LONG);
		activity.finish();
	}

	public boolean onTouchEvent(MotionEvent event) {
		    //Log.d(LOG_TAG, ">> X: " + event.getX() + " Y: " + event.getY());

			if (manager == null) {
				return true;
			}

			List<CPoint> pointList = new ArrayList<>();
			int pointerCount = event.getPointerCount();

			Log.d(LOG_TAG, "Point Count: " + pointerCount);

			final int action = event.getAction();

			if (MotionEvent.ACTION_MOVE == (action & MotionEvent.ACTION_MASK)) {
				for (int i = 0; i < pointerCount; i++) {
					CPoint point = new CPoint();
					point.id = event.getPointerId(i);
					point.x = (int) (event.getX(i));
					point.y = (int) (event.getY(i));
					pointList.add(point);
					// Log.d(LOG_TAG, point.toString());
				}
			}

			Message msg = new Message();
			msg.what = PRESS_KEY;
			msg.arg1 = pointerCount;
			msg.obj = pointList;
			mHandler.sendMessage(msg);

			return true;
	}

	public int getRandomColor(){
		Random rnd = new Random();
		return Color.argb(255, 56 + rnd.nextInt(200), 56 + rnd.nextInt(200), 56 + rnd.nextInt(200));
	}

}
