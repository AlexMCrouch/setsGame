package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Board implements GameObject {
    private  BoardBackend boardBack = null;
    private int screenWidth = 0;
    private int screenHight = 0;
    private int leftPad = 30;
    private int rightPad = 30;
    private int topPad = 30;
    private int bottomPad = 30;
    private int peicePadR = 5;
    private int peicePadL = 5;
    private int peicePadT = 5;
    private int peicePadB = 5;
    private int peiceWidth = 0;
    private int peiceHight = 0;

    public Board(BoardBackend boardBack, int screenWidth, int screenHight){
        this.boardBack = boardBack;
        this.screenWidth = screenWidth;
        this.screenHight = screenHight;
        this.peiceWidth = ((screenWidth-(rightPad+leftPad))/boardBack.getWidth()) - (peicePadR+peicePadL);
        this.peiceHight = ((screenWidth - (bottomPad+topPad))/boardBack.getHight()) - (peicePadT+peicePadB);
    }
    @Override
    public void draw(Canvas canvas) {
        Paint peicePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect peiceSquare = new Rect();
        int horShift = peicePadL+peiceWidth/2;
        int verShift = peicePadT+peiceHight/2;
        Point p = new Point();
        for(int i = 0; i < boardBack.getWidth(); i++){
            for(int j = 0; j < boardBack.getHight(); j++) {
                p.set((leftPad + (horShift * (i + 1))), (topPad + (verShift * (j + 1))));
                peiceSquare.set(p.x - peiceWidth / 2, p.y - peiceHight / 2, p.x + peiceWidth / 2, p.y + peiceHight / 2);
                peicePaint.setColor(boardBack.getSpotColor(i, j));
                canvas.drawRect(peiceSquare,peicePaint);
            }
        }
    }

    @Override
    public void update() {

    }
}
