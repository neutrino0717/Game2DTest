package com.example.game2d;

import static com.example.game2d.GameView.screenRatioX;
import static com.example.game2d.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {
    int x, y;
    Bitmap bullet;

    public Bullet(Resources res) {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        int width = bullet.getWidth()/4;
        int height = bullet.getHeight()/4;
        width *= screenRatioX;
        height *= screenRatioY;

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);

    }
}
