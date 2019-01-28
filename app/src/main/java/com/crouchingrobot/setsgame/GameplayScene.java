package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

public class GameplayScene implements Scene {
    private BoardBackend boardBack;
    private Board boardFront;
    private final int boardHight = 11;
    private final int boardWidth = 5;
    private SetTracker setTrack = null;
    private boolean press = false;
    private Point touchLocation = new Point(0,0);
    private Point setBottomLeft = new Point(30,350);
    private Point setTopRight = new Point(1400,300);
    private Point timerCenter = null;
    private Point scoreCenter = null;
    private Point streakCenter = null;
    private GameTimer gTime = null;
    public ScoreCounter gScore = null;
    private boolean gameOver = false;
    private final int startTimeAmount = 5; //number of seconds to start the timer with  //was using 35
    private final int boardClearAditionalTime = 12; //amount of time to add after a board clear

    private int screenWidth = 0;
    private int screenHight = 0;


    public GameplayScene(){
        this.screenWidth = GamePanel.SCREEN_WIDTH;
        this.screenHight = GamePanel.SCREEN_HIGHT;

        timerCenter = new Point(screenWidth/2, screenHight/19);
        scoreCenter = new Point(screenWidth/2,(screenHight/19) * 2);
        streakCenter = new Point(screenWidth/100 * 85, (screenHight/80) * 4);

        gTime = new GameTimer(startTimeAmount, timerCenter, screenHight/17);
        gScore = new ScoreCounter(scoreCenter,streakCenter, screenHight/23,screenHight/23);


        boardBack = new BoardBackend(boardHight,boardWidth, gScore);
        boardFront = new Board( boardBack,screenWidth,screenHight);

        setBottomLeft.set((screenWidth/200)*7,(screenHight/100)*15);
        setTopRight.set((screenWidth/200)*196,(screenHight/100)*13);
        setTrack = new SetTracker(boardBack,setBottomLeft,setTopRight);

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

    @Override
    public void terminate() {
        //set active screen to loss screen
        SceneManager.ACTIVE_SCENE = 2;
    }

    @Override
    public void draw(Canvas canvas) {
        boardFront.draw(canvas);
        setTrack.draw(canvas);
        gTime.draw(canvas);
        gScore.draw(canvas);
    }

    @Override
    public void update() {
        boardFront.update(touchLocation,press);
        if(boardBack.isClear()){
            boardBack = new BoardBackend(boardHight,boardWidth, gScore);
            boardFront = new Board(boardBack,screenWidth,screenHight);
            setTrack = new SetTracker(boardBack,setBottomLeft,setTopRight);
            gTime.addTime(boardClearAditionalTime);
            gScore.streakInc();
        }
        gTime.update();
        if(gTime.up()){
            terminate();
        }
    }
}
