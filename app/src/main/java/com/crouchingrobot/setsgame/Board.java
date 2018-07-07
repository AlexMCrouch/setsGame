package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Board {
    private  BoardBackend boardBack = null;
    private int screenWidth = 0;
    private int screenHight = 0;
    private int leftPad = 30;
    private int rightPad = 30;
    private int topPad = 30;
    private int bottomPad = 100;
    private int peicePadR = 5;
    private int peicePadL = 5;
    private int peicePadT = 5;
    private int peicePadB = 5;
    private int peiceWidth = 0;
    private int peiceHight = 0;
    private int selCol = 0;
    private boolean touch = false;
    private boolean wasTouch = false;
    private int highlightColor = Color.WHITE;

    public Board(BoardBackend boardBack, int screenWidth, int screenHight){
        this.boardBack = boardBack;
        this.screenWidth = screenWidth;
        this.screenHight = screenHight;
        this.peiceWidth = ((screenWidth-(rightPad+leftPad))/boardBack.getWidth()) - (peicePadR+peicePadL);
        this.peiceHight = ((screenHight - (bottomPad+topPad))/boardBack.getHight()) - (peicePadT+peicePadB);
    }

    public void draw(Canvas canvas) {
        Paint peicePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect peiceSquare = new Rect();
        int horShift = peicePadL+peiceWidth;
        int verShift = peicePadT+peiceHight;
        Point p = new Point();

        //draw the highlighting first
        if(touch){
            peicePaint.setColor(highlightColor);
            p.set((leftPad + (horShift * (selCol + 1)) - peiceWidth/2), 0);
            peiceSquare.set(p.x - peiceWidth / 2, topPad, p.x + peiceWidth / 2, screenHight-bottomPad);
            canvas.drawRect(peiceSquare,peicePaint);
        }

        //If rising edge then remove a piece
        if(wasTouch && !touch){
            boardBack.columnClickHandler(selCol);
        }
        wasTouch = touch;

        for(int i = 0; i < boardBack.getWidth(); i++){
            for(int j = 0; j < boardBack.getHight(); j++) {
                p.set((leftPad + (horShift * (i + 1)) - peiceWidth/2), (topPad + (verShift * (j + 1))));
                peiceSquare.set(p.x - peiceWidth / 2, p.y - peiceHight / 2, p.x + peiceWidth / 2, p.y + peiceHight / 2);
                peicePaint.setColor(boardBack.getSpotColor(i, j));
                canvas.drawRect(peiceSquare,peicePaint);
                System.out.println(p.x+"  "+p.y);
            }
        }
    }

    public void update(Point point, boolean press) {
        touch = press;
        selCol = (point.x - leftPad)/(peiceWidth+peicePadR+peicePadL);
    }
}
