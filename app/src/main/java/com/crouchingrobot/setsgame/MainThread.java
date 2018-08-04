package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public Canvas canvas;
   // private BoardBackend boardBack;
    //private Board boardFront;
    //private String mode = null;
    //private final int boardHight = 12;
    //private final int boardWidth = 5;

    public void setRunning(boolean running){
        this.running = running;
    }

    private static Object mPauseLock;
    private static boolean mPaused;
    private static boolean mFinished;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        //this.mode = mode;

        mPauseLock = new Object();
        mPaused = false;
        mFinished = false;
    }

    @Override
    public void run(){
        long startTime;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;


        while (running) {

            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }

            if(SceneManager.ACTIVE_SCENE == 1){
                MAX_FPS = 30;
            }
            else{
                MAX_FPS = 10;
            }
            long targetTime = 1000/MAX_FPS;
            long timeMillis = 1000/MAX_FPS;

            startTime = System.nanoTime();
             canvas = null;

             try {
                 canvas = this.surfaceHolder.lockCanvas();
                 synchronized (surfaceHolder) {
                     this.gamePanel.update();
                     this.gamePanel.draw(canvas);
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             } finally {
                 if (canvas != null) {
                     try {
                         surfaceHolder.unlockCanvasAndPost(canvas);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             }

             timeMillis = (System.nanoTime() - startTime) / 1000000;
             waitTime = targetTime - timeMillis;
             try {
                 if (waitTime > 0) {
                     this.sleep(waitTime);
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }
             totalTime += System.nanoTime() - startTime;
             frameCount++;
             if (frameCount == MAX_FPS) {
                 averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                 frameCount = 0;
                 totalTime = 0;
                 //System.out.println(averageFPS);
             }
         }
     }


    /**
     * Call this on pause.
     */
    public static void onPause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    /**
     * Call this on resume.
     */
    public static void onResume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }
}
