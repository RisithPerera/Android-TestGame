package com.risith.testgame.instantFeature;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by RISITH-PC on 2018-03-04.
 */

public class Obstacle implements GameObject {

    private Rect rectangle;
    private int color;

    public Obstacle(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public boolean playerCollide(RectPlayer player){
        if(rectangle.contains(player.getRectangle().left, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().left, player.getRectangle().top)
                || )
    }
    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update() {

    }
}
