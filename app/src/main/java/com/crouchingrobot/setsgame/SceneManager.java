package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class SceneManager {
    private static ArrayList<Scene> scenes = new ArrayList();
    public static int ACTIVE_SCENE;
    public static int LAST_SCENE;
    public SceneManager(){
        ACTIVE_SCENE = 0;
        LAST_SCENE = 0;
        scenes.add(new MainMenuScene());
        scenes.add(null);
        scenes.add(new JustLostScene());
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
        LAST_SCENE = ACTIVE_SCENE;
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    public static void newGame(){
        scenes.remove(1);
        scenes.add(1,new GameplayScene());
    }

}
