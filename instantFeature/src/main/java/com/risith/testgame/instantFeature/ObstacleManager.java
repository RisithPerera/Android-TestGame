package com.risith.testgame.instantFeature;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by RISITH-PC on 2018-03-04.
 */

public class ObstacleManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;
    private int score = 0;

    public ObstacleManager(int playerGap,int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player){
        for (Obstacle ob: obstacles) {
            if(ob.playerCollide(player)){
                return true;
            }
        }
        return false;
    }
    private void populateObstacles(){
        int currY = -5*Constant.SCREEN_HEIGHT/4;

        while(currY < 0){
            int xStart = (int)(Math.random()*(Constant.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight,color,xStart,currY,playerGap));
            currY += obstacleHeight+obstacleGap;
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle ob: obstacles) {
            ob.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.YELLOW);
        canvas.drawText(String.format("[%s]",score),50,50 + paint.descent()- paint.ascent(),paint);
    }

    public void update(){
        if(startTime < Constant.INIT_TIME){
            startTime = Constant.INIT_TIME;
        }
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();
        float speed = (float) (Math.sqrt(1 + (startTime-initTime)/2000.0)*Constant.SCREEN_HEIGHT/10000.0f);
        //float speed = Constant.SCREEN_HEIGHT/10000.0f);
        for(Obstacle ob : obstacles){
            ob.incrementY(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size()-1).getRectangle().top >= Constant.SCREEN_HEIGHT){
            int xStart = (int)(Math.random()*(Constant.SCREEN_WIDTH - playerGap));
            obstacles.add(0,new Obstacle(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top-obstacleHeight-obstacleGap, playerGap));
            obstacles.remove(obstacles.size() -1);
            score++;
        }
    }
}
