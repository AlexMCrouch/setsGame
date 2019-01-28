package com.crouchingrobot.setsgame;

import android.content.Context;
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
    private int highscore = 0;

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
//        timerPaint.setTextSize(200);
        String scoreOut = "High Score: " + highscore + "";
        //MyDraw.drawCenter(canvas, timerPaint, scoreOut, scoreCenter);
        Point tempPoint = new Point(700,500);
        MyDraw.drawCenter(canvas, timerPaint, scoreOut, tempPoint);
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
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("score.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(highscore);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void readHighScore(Context context){
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("score.txt");
            System.out.println(is.read());
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
