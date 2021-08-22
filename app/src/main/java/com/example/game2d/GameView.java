package com.example.game2d;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private final int sound;
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    public static int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Bird[] birds;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private Flight flight;
    private GameActivity activity;
    private Background background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = soundPool.load(activity, R.raw.shoot, 1);


        this.screenX = screenX;
        this.screenY = screenY;
        Log.d("screen size", String.valueOf(this.screenX) + " " + String.valueOf(this.screenY));
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        Log.d("screen ratio", String.valueOf(screenRatioX) + " " + String.valueOf(screenRatioY));
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        flight = new Flight(this, screenY, getResources());
        birds = new Bird[4];
        for (int i = 0; i < 4; i++){
            Bird bird = new Bird(getResources());
            birds[i] = bird;
        }
        bullets = new ArrayList<>();

        background2.x = screenX;
        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (flight.isGoingUp)
            flight.y -= 30 * screenRatioY;
        else
            flight.y += 30 * screenRatioY;

        if (flight.y < 0)
            flight.y = 0;

        if (flight.y > screenY - flight.height)
            flight.y = screenY - flight.height;

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += 50 * screenRatioX;
            for (Bird bird : birds) {
                if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())) {
                    score++;
                    bird.reset();
                    bullet.x = screenX + 5000;
                    bird.wasShot = true;
                }
            }
        }

        for (Bullet bullet : trash)
            bullets.remove(bullet);

        for (Bird bird : birds){
            bird.x -= bird.speed;
            if (bird.x + bird.width < 0){

                //if (!bird.wasShot) {
                //    isGameOver = true;
                //    return;
                //}

                bird.reset();

            }
            if (Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {
                isGameOver = true;
                return;
            }
            bird.wasShot = false;
        }


    }

    private void draw() {
        Log.d("isGameOver", String.valueOf(isGameOver));
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Bird bird : birds) {
                Log.d("bird", bird.toString() );
                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
            }
            canvas.drawText(String.valueOf(score), screenX/2, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(flight.getFight(), flight.x, flight.y, paint);

            for(Bullet bullet: bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if (event.getX() > screenX / 2) {
                    flight.toShoot++;
                }
                break;
        }
        //return super.onTouchEvent(event);
        return true;
    }

    public void newBullet() {
        if (!prefs.getBoolean("isMute", false)) {
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);
    }
}
