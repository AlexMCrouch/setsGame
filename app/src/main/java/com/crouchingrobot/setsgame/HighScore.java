package com.crouchingrobot.setsgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class HighScore implements GameObject {
    private Point scoreCenter = null;
    private Rect r = new Rect();
    private int scoreTextSize = 200;
    public int highscore = 0;

    //Pass in timer length in seconds
    public HighScore(Point scoreCenter, int scoreTextSize) {
        this.scoreCenter = scoreCenter;
        this.scoreTextSize = scoreTextSize;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint timerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timerPaint.setColor(Color.WHITE);
        timerPaint.setTextSize(scoreTextSize);
        String scoreOut = "High Score: " + highscore + "";
        MyDraw.drawCenter(canvas, timerPaint, scoreOut, scoreCenter);
    }

    @Override
    public void update(){

    }
    public void updateHS(int score) {
        if(score>highscore){
            highscore = score;
        }
    }

    public void writeHighScore(Context context){
        SharedPreferences prefs = context.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highscore", highscore);
        editor.commit();
    }

    public void readHighScore(Context context){

        //getting preferences
        SharedPreferences prefs = context.getSharedPreferences("scores", Context.MODE_PRIVATE);
        highscore = prefs.getInt("highscore", 0); //0 is the default value
    }

}
