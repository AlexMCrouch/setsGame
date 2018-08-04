package com.crouchingrobot.setsgame;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MyRunnable implements Runnable {
        private int lastScene = 0;
    private AdRequest adRequest;
    private RelativeLayout layout;
    private AdView adView;
    private RelativeLayout.LayoutParams adParams;
    private Handler handler;


    private static Object mPauseLock;
    private static boolean mPaused;
    private static boolean mFinished;

    public MyRunnable(AdRequest adRequest, RelativeLayout layout, AdView adView,RelativeLayout.LayoutParams adParams, Handler handler) {
        this.adRequest = adRequest;
        this.layout = layout;
        this.adView = adView;
        this.adParams = adParams;
        this.handler = handler;


        mPauseLock = new Object();
        mPaused = false;
        mFinished = false;
    }
        @Override
        public void run() {

            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted e in myRunnable");
                    }
                }
            }
            if(SceneManager.ACTIVE_SCENE == 1){
                if(lastScene != 1){
                    adView.setVisibility(View.INVISIBLE);
                    // Start loading the ad in the background.
                    adView.loadAd(adRequest);
                    layout.removeView(adView);
                }
            }else{
                if(lastScene == 1){
                    adView.setVisibility(View.VISIBLE);
                    layout.addView(adView, adParams);
                }
            }
            handler.postDelayed(this, 100);
            lastScene = SceneManager.ACTIVE_SCENE;
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
