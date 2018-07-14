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
    private final int boardHight = 12;
    private final int boardWidth = 5;
    private Context context;
    private int scoreCount = 0;
    private int levelCount = 0;

    private boolean press = false;
    private Point touchLocation = new Point(0,0);


    public GamePanel(Context context){
         super(context);
        this.context = context;

        boardBack = new BoardBackend(boardHight,boardWidth);
        boardFront = new Board(boardBack,context.getResources().getDisplayMetrics().widthPixels,
                context.getResources().getDisplayMetrics().heightPixels);
        System.out.println(context.getResources().getDisplayMetrics().widthPixels + "  " + context.getResources().getDisplayMetrics().heightPixels);

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
            boardBack = new BoardBackend(boardHight,boardWidth);
            boardFront = new Board(boardBack,context.getResources().getDisplayMetrics().widthPixels,
                    context.getResources().getDisplayMetrics().heightPixels);
        }
    }

    @Override public void draw(Canvas canvas){

        super.draw(canvas);
        boardFront.draw(canvas);
    }

}
