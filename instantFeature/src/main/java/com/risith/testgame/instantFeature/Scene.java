package com.risith.testgame.instantFeature;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by RISITH-PC on 2018-03-06.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent event);
}
