package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class MainMenuScene implements Scene {

    Point touchLocation = new Point();
    boolean press = false;
    boolean pressDelay = false;

    public MainMenuScene(){

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
