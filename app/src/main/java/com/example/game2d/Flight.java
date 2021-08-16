package com.example.game2d;

import static com.example.game2d.GameView.screenRatioX;
import static com.example.game2d.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flight {
    public boolean isGoingUp = false;
    int x, y, width, height, wingCount = 0;
    Bitmap flight1, flight2;

    public Flight(int screenY, Resources res) {
        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        width = flight1.getWidth();
        height = flight1.getHeight();
        width /= 4;
        height /=4;
        width *= screenRatioX;
        height *= screenRatioY;
        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        y = screenY/2;
        x = (int)(64*screenRatioX);
    }

    Bitmap getFight(){
        if (wingCount == 0){
            wingCount++;
            return flight1;
        }
        wingCount--;
        return flight2;

    }
}
