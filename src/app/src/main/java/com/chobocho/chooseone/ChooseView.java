package com.chobocho.chooseone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.nfc.tech.NfcB;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chobocho.chooseone.manager.CPoint;
import com.chobocho.chooseone.manager.ChooseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseView extends View  {
	final  String LOG_TAG = this.getClass().getSimpleName();
	Context mContext;
	ChooseManager manager;
	int colorTable[] = new int[20];

	final int UPDATE_SCREEN = 1001;
	final int PRESS_KEY = 1002;

	private int screenWidth;
	private int screenHeigth;
	int gameSpeed;

	public ChooseView(Context context) {
		super(context);
		this.mContext = context;

		gameSpeed = 800;

		for (int i = 0; i < 20; i++) {
			colorTable[i] = getRandomColor();
		}
	}

	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, screenWidth, screenHeigth, paint);

		if (manager == null) {
			return;
		}

		if (manager.hasChoosen()) {
			CPoint point = manager.getChosenPoint();
			Log.d(LOG_TAG,"Has chosen!" + point.toString());
			paint.setColor(colorTable[point.color]);
			canvas.drawCircle(point.x, point.y, 250, paint);
			return;
		}

		List<CPoint> list = manager.getPointList();

		if (list == null) {
			return;
		}

		for (CPoint point : list) {
			Log.d(LOG_TAG, point.toString());
			paint.setColor(colorTable[point.id]);
		    canvas.drawCircle(point.x, point.y, 150, paint);
	    }

	}

	public void setScreenSize(int w, int h) {
		this.screenWidth = w;
		this.screenHeigth = h;
	}

	public void setManager(ChooseManager manager) {
		this.manager = manager;
	}

	public void update() {
		Log.d(LOG_TAG, "View.update()");
		Message message= new Message();
		message.what = UPDATE_SCREEN;
        mHandler.sendMessageDelayed(message, gameSpeed);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d(LOG_TAG, "There is event : " + msg.what);
			if (msg.what == PRESS_KEY) {
				int pointCount = msg.arg1;
				List<CPoint> list = (ArrayList<CPoint>) msg.obj;
				//for (CPoint point : list) {
				//	Log.d(LOG_TAG, point.toString());
				//}
                manager.updatePoint(pointCount, list);
                if (mHandler.hasMessages(UPDATE_SCREEN) == false) {
					update();
				}
				invalidate();
			} else if (msg.what == UPDATE_SCREEN) {
				if (manager == null) {
					return;
				}
				manager.tick();
				invalidate();
				update();
			}
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		    //Log.d(LOG_TAG, ">> X: " + event.getX() + " Y: " + event.getY());

			if (manager == null) {
				return true;
			}

			List<CPoint> pointList = new ArrayList<CPoint>();
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
		return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
	}

}
