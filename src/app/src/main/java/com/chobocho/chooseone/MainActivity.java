package com.chobocho.chooseone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chobocho.chooseone.manager.ChooseManagerImpl;
import com.chobocho.chooseone.manager.ChooseManagerObserver;
import com.chobocho.chooseone.viewmodel.ViewManagerImpl;

public class MainActivity extends AppCompatActivity {
    ChooseManagerObserver chooseManager;
    ChooseView chooseView;
    ViewManagerImpl viewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewManager = new ViewManagerImpl();
        chooseManager = new ChooseManagerImpl();
        chooseManager.registerObserver(viewManager);

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        chooseView = new ChooseView(this);
        chooseView.setActivity(this);
        chooseView.setScreenSize(screenWidth,screenHeight);
        chooseView.setManager(chooseManager);
        chooseView.setViewManager(viewManager);
        setContentView(chooseView);
    }
}
