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

import com.chobocho.chooseone.manager.CPoint;
import com.chobocho.chooseone.viewmodel.DrawEngine;
import com.chobocho.chooseone.viewmodel.ViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseView extends View  {
	final  String LOG_TAG = this.getClass().getSimpleName();
	final static int UPDATE_SCREEN = 1001;
	final static int PRESS_KEY = 1002;

	DrawEngine drawEngine;
	ViewManager viewManager;
	ViewListener listener;

	int [] colorTable = new int[20];

	private int screenWidth;
	private int screenHeight;

	public ChooseView(Context context) {
		super(context);

		listener = new ViewListener();

		for (int i = 0; i < 20; i++) {
			colorTable[i] = getRandomColor();
		}
	}

	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);

		if (viewManager == null) {
			return;
		}
		viewManager.OnDraw(canvas, colorTable);
	}

	public void setScreenSize(int w, int h) {
		this.screenWidth = w;
		this.screenHeight = h;
	}

	public void setDrawEngine(DrawEngine drawEngine) {
		this.drawEngine = drawEngine;

		drawEngine.setListener(this.listener);
		drawEngine.init();
		drawEngine.start();
	}

	public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
	}

	public void updateScreen() {
		Log.d(LOG_TAG, "View.updateScreen()");
		Message message= new Message();
		message.what = UPDATE_SCREEN;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d(LOG_TAG, "There is event : " + msg.what);
			switch(msg.what) {
				case PRESS_KEY:
					int pointCount = msg.arg1;
					List<CPoint> list = (ArrayList<CPoint>) msg.obj;
					drawEngine.updatePoint(pointCount, list);
					break;
				case UPDATE_SCREEN:
					invalidate();
					break;
			}
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		    //Log.d(LOG_TAG, ">> X: " + event.getX() + " Y: " + event.getY());

			if (drawEngine == null) {
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

	public class ViewListener {
		public  ViewListener() {

		}
		public void update() {
			Message message= new Message();
			message.what = ChooseView.UPDATE_SCREEN;
			mHandler.sendMessage(message);
		}
	}
}
