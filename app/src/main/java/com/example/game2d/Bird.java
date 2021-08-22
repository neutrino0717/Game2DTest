package com.example.game2d;

import static com.example.game2d.GameView.screenRatioX;
import static com.example.game2d.GameView.screenRatioY;
import static com.example.game2d.GameView.screenX;
import static com.example.game2d.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Bird {
    public int speed;
    public boolean wasShot = true;
    int x = 2000, y, width, height;
    Bitmap bird1, bird2, bird3, bird4;
    private int birdCounter;

    @Override
    public String toString() {
        return "x:" + x + ", y:" + y + ", width:" + width + ", height:" + height;
    }

    public void reset(){
        x = screenX;
        y = new Random().nextInt(screenY - height );
        speed = 5 + new Random().nextInt(10);
    }

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
        reset();
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
