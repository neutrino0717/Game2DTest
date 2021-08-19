package com.example.game2d;

import static com.example.game2d.GameView.screenRatioX;
import static com.example.game2d.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bird {
    public int speed = 20;
    int x = 0, y, width, height;
    Bitmap bird1, bird2, bird3, bird4;
    private int birdCounter;

    Bird ( Resources res ){
        bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
        bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3);
        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);

        width = bird1.getWidth()/6;
        height = bird1.getHeight()/6;
        width *= screenRatioX;
        height *= screenRatioY;

        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);
        bird3 = Bitmap.createScaledBitmap(bird3, width, height, false);
        bird4 = Bitmap.createScaledBitmap(bird4, width, height, false);

        //off the screen at the start
        y = -height;
    }

    Bitmap getBird (){
        if (birdCounter == 1){
            birdCounter++;
            return bird1;
        }
        if (birdCounter == 2){
            birdCounter++;
            return bird2;
        }
        if (birdCounter == 3){
            birdCounter++;
            return bird3;
        }
        birdCounter = 1;
        return bird4;
    }

    Rect getCollisionShape(){
        return new Rect(x, y, x + width, y + height);
    }
}
