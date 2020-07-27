package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.my_app;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class option_background extends LinearLayout implements View.OnClickListener {



private  int rid_r = 0;

    private ValueAnimator back_anim;


    private Paint backPaint;
    private int end_state = 0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public option_background(Context context) {

        super(context);


       init();
    }

    public void show(int end_state){

        //---DEACTIVE_CODE
      //  my_app.game_eng.add_Stop_time_points();
      //  my_app.game_eng.add_Stop_time_points();
      //-------------------


        this.end_state = end_state;
        back_anim.start();





    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        setTag("OPTION_PACK");

        setWillNotDraw(false);



       backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setAntiAlias(true);
        backPaint.setColor(getResources().getColor(android.R.color.black));
        backPaint.setAlpha(220);






    }





    public option_background(Context context, AttributeSet attrs) {
        super(context, attrs);



    }

    public option_background(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //  super.onLayout(b,l, t, r, b);
        super.onLayout(b, i, i1, i2, i3);







        back_anim =  ValueAnimator.ofInt( 0, my_app.game_Height + my_app.game_Height/3);
        back_anim.setDuration(801);
        back_anim.setInterpolator(new AccelerateDecelerateInterpolator());
        back_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rid_r = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }

        });


        back_anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                switch (end_state){

                    case core.END_LEVEL_SUSSES:
                        backPaint.setShader(new RadialGradient(my_app.game_width/2,
                                0,
                                getHeight(),
                                my_app.game_eng.current_level.getColor2(),
                                my_app.game_eng.current_level.getColor1(),
                                Shader.TileMode.CLAMP));
                        backPaint.setAlpha(230);

                        break;

                    default:
                        backPaint.setShader(new RadialGradient(my_app.game_width/2,
                                0,
                                getHeight(),
                                getResources().getColor(android.R.color.holo_blue_light),
                                getResources().getColor(android.R.color.black),
                                Shader.TileMode.CLAMP));
                        break;

                }

                
            }

            @Override
            public void onAnimationEnd(Animator animator) {



            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });






    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);




    }

    @Override
        protected void onDraw(Canvas canvas) {
          //  Log.i("onDraw","onDraw round");
            super.onDraw(canvas);
         //  canvas.drawPaint(backPaint);
     //   canvas.drawRoundRect(new RectF(pos_x-FramWidth,pos_y-FramHeight,pos_x+FramWidth,pos_y+FramHeight),30,30,mPaint);

       // canvas.drawCircle(cntr_width,my_app.game_width, rid_r ,porderPaint);
        canvas.drawCircle(my_app.center_width,my_app.game_Height, rid_r,backPaint);
     //   canvas.drawCircle(getWidth()/3,getWidth()/3,getWidth()/1.7f,bublePaint);


        //    backPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));



    }


    @Override
    public void onClick(View view) {

    }
}
