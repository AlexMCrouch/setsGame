package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainMenuScene implements Scene {

    Point touchLocation = new Point();
    boolean press = false;
    boolean pressDelay = false;
    View thisView;
    public MainMenuScene(Context context, View inView){
        thisView = inView;


//        // Load the ImageView that will host the animation and
//        // set its background to our AnimationDrawable XML resource.
//        ImageView img = (ImageView)thisView;
//        img.setBackgroundResource(R.drawable.test_animation);
//
//        // Get the background, which has been compiled to an AnimationDrawable object.
//        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
//
//        // Start the animation (looped playback by default).
//        frameAnimation.start();
    }

    @Override
    public void update() {
        if(pressDelay == true && press == false){
            SceneManager.newGame();
            SceneManager.ACTIVE_SCENE = 1;

        }
        pressDelay = press;
    }

    @Override
    public void terminate() {

    }
    @Override
    public void draw(Canvas canvas) {
        Rect fullScreenRec = new Rect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HIGHT);
        Paint paintBack = new Paint();
        paintBack.setColor(Color.BLACK);
        canvas.drawRect(fullScreenRec,paintBack);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);

        textPaint.setTextSize(200);
        MyDraw.drawCenter(canvas,textPaint,"MAKE SETS", new Point(GamePanel.SCREEN_WIDTH/2,GamePanel.SCREEN_HIGHT/2));


    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                touchLocation.set((int)event.getX(),(int)event.getY());
                press = false;
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                touchLocation.set((int)event.getX(),(int)event.getY());
                press = true;
                break;
        }
    }
}
