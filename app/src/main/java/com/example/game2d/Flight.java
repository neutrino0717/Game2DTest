package com.example.game2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flight {
    int x, y, width, height;
    Bitmap flight1, flight2;

    public Flight(int screenY, Resources res) {
        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        width = flight1.getWidth();
        height = flight1.getHeight();
        width /= 4;
        height /=4;

    }
}
