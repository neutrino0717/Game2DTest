package com.example.game2d;

import static com.example.game2d.GameView.screenRatioX;
import static com.example.game2d.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flight {
    public boolean isGoingUp = false;
    int toShoot = 0;
    int x, y, width, height, wingCount = 0, shootCount = 1;
    Bitmap flight1, flight2, shoot1, shoot2, shoot3, shoot4, shoot5;
    private GameView gameView;

    public Flight(GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;
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
        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);
        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);
    }

    Bitmap getFight(){
        if(toShoot != 0){
            if(shootCount == 1 ){
                shootCount++;
                return shoot1;
            }
            if(shootCount == 2 ){
                shootCount++;
                return shoot3;
            }
            if(shootCount == 3 ){
                shootCount++;
                return shoot3;
            }
            if(shootCount == 4 ){
                shootCount++;
                return shoot4;
            }
            shootCount = 1;
            toShoot--;
            gameView.newBullet();

            return shoot5;
        }

        if (wingCount == 0){
            wingCount++;
            return flight1;
        }
        wingCount--;
        return flight2;

    }
}
