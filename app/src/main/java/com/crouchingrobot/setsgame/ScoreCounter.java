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
    public ScoreCounter(Point scoreCenter, Point streakCenter, int scoreTextSize, int streakTextSize) {
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
        MyDraw.drawCenter(canvas, timerPaint, scoreOut, scoreCenter);

        timerPaint.setTextSize(streakTextSize);
        String streakOut = "x" + currentStreak;
        streakCenter.set(streakCenter.x, streakCenter.y + (streakTextSize / 2) + (streakTextSize / 2));
        MyDraw.drawCenter(canvas, timerPaint, streakOut, streakCenter);
        streakCenter.set(streakCenter.x, streakCenter.y - (streakTextSize / 2) - (streakTextSize / 2));
        MyDraw.drawCenter(canvas, timerPaint, "Streak", streakCenter);
    }

    @Override
    public void update() {

    }

    public void addPoints(int scoreToAdd) {
        currentScore += (scoreToAdd * currentStreak);
    }

    public int getScore() {
        return currentScore;
    }

    public void streakInc() {
        currentStreak++;
    }

    public void streakEnd() {
        if (maxStreak < currentStreak) {
            maxStreak = currentStreak;
        }
        lastStreak = currentStreak;
        currentStreak = 1;
    }

    public void setToMax() {
        currentStreak = maxStreak;
    }

    public void setToLast() {
        currentStreak = lastStreak;
    }

}
