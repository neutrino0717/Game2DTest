package com.example.game2d;

import static com.example.game2d.GameView.screenRatioX;
import static com.example.game2d.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {
    int x, y, width, height;
    Bitmap bullet;

    public Bullet(Resources res) {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        width = bullet.getWidth()/4;
        height = bullet.getHeight()/4;
        width *= screenRatioX;
        height *= screenRatioY;

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }

    Rect getCollisionShape(){
        return new Rect(x, y, x + width, y + height);
    }

}
