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
    private Rect r = new Rect();

    //Pass in timer length in seconds
    public GameTimer(int startTimerLength, Point center){
        this.center = center;
        timerLength = startTimerLength * 1000; //convert seconds to miliseconds
        startTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint timerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timerPaint.setColor(Color.WHITE);
        timerPaint.setTextSize(200);
        String timeOut = "" + timeLeft/1000 + "";
        //canvas.drawText(timeOut,(float)center.x,
        //        (float)center.y- ((timerPaint.descent() + timerPaint.ascent()) / 2),
        //        timerPaint);
        drawCenter(canvas, timerPaint,timeOut);
    }

    private void drawCenter(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = center.y + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
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
