package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ScoreCounter implements GameObject {
    private Point center = null;
    private int currentScore = 0;
    private Rect r = new Rect();
    private int currentStreak = 0;
    private int maxStreak = 0;
    private int lastStreak = 0;
    private int textSize = 0;

    //Pass in timer length in seconds
    public ScoreCounter(Point center, int textSize){
        this.center = center;
        this.textSize = textSize;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint timerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timerPaint.setColor(Color.WHITE);
        timerPaint.setTextSize(textSize);
        String scoreOut = "" + currentScore + "";
        drawCenter(canvas, timerPaint,scoreOut);
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

    }

    public void addPoints(int scoreToAdd){
        currentScore += (scoreToAdd * currentStreak);
    }

    public int getScore(){
        return currentScore;
    }

    public void streakInc(){
        currentStreak++;
    }

    public void streakEnd(){
        if(maxStreak<currentStreak){
            maxStreak = currentStreak;
        }
        lastStreak = currentStreak;
        currentStreak = 0;
    }

    public void setToMax(){
        currentStreak = maxStreak;
    }

    public void setToLast(){
        currentStreak = lastStreak;
    }
}
