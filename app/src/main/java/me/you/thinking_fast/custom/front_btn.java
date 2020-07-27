package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RadioButton;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;

/**
 * Created by younes_hp on 4/8/2017.
 */

public class front_btn extends View implements  View.OnClickListener , View.OnTouchListener{

    private final ValueAnimator anim_loop;
    private final Paint Paint_bg;
    Bitmap btn_img;
    private Paint imgPaint;
    private int movx =1;
   int imgResId ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public front_btn(Context context, int imgResId) {
        super(context);
        Log.i("getMeasuredHeight",getMeasuredHeight() + " ");

        this.imgResId= imgResId;
        btn_img = BitmapFactory.decodeResource(getResources(), imgResId);
       // return Bitmap.createScaledBitmap(bbb,getHeight()/2  , getHeight()/2 , false);
        imgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      //  imgPaint.setAlpha(100);

        Paint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint_bg.setColor(getResources().getColor(R.color.white));
        Paint_bg.setAlpha(0);


        setOnClickListener(this);
        setOnTouchListener(this);

        setTransitionName("sett_fram");


        anim_loop =  ValueAnimator.ofInt(0, my_app.game_width/50);
        anim_loop.setDuration(1000);
        anim_loop.setStartDelay(1000);
        anim_loop.setRepeatCount(3);
        anim_loop.setRepeatMode(ValueAnimator.REVERSE);
        anim_loop.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_loop.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_loop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int valueToDraw = (int) valueAnimator.getAnimatedValue();

                movx =   valueToDraw;
                invalidate();
            }
        });
        anim_loop.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
              //  anim_loop.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
      //  anim_loop.start();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


        btn_img=  Bitmap.createScaledBitmap(btn_img, (int) (getWidth()/1.8f), (int) (getHeight()/1.8f), false);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawPaint(imgPaint);



    //    canvas.drawRoundRect(new RectF(20,20,getWidth()-20,getHeight()-20),50,100,Paint_bg);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,Paint_bg);
            canvas.drawBitmap(btn_img, getWidth() / 2 - btn_img.getWidth() / 2 + movx, getHeight() / 2 - btn_img.getHeight() / 2 - movx, imgPaint);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN) {
           // imgPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
            //imgPaint.setARGB(233,245,234,145);
            ColorFilter emphasize = new LightingColorFilter(getResources().getColor(android.R.color.white),getResources().getColor(android.R.color.white) );
            imgPaint.setColorFilter(emphasize);
          //  Paint_bg.setColor(getResources().getColor(R.color.blow_light2));
         //   imgPaint.setAlpha(150);
            Paint_bg.setAlpha(150);
            invalidate();
            return false;
        }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
          //  imgPaint.setColorFilter(null);
         //   Paint_bg.setColor(getResources().getColor(R.color.blow_dark));
         //   imgPaint.setAlpha(0);
            Paint_bg.setAlpha(0);

            invalidate();
            return false;
        }
    return false;
    }

}
