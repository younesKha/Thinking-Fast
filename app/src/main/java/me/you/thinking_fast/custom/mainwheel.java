package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import me.you.thinking_fast.R;
import me.you.thinking_fast.canvas.gameCanvas;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.game;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class mainwheel extends View {



    private  float ypos_x;
    private  ValueAnimator animation;
    private Paint _ringPaint ;


    private float x = 0 ;


    private wheel_listener m_wheel_listener;

    private Paint _ringPaint2 ;
    long time1;



    float xpos = 50;
    float ypox = 50;
    float rid = 2800;
    float lineWight = 600;

    float _rid = 2800;
    float  _xp= ((xpos/100)* getWidth()) - (rid/2);
    float _yp = ((ypox/100)* getHeight()) -  (rid/2);
    float _lineWight = 600;
    private ValueAnimator animStop;
    private ValueAnimator animStop_red;
    public float _rid_base;
    private boolean stop_not_entered= true;
    private ValueAnimator anim_stop_time;
    private int zoomOut_am;
    private float xpos_x;
    private boolean StartProgress = false;


    public mainwheel(Context context, float xpos, float ypox, int rid, int lineWight , int color1,int color2) {
        super(context);
        this.xpos = xpos;
        this.ypox = ypox;
        this.rid = rid;
        this.lineWight = lineWight;

        zoomOut_am = my_app.game_width/300;
Log.i("zoomOut_am",zoomOut_am +"");
        this._rid = ((this.rid / 100) * my_app.game_Height);
        this._xp = ((this.xpos / 100) * my_app.game_width) - (_rid / 2);
        this._yp = ((this.ypox / 100) * my_app.game_Height) - (_rid / 2);
        this._lineWight = ((this.lineWight / 100) * (_rid / 2));

        _rid_base = _rid;
        my_app.wheel_rid = (int) this._rid;


      xpos_x=  ((this.xpos / 100) * my_app.game_width);
        ypos_x=  ((this.ypox / 100) * my_app.game_Height);
       // Log.i("getHeight_layout",getWidth() + "::"+ t );



            init();
    }


    private void init() {



        _ringPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        _ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);






        Log.i("getHeight_cons",getWidth() + "" );

        //    getResources().getIdentifier()
        //   ridd_bitmap = BitmapFactory.decodeStream(is);



        setLayerType(View.LAYER_TYPE_HARDWARE, null);






        animation =  ValueAnimator.ofFloat(0, 360);
        animation.setDuration(2001);
        animation.setStartDelay(440);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float valueToDraw = (float) valueAnimator.getAnimatedValue();
                x=valueToDraw;

                m_wheel_listener.tickWheel(valueToDraw);
                if(x >= 200 && stop_not_entered){
                    stop_not_entered = false;
                    m_wheel_listener.onStopTimeArea();
                }
                _rid =_rid - zoomOut_am ;
                invalidate();
            }

        });


        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            time1 = System.currentTimeMillis();
               StartProgress = true;
                _rid =  _rid_base ;

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if(x == 360 && StartProgress) {
                    StartProgress = false;
                    m_wheel_listener.onWheelEndWithNoAswer(animator);
                }
               // my_app.Show_fail_Dialog(game.frag_mngr);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animStop =  ValueAnimator.ofInt(150, 30);
        animStop.setDuration(300);
        animStop.setInterpolator(new AccelerateDecelerateInterpolator());
        animStop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                _ringPaint2.setAlpha((Integer) valueAnimator.getAnimatedValue());
                invalidate();
            }

        });

        animStop.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                x  =0;
                _ringPaint2.setAlpha(255);
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        animStop_red =  ValueAnimator.ofInt(150, 30);
        animStop_red.setDuration(300);
        animStop_red.setInterpolator(new AccelerateDecelerateInterpolator());
        animStop_red.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                _ringPaint2.setAlpha((Integer) valueAnimator.getAnimatedValue());
                invalidate();
            }

        });

        animStop_red.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                x  =0;
                _ringPaint2.setAlpha(255);
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        // animation.start();

    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        _ringPaint2.setShader(new RadialGradient( getWidth(),
                getHeight()/2,
                getHeight(),
                my_app.game_eng.current_level.getTextColor(), my_app.game_eng.current_level.getColor2(),
                Shader.TileMode.CLAMP));

    }


    ////////////////////////////////////////////////////////////////////////

    public void setwheel_zero(){
        animation.cancel();
        animStop.start();

    }


    public void stop_time_anim(){
        animation.cancel();

        anim_stop_time =  ValueAnimator.ofFloat(x, 0);
        anim_stop_time.setDuration(300);
        anim_stop_time.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_stop_time.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                x = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }

        });


        anim_stop_time.start();
    }
    public void make_red() {

        animStop_red.start();

    }

    public long get_spend_time() {
      return  System.currentTimeMillis() - time1 ;
    }

    //////////////////////////////////////////////////////////////////////////////

    public mainwheel(Context context, AttributeSet attrs) {
        super(context, attrs);



    }

    public mainwheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);

    }




    @Override
        protected void onDraw(Canvas canvas) {
          //  Log.i("onDraw","onDraw round");
            super.onDraw(canvas);


       canvas.drawCircle(getWidth()/2,getHeight()/2,rid,_ringPaint);


        this._xp = xpos_x  - (_rid / 2);
        this._yp =  ypos_x - (_rid / 2);

      //   _lineWight = _lineWight + 1;
        canvas.drawArc(new RectF(_xp ,_yp ,_xp + _rid,_yp + _rid),0,x,true,_ringPaint2);

     _ringPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
      //  canvas.drawArc(new RectF(_xp + _lineWight ,_yp +_lineWight ,_xp +_rid - _lineWight ,_yp +_rid - _lineWight ),0,360,true,_ringPaint);


        canvas.drawCircle(xpos_x,ypos_x,_rid/2 - _lineWight  , _ringPaint);



    }


    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);




    }

    public void reset(long reqtime) {
        x= 0;

        animation.setDuration(reqtime);
        stop_not_entered =true;
         animation.start();


    }


    public void set_wheel_listener(wheel_listener wheel_listener) {
        this.m_wheel_listener = wheel_listener;
    }

    public void setwheel_colors(int color1,int color2) {

        _ringPaint2.setShader(new RadialGradient(0,
                0,
                my_app.game_Height,
                color1,
                color1,
                Shader.TileMode.CLAMP));

        invalidate();

    }

    public void stopanimation() {
        animation.cancel();
    }


    public interface wheel_listener{
        public void onWheelEndWithNoAswer(Animator animator);
        public void onStopTimeArea();
        public void tickWheel(float val);

    }
}
