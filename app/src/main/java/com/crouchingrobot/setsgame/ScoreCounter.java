package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ScoreCounter implements GameObject {
    private Point scoreCenter = null;
    private Point streakCenter = null;
    private int currentScore = 0;
    private Rect r = new Rect();
    private int currentStreak = 1;
    private int maxStreak = 0;
    private int lastStreak = 0;
    private int scoreTextSize = 0;
    private int streakTextSize = 0;

    //Pass in timer length in seconds
    public ScoreCounter(Point scoreCenter, Point streakCenter, int scoreTextSize, int streakTextSize){
        this.scoreCenter = scoreCenter;
        this.scoreTextSize = scoreTextSize;
        this.streakCenter = streakCenter;
        this.streakTextSize = streakTextSize;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint timerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timerPaint.setColor(Color.WHITE);
        timerPaint.setTextSize(scoreTextSize);
        String scoreOut = "Score: " + currentScore + "";
        drawCenter(canvas, timerPaint,scoreOut, scoreCenter);

        timerPaint.setTextSize(streakTextSize);
        String streakOut = "x" + currentStreak;
        drawCenter(canvas,timerPaint,streakOut,streakCenter);

    }

    private void drawCenter(Canvas canvas, Paint paint, String text, Point p) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = p.x - r.width() / 2f - r.left;
        float y = p.y + r.height() / 2f - r.bottom;
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
        currentStreak = 1;
    }

    public void setToMax(){
        currentStreak = maxStreak;
    }

    public void setToLast(){
        currentStreak = lastStreak;
    }
}
