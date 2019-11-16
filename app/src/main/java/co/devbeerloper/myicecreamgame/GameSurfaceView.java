package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private IceCreamCar icecreamCar;

    private ArrayList<Cloud> clouds;
    ArrayList<Integer> removeID;
    private Paint paint;
    private Paint paintCloud;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private Random rd;
    private Context context;
    private float screenWith;
    private float screenHeight;

    /**
     * Contructor
     *
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        icecreamCar = new IceCreamCar(context, screenWith, screenHeight);

        this.clouds = new ArrayList<Cloud>();
        paint = new Paint();
        paintCloud = new Paint();
        holder = getHolder();
        isPlaying = true;
        rd = new Random();
        this.context = context;
        this.screenWith = screenWith;
        this.screenHeight = screenHeight;
    }

    /**
     * Method implemented from runnable interface
     */
    @Override
    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();

        }

    }

    private void updateInfo() {
        icecreamCar.updateInfo();

        for (Cloud c : clouds) {
            c.updateInfo();
        }

    }

    private void paintFrame() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.rgb(52, 153, 255));


            //holder.unlockCanvasAndPost(canvas);
            removeID = new ArrayList<Integer>();


            for (int i=0; i< clouds.size(); i++) {
             if(clouds.get(i).isVisible())
                canvas.drawBitmap(clouds.get(i).getSpriteIcecreamCar(), clouds.get(i).getPositionX(), clouds.get(i).getPositionY(), new Paint());
             else
                removeID.add(i);
            }

            for (int i=0; i< removeID.size(); i++)
                clouds.remove(removeID.get(i));

            removeID.clear();


            canvas.drawBitmap(icecreamCar.getSpriteIcecreamCar(), icecreamCar.getPositionX(), icecreamCar.getPositionY(), paint);
            int r = rd.nextInt(100);
            if (r > 95) {
                Cloud cloud = new Cloud(getContext(), screenWith, screenHeight);
                //canvas.drawBitmap(cloud.getSpriteIcecreamCar(),cloud.getPositionX(),cloud.getPositionY(),paintCloud);
                clouds.add(cloud);
                canvas.drawBitmap(cloud.getSpriteIcecreamCar(), cloud.getPositionX(), cloud.getPositionY(), new Paint());
                holder.unlockCanvasAndPost(canvas);
            }
            else
                holder.unlockCanvasAndPost(canvas);


        }

    }


    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    /**
     * Detect the action of the touch event
     *
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                System.out.println("TOUCH UP - STOP JUMPING");
                icecreamCar.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                System.out.println("TOUCH DOWN - JUMP");
                icecreamCar.setJumping(true);
                break;
        }
        return true;
    }

}
