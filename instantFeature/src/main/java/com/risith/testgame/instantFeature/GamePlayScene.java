package com.risith.testgame.instantFeature;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by RISITH-PC on 2018-03-06.
 */

public class GamePlayScene implements Scene {

    private SceneManager manager;

    private Rect r = new Rect();
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;
    private boolean gameOver = false;

    private long gameOverTime;

    private  OrientationData orientationData;
    private long frameTime;

    public GamePlayScene(){
        player = new RectPlayer(new Rect(0, 0, 100, 100), Color.rgb(0, 0, 255));
        playerPoint = new Point(Constant.SCREEN_WIDTH/2,3*Constant.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY())){
                    movingPlayer = true;
                }
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000){
                    reset();
                    gameOver = false;
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer){
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    @Override
    public void update() {
        if(!gameOver){
            if(frameTime<Constant.INIT_TIME){
                frameTime = Constant.INIT_TIME;
            }
            int elapsedTime = (int) (System.currentTimeMillis()-frameTime);
            frameTime = System.currentTimeMillis();
            if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null){
                float pitch = orientationData.getOrientation()[1]-orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2]-orientationData.getStartOrientation()[2];

                float xSpeed = roll * Constant.SCREEN_WIDTH/1000;
                float ySpeed = pitch * Constant.SCREEN_HEIGHT/1000;

                playerPoint.x += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed*elapsedTime : 0;
                playerPoint.y += Math.abs(ySpeed*elapsedTime) > 5 ? ySpeed*elapsedTime : 0;

            }

            if(playerPoint.x<0){
                playerPoint.x = 0;
            }else if(playerPoint.x> Constant.SCREEN_WIDTH){
                playerPoint.x = Constant.SCREEN_WIDTH;
            }

            if(playerPoint.y<0){
                playerPoint.y = 0;
            }else if(playerPoint.y> Constant.SCREEN_HEIGHT){
                playerPoint.y = Constant.SCREEN_HEIGHT;
            }

            player.update(playerPoint);
            obstacleManager.update();
            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(77,116,57));
        obstacleManager.draw(canvas);
        player.draw(canvas);

        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.YELLOW);
            drawCenterText(canvas,paint,"Game Over");
        }
    }

    public void reset(){
        playerPoint = new Point(Constant.SCREEN_WIDTH/2,3*Constant.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        movingPlayer = false;
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
