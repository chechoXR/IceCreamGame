package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;


public class AdultCream {

    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public static final int SPRITE_SIZE_WIDTH =59;
    public static final int SPRITE_SIZE_HEIGTH=70;
    public static final float GRAVITY_FORCE=0;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteIcecreamCar;
    private boolean isJumping;
    private boolean visible = true;


    public AdultCream(Context context, float screenWidth, float screenHeigth){
        Random rd = new Random();

        speed = 1;
        positionX =screenWidth;
        positionY =rd.nextInt((int)(screenHeigth));
        isJumping = false;
        //Getting bitmap from resource


        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo);

        spriteIcecreamCar  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteIcecreamCar.getWidth()/2);
        this.maxY = screenHeigth - spriteIcecreamCar.getHeight();
    }


    public AdultCream(Context context, float screenWidth, float screenHeigth, int y){
        Random rd = new Random();

        speed = 1;
        positionX =screenWidth;
        positionY =y;
        isJumping = false;
        //Getting bitmap from resource


        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo);

        spriteIcecreamCar  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteIcecreamCar.getWidth()/2);
        this.maxY = screenHeigth - spriteIcecreamCar.getHeight();
    }


    public AdultCream(Context context, float initialX, float initialY, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = initialX;
        positionY = initialY;


        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo);

        spriteIcecreamCar  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteIcecreamCar.getWidth()/2);
        this.maxY = screenHeigth - spriteIcecreamCar.getHeight();

    }

    public static float getInitX() {
        return INIT_X;
    }

    public static float getInitY() {
        return INIT_Y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpriteIcecreamCar() {
        return spriteIcecreamCar;
    }

    public void setSpriteIcecreamCar(Bitmap spriteIcecreamCar) {
        this.spriteIcecreamCar = spriteIcecreamCar;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    /**
     * Control the position and behaviour of the icecream car
     */
    public void updateInfo () {

        /*
        if (isJumping) {
            speed += 5;
        } else {
            speed -= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }
        this.positionY -= speed - GRAVITY_FORCE;

        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }

*/      if(this.positionX>-100)
            this.positionX-=5;
        else
            this.visible=false;



    }

    public boolean isVisible() {
        return visible;
    }

    public int random (){
        int a = (int) Math.random()*4;
        return a;
    }

}
