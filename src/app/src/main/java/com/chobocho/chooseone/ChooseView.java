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


public class ChooseView extends View  {
	private final String LOG_TAG = this.getClass().getSimpleName();
	private final static int UPDATE_SCREEN = 1001;
	private final static int PRESS_KEY = 1002;

	private DrawEngine drawEngine;
	private ViewManager viewManager;
	private final ViewListener listener;
    private Paint paint;

	private int screenWidth;
	private int screenHeight;

	public ChooseView(Context context) {
		super(context);

		listener = new ViewListener();
		paint = new Paint();
	}

	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);

		if (viewManager == null) {
			return;
		}
		viewManager.OnDraw(canvas);
	}

	public void setScreenSize(int w, int h) {
		this.screenWidth = w;
		this.screenHeight = h;
	}

	public void setDrawEngine(DrawEngine drawEngine) {
		this.drawEngine = drawEngine;
		if (drawEngine == null) {
			Log.e(LOG_TAG, "Fatal: drawEngine is null");
		}
		drawEngine.setListener(this.listener);
	}

	public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
	}


	final Handler mHandler = new Handler() {
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

	public class ViewListener {
		ViewListener() {

		}
		public void update() {
			Message message= new Message();
			message.what = ChooseView.UPDATE_SCREEN;
			mHandler.sendMessage(message);
		}
	}
}
