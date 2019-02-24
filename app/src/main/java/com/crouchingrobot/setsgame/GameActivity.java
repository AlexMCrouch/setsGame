package com.crouchingrobot.setsgame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class GameActivity extends AppCompatActivity {

        private SceneManager manager = new SceneManager();
        private long backHitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //This is my Ad ID so use this for publishing
        //MobileAds.initialize(this, "ca-app-pub-3501821828458096~4941816590");

        //Create the banner ad
        final AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        //This is the test Ad ID so use this for testing
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        //This is the real Ad ID so use this for publishing
        //adView.setAdUnitId("ca-app-pub-3501821828458096/3702980440");

//        //Create the interstatial ad
//        mInterstitialAd = new InterstitialAd(this);
//        //Test ad ID
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        //Real ad ID
//        //mInterstitialAd.setAdUnitId("ca-app-pub-3501821828458096/4826212622");


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
        manager.createScenes();
        ((JustLostScene)manager.scenes.get(2)).gHscore.readHighScore(this);



        setContentView(R.layout.activity_game_acctivity);
        layout.addView(gameView);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(layout);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            private int lastScene = 0;

            boolean interAdLoading = false;
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


//                        if (mInterstitialAd.isLoaded()) {
//                            mInterstitialAd.show();
//                            interAdLoading = false;
//                            System.out.println("Trying to show");
//                        }else{
//                            if(!interAdLoading){
//                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                                interAdLoading = true;
//                                System.out.println("starting loading");

                        //display banner ad since interstatal is not ready
                        adView.setVisibility(View.VISIBLE);
                        layout.addView(adView, adParams);


//                            }
                        //}
                    }
                }
                lastScene = SceneManager.ACTIVE_SCENE;
            }
        };

        handler.postDelayed(r, 100);



    }

    void writeHighScore(){

        if(manager.scenes.get(2)!= null){
            ((JustLostScene)manager.scenes.get(2)).gHscore.writeHighScore(this);
        }
    }

    protected void onResume() {
        // TODO Auto-generated method stub
        System.out.println("Resume Game");
        super.onResume();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        System.out.println("onPause Game");
        writeHighScore();
        //System.exit(0);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("onDestroy Game");
        super.onDestroy();
    }

    @Override
    protected void onStart() {

        System.out.println("onStart Game");
        super.onStart();
    }

    @Override
    protected void onStop() {

        System.out.println("onStop Game");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("outState Game");
        writeHighScore();
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onBackPressed() {
        if(backHitTime + 1000 > System.currentTimeMillis()){
            moveTaskToBack(true);
        }else{
            backHitTime = System.currentTimeMillis();
        }

    }
}
