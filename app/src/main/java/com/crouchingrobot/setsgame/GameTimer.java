package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GameTimer implements GameObject {
    private Point center = null;
    private long timeLeft;
    private long startTime;
    private long timeElapsed;
    private long timerLength;
    private int textSize = 0;

    //Pass in timer length in seconds
    public GameTimer(int startTimerLength, Point center, int textSize){
        this.center = center;
        timerLength = startTimerLength * 1000; //convert seconds to miliseconds
        startTime = System.currentTimeMillis();
        this.textSize = textSize;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint timerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timerPaint.setColor(Color.WHITE);
        timerPaint.setTextSize(textSize);
        String timeOut = "" + timeLeft/1000 + "";
        //canvas.drawText(timeOut,(float)center.x,
        //        (float)center.y- ((timerPaint.descent() + timerPaint.ascent()) / 2),
        //        timerPaint);
        MyDraw.drawCenter(canvas, timerPaint,timeOut,center);
    }

    @Override
    public void update() {
        timeElapsed = System.currentTimeMillis() - startTime;
        timeLeft = timerLength - timeElapsed;
    }

    public void addTime(int timeToExtend){
        timerLength += (timeToExtend * 1000);   //convert seconds to miliseconds
    }

    public boolean up (){
        return (timeLeft <= 0);
    }
}
