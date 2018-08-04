package com.crouchingrobot.setsgame;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.os.Bundle;

public class MyApplication extends Application implements ActivityLifecycleCallbacks{

@Override
public void onCreate() {
        super.onCreate();
        }
    @Override
public void onActivityStopped(Activity activity) {
        Log.i("TA Stopped", activity.getLocalClassName());

        }
    @Override
public void onActivityStarted(Activity activity) {
        Log.i("TA Started", activity.getLocalClassName());

        }
    @Override
public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i("TA SaveInstanceState", activity.getLocalClassName());
        }
    @Override
public void onActivityResumed(Activity activity) {
        Log.i("TA Resumed", activity.getLocalClassName());
        MainThread.onResume();
        MyRunnable.onPause();
    }
    @Override
public void onActivityPaused(Activity activity) {
        Log.i("TA Paused", activity.getLocalClassName());
        MainThread.onPause();
        MyRunnable.onPause();

    }
    @Override
public void onActivityDestroyed(Activity activity) {
        Log.i("TA Destroyed", activity.getLocalClassName());
        }
    @Override
public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i("TA Created", activity.getLocalClassName());
        }
        }