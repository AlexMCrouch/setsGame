package com.crouchingrobot.setsgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class MyDraw {
    public static void drawCenter(Canvas canvas, Paint paint, String text, Point p) {
        Rect r = new Rect();
        canvas.getClipBounds(r);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = p.x - r.width() / 2f - r.left;
        float y = p.y + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
