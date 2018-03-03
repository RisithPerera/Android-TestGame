package com.risith.testgame.instantFeature;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by RISITH-PC on 2018-03-02.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private RectPlayer player;
    private Point playerPoint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.surfaceHolder = getHolder();

        thread = new MainThread(surfaceHolder,this);
        player = new RectPlayer(new Rect(100,200,200,100), Color.rgb(0,0,255));
        playerPoint = new Point(150,150);
        setFocusable(true);

        System.out.println(getHolder());
        System.out.println(surfaceHolder);
    }

    private void canvasColor(int color) {
        try{
            this.canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder){
                super.draw(canvas);
                canvas.drawColor(Color.YELLOW);
                System.out.println("canvasColor runned");
            }
        }catch (Exception e){e.printStackTrace();
        }finally {
            if(canvas != null){
                try{
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("surfaceCreated");
        canvasColor(Color.GREEN);
        thread = new MainThread(surfaceHolder, this);
        //thread.getSurfaceHolder().lockCanvas().drawColor(Color.GREEN);
        thread.setRunning(true);
        thread.start();

        //---------------------------------------------------------//

        /*
        super.draw(thread.getCanvas());
        thread.getCanvas().drawColor(Color.GREEN);
        getHolder().unlockCanvasAndPost(thread.getCanvas());
        */

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int)event.getX(),(int)event.getY());
        }
        return true;
        //return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        player.draw(canvas);
        //System.out.println("draw game panel: "+color);
    }

    public void update(){
        player.update(playerPoint);
    }
}
