package com.risith.testgame.instantFeature;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by RISITH-PC on 2018-03-04.
 */

public class Obstacle implements GameObject {

    private Rect rectangle1, rectangle2;
    private int color;


    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        rectangle1 = new Rect(0,startY,startX,startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap,startY,Constant.SCREEN_WIDTH,startY+rectHeight);

    }

    public boolean playerCollide(RectPlayer player){
       return Rect.intersects(rectangle1, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle1,paint);
        canvas.drawRect(rectangle2,paint);

    }

    @Override
    public void update() {

    }

    public Rect getRectangle(){
        return rectangle1;
    }

    public void incrementY(float y){
        rectangle1.top += y;
        rectangle1.bottom += y;

        rectangle2.top += y;
        rectangle2.bottom += y;
    }
}
