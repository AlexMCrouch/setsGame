package com.crouchingrobot.setsgame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {
    public RelativeLayout layout;
    private SceneManager manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This is my Ad ID so use this for publishing
        MobileAds.initialize(this, "ca-app-pub-3501821828458096~4941816590");

        //Create the banner ad
        final AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        //This is the test Ad ID so use this for testing
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        //This is the real Ad ID so use this for publishing
        //adView.setAdUnitId("ca-app-pub-3501821828458096/3702980440");

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        final RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,    WindowManager.LayoutParams.MATCH_PARENT));
        // Create an ad request.
        // get test ads on a physical device.
        final AdRequest adRequest = new AdRequest.Builder().build();





        //define ad view parameters
        final RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);



        manager = new SceneManager();
        //Create game view
        View gameView = new GamePanel(this,manager);

        layout.addView(gameView);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(layout);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            private int lastScene = 0;
            public void run() {
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
        };
        handler.postDelayed(r, 100);

    }

}
