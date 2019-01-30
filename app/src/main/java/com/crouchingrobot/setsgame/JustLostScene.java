package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.google.android.gms.ads.AdRequest;

public class JustLostScene implements Scene {

    Point touchLocation = new Point();
    boolean press = false;
    boolean pressDelay = false;
    private Point hsCenter = null;
    public HighScore gHscore = null;


    private int screenWidth = 0;
    private int screenHight = 0;

    public JustLostScene(){
        this.screenWidth = GamePanel.SCREEN_WIDTH;
        this.screenHight = GamePanel.SCREEN_HIGHT;

        hsCenter = new Point(screenWidth/2, (screenHight/19) * 13);
        gHscore = new HighScore(hsCenter,screenHight/23);
    }

    @Override
    public void update() {
        if(pressDelay == true && press == false){
            SceneManager.ACTIVE_SCENE = 1;
            SceneManager.newGame();
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
        //canvas.drawRect(fullScreenRec,paintBack);

        gHscore.draw(canvas);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(200);
        MyDraw.drawCenter(canvas,textPaint,"MAKE SETS", new Point(GamePanel.SCREEN_WIDTH/2,GamePanel.SCREEN_HIGHT/6));

        textPaint.setTextSize(150);
        MyDraw.drawCenter(canvas,textPaint,"GAME OVER", new Point(GamePanel.SCREEN_WIDTH/2,GamePanel.SCREEN_HIGHT/4));

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
