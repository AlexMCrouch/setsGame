package com.crouchingrobot.setsgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, GameActivity.class);
        //EditText editText = (EditText) findViewById(R.id.FullscreenActivity);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();

    }


    protected void onResume() {
        // TODO Auto-generated method stub
        System.out.println("Resume Main");
        super.onResume();
        startGameActivity();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        System.out.println("onPause Main");
        //System.exit(0);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("onDestroy Main");
        super.onDestroy();
    }

    @Override
    protected void onStart() {

        System.out.println("onStart Main");
        super.onStart();
    }

    @Override
    protected void onStop() {

        System.out.println("onStop Main");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("outState Main");
        super.onSaveInstanceState(outState);
    }

    private void startGameActivity(){
        intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

}

