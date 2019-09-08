package com.chobocho.chooseone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chobocho.chooseone.manager.ChooseManagerImpl;
import com.chobocho.chooseone.manager.ChooseManagerObserver;
import com.chobocho.chooseone.viewmodel.DrawEngine;
import com.chobocho.chooseone.viewmodel.DrawEngineImpl;
import com.chobocho.chooseone.viewmodel.ViewManagerImpl;

public class MainActivity extends AppCompatActivity {
    private ChooseManagerObserver chooseManager;
    private ChooseView chooseView;
    private DrawEngine drawEngine;
    private ViewManagerImpl viewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        viewManager = new ViewManagerImpl(screenWidth);
        chooseManager = new ChooseManagerImpl();
        chooseManager.registerObserver(viewManager);

        drawEngine = new DrawEngineImpl(this, chooseManager);
        drawEngine.setActivity(this);

        chooseView = new ChooseView(this);

        chooseView.setScreenSize(screenWidth,screenHeight);
        chooseView.setViewManager(viewManager);
        chooseView.setDrawEngine(drawEngine);
        setContentView(chooseView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (drawEngine != null) {
            drawEngine.stopEngnine();
        }
    }

    @Override
    protected  void onResume() {
        super.onResume();
        if (drawEngine != null) {
            drawEngine.resumeEngnine();
        }
    }
}
