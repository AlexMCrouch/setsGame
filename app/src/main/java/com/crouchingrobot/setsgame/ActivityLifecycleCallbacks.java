package com.crouchingrobot.setsgame;

import android.app.Activity;
import android.os.Bundle;

public interface ActivityLifecycleCallbacks{
    public void onActivityStopped(Activity activity);
    public void onActivityStarted(Activity activity);
    public void onActivitySaveInstanceState(Activity activity, Bundle outState);
    public void onActivityResumed(Activity activity);
    public void onActivityPaused(Activity activity);
    public void onActivityDestroyed(Activity activity);
    public void onActivityCreated(Activity activity, Bundle savedInstanceState);
}
