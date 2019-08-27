package com.chobocho.chooseone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chobocho.chooseone.manager.ChooseManager;
import com.chobocho.chooseone.manager.ChooseManagerImpl;

public class MainActivity extends AppCompatActivity {
    ChooseManager chooseManager;
    ChooseView chooseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseManager = new ChooseManagerImpl();

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        chooseView = new ChooseView(this);
        chooseView.setScreenSize(screenWidth,screenHeight);
        chooseView.setManager(chooseManager);
        setContentView(chooseView);
    }
}
