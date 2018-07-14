package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SetTracker implements GameObject{
    private BoardBackend boardBack = null;
    private Point botLeft = null;
    private Point topRight = null;
    private int inbetweenPad = 30;

    //Pass the backend of the current board so that the data is all their for painting the sets.
    //Pass the botLeft point of where the set should be drawn
    public SetTracker(BoardBackend boardBack, Point botLeft, Point topRight){
        this.boardBack = boardBack;
        this.botLeft = botLeft;
        this.topRight = topRight;
    }
    public void draw(Canvas canvas){
        Paint setPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect setSquare = new Rect();
        int setColor = boardBack.getSetColor();

        //Set the color to the color of the pieces set
        setPaint.setColor(setColor);

        int numInSet = boardBack.getNumInSet();
        int widthOfEach = ((topRight.x - botLeft.x)-(inbetweenPad*2))/3;

        for(int i = 0; i < numInSet; i++){
            //B T R B
            setSquare.set(botLeft.x + (widthOfEach*(i)) + inbetweenPad*i,
                    topRight.y,botLeft.x + (widthOfEach*(i+1)) + inbetweenPad*i,botLeft.y);
            canvas.drawRect(setSquare,setPaint);
        }

    }
    public void update(){

    }

}
