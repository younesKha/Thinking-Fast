package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import me.you.thinking_fast.engine.my_app;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class wiave_view extends View  {


    private Paint mPaint = null;

    private ValueAnimator anim;


    private float mangle;
    private float pos_x,pos_y,rid;


    public wiave_view(Context context) {
        super(context);



       init();
    }

    public  void do_waive(float pos_x,float pos_y,int color_res_id){
        this.pos_x =pos_x;
        this.pos_y = pos_y;
        mPaint.setColor(getResources().getColor(color_res_id));
        anim.start();
    }

    public void start_motin(){
        anim.start();
    }


    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(android.R.color.holo_green_light));
        //mPaint.setAlpha(20);

         setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        anim =  ValueAnimator.ofFloat(my_app.game_width/10, my_app.game_width/3);
        anim.setDuration(301);

        anim.setInterpolator(new DecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rid =(float) valueAnimator.getAnimatedValue();

                mPaint.setAlpha((int) rid );
                invalidate();
            }

        });


        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mPaint.setAlpha(0);
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }




    public wiave_view(Context context, AttributeSet attrs) {
        super(context, attrs);



    }

    public wiave_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
        protected void onDraw(Canvas canvas) {
          //  Log.i("onDraw","onDraw round");
            super.onDraw(canvas);
        //canvas.drawPaint(mPiePaint);
         canvas.drawCircle(pos_x,pos_y,rid,mPaint);

    }


    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);


        pos_x = getWidth() /4;
        pos_y = getHeight() - getHeight()/7;



    }







}
