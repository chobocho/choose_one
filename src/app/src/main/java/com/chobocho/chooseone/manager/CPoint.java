package com.chobocho.chooseone.manager;

import androidx.annotation.NonNull;

public class CPoint {
    public int id;
    public int x;
    public int y;
    public int color;

    public CPoint() {

    }

    public CPoint(CPoint cPoint) {
        this.id = cPoint.id;
        this.x = cPoint.x;
        this.y = cPoint.y;
        this.color = cPoint.color;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id + " x: " + x + " y: " + y;
    }
}
