package com.crouchingrobot.setsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private BoardBackend boardBack;
    private Board boardFront;
    private final int boardHight = 11;
    private final int boardWidth = 5;
    private Context context;
    private SetTracker setTrack = null;
    private boolean press = false;
    private Point touchLocation = new Point(0,0);
    private Point setBottomLeft = new Point(30,350);
    private Point setTopRight = new Point(1400,300);
    private Point timerCenter = null;
    private Point scoreCenter = null;
    private Point streakCenter = null;
    private int screenWidth = 0;
    private int screenHight = 0;
    private GameTimer gTime = null;
    private ScoreCounter gScore = null;
    private boolean gameOver = false;

    private final int startTimeAmount = 45; //number of seconds to start the timer with
    private final int boardClearAditionalTime = 15; //amount of time to add after a board clear

    public GamePanel(Context context){
        super(context);
        this.context = context;

        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHight = context.getResources().getDisplayMetrics().heightPixels;

        timerCenter = new Point(screenWidth/2, screenHight/19);
        scoreCenter = new Point(screenWidth/2,(screenHight/19) * 2);
        streakCenter = new Point(screenWidth/100 * 85, (screenHight/19) * 2);

        gTime = new GameTimer(startTimeAmount, timerCenter, screenHight/17);
        gScore = new ScoreCounter(scoreCenter,streakCenter, screenHight/19,screenHight/19);

        boardBack = new BoardBackend(boardHight,boardWidth, gScore);
        boardFront = new Board( boardBack,screenWidth,screenHight);
        System.out.println(context.getResources().getDisplayMetrics().widthPixels + "  " + context.getResources().getDisplayMetrics().heightPixels);

        setBottomLeft.set((screenWidth/200)*7,(screenHight/100)*15);
        setTopRight.set((screenWidth/200)*196,(screenHight/100)*13);
        setTrack = new SetTracker(boardBack,setBottomLeft,setTopRight);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
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
        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){

        boardFront.update(touchLocation,press);
        if(boardBack.isClear()){
            boardBack = new BoardBackend(boardHight,boardWidth, gScore);
            boardFront = new Board(boardBack,context.getResources().getDisplayMetrics().widthPixels,
                    context.getResources().getDisplayMetrics().heightPixels);
            setTrack = new SetTracker(boardBack,setBottomLeft,setTopRight);
            gTime.addTime(boardClearAditionalTime);
            gScore.streakInc();
        }
        gTime.update();
        if(gTime.up()){
            gameOver = true;
        }
    }

    @Override public void draw(Canvas canvas){

        super.draw(canvas);
        boardFront.draw(canvas);
        setTrack.draw(canvas);
        gTime.draw(canvas);
        gScore.draw(canvas);
    }

}
