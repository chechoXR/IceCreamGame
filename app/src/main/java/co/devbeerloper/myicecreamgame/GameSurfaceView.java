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

    private ArrayList<AdultCream> adultCreams;
    private ArrayList<BoyCream> boyCreams;
    ArrayList<Integer> removeID;
    private Paint paint;
    private Paint paintCloud;
    private Paint paintAdult;
    private Paint paintBoy;

    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private Random rd;
    private Context context;
    private float screenWith;
    private float screenHeight;
    private int score;


    /**
     * Contructor
     *
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        icecreamCar = new IceCreamCar(context, screenWith, screenHeight);

        this.clouds = new ArrayList<Cloud>();

        this.adultCreams = new ArrayList<AdultCream>();
        this.boyCreams = new ArrayList<BoyCream>();
        paint = new Paint();
        paintCloud = new Paint();
        paintAdult = new Paint();
        paintBoy = new Paint();

        holder = getHolder();
        isPlaying = true;
        rd = new Random();
        this.context = context;
        this.screenWith = screenWith;
        this.screenHeight = screenHeight;
        this.score = 0;

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

        for (AdultCream a : adultCreams) {
            a.updateInfo();
        }

        for (BoyCream b : boyCreams) {
            b.updateInfo();
        }

    }

    private void paintFrame() {


        ArrayList<Integer> PosY = new ArrayList<Integer>();

        for (int i = 100; i < screenHeight; i += 150)
            PosY.add(i);

        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.rgb(52, 153, 255));

            //holder.unlockCanvasAndPost(canvas);
            removeID = new ArrayList<Integer>();


            //Si está en pantalla se pinta, sino se borra de la lista
            for (int i = 0; i < clouds.size(); i++) {
                if (clouds.get(i).isVisible())
                    canvas.drawBitmap(clouds.get(i).getSpriteIcecreamCar(), clouds.get(i).getPositionX(), clouds.get(i).getPositionY(), new Paint());
                else
                    removeID.add(i);
            }

            for (int i = 0; i < adultCreams.size(); i++) {
                if (adultCreams.get(i).isVisible())
                    canvas.drawBitmap(adultCreams.get(i).getSpriteIcecreamCar(), adultCreams.get(i).getPositionX(), adultCreams.get(i).getPositionY(), new Paint());
                else
                    removeID.add(i);
            }
            for (int i = 0; i < boyCreams.size(); i++) {
                if (boyCreams.get(i).isVisible())
                    canvas.drawBitmap(boyCreams.get(i).getSpriteIcecreamCar(), boyCreams.get(i).getPositionX(), boyCreams.get(i).getPositionY(), new Paint());
                else
                    removeID.add(i);
            }


            for (int i = 0; i < removeID.size(); i++)
                clouds.remove(removeID.get(i));
            for (int i = 0; i < removeID.size(); i++)
                adultCreams.remove(removeID.get(i));
            for (int i = 0; i < removeID.size(); i++)
                boyCreams.remove(removeID.get(i));


            removeID.clear();


            double porcentajeProbabilidad = 0.95;

            canvas.drawBitmap(icecreamCar.getSpriteIcecreamCar(), icecreamCar.getPositionX(), icecreamCar.getPositionY(), paint);
            int r = rd.nextInt(10000);
            if (r > 10000 * porcentajeProbabilidad) {
                Cloud cloud = new Cloud(getContext(), screenWith, screenHeight);

                int pos = rd.nextInt(PosY.size() - 1);
                AdultCream adultCream = new AdultCream(getContext(), screenWith, screenHeight, PosY.get(pos));
                Boolean b = PosY.remove((Object) pos);
                pos = rd.nextInt(PosY.size() - 1);

                if (r % 2 == 0) {
                    BoyCream boyCream = new BoyCream(getContext(), screenWith, screenHeight, PosY.get(pos));
                    boyCreams.add(boyCream);
                }

                //canvas.drawBitmap(cloud.getSpriteIcecreamCar(),cloud.getPositionX(),cloud.getPositionY(),paintCloud);
                clouds.add(cloud);
                adultCreams.add(adultCream);


                canvas.drawBitmap(cloud.getSpriteIcecreamCar(), cloud.getPositionX(), cloud.getPositionY(), new Paint());
                holder.unlockCanvasAndPost(canvas);


                ArrayList<BoyCream> newBoyCream = new ArrayList<BoyCream>();
                for (BoyCream boyCream1 : boyCreams)
                    if (checkBoyCollision(boyCream1))
                        score += 10;
                    else
                        newBoyCream.add(boyCream1);

                boyCreams = newBoyCream;

                ArrayList<AdultCream> newAdultCream = new ArrayList<AdultCream>();
                for (AdultCream adultCream1 : adultCreams)
                    if (checkAdultCollision(adultCream1))
                        score -= 10;
                    else
                        newAdultCream.add(adultCream1);

                adultCreams = newAdultCream;

            } else
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

    public boolean checkBoyCollision(BoyCream boyCream) {

        return icecreamCar.getPositionX() - boyCream.getPositionX() <= 5 && icecreamCar.getPositionY() - boyCream.getPositionY() <= 5;

    }

    public boolean checkAdultCollision(AdultCream adultCream) {
        return icecreamCar.getPositionX()- adultCream.getPositionX() <=5 && icecreamCar.getPositionY()- adultCream.getPositionY() <=5;
    }

}
