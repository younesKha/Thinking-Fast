package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class stop_time_view extends View  implements View.OnTouchListener{


    private Paint btn_Paint,btn_text;
    Handler handler = new Handler(Looper.getMainLooper());
    private  ValueAnimator animation;

    private float rid = 90;
    private float dx = 6;
    private float init_rid;
    private Paint btn_Paint2,btn_Paint3;
    private ValueAnimator animation2;
    private float rid2 = 0;
    private ValueAnimator animation_end;
    private int cntr_width;
    private int cntr_height;
    private boolean clickable = false;
    private Bitmap stop_bitmap;
    private ValueAnimator animation11;
    private int hidOffest;
    private Paint btn_text_w;

    public void setStopTimeListener(StopTimeListener stopTimeListener) {
        this.stopTimeListener = stopTimeListener;
    }

    private StopTimeListener stopTimeListener;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public stop_time_view(Context context) {
        super(context);
       // setTransitionName("start_game");
        init();
    }



    private void init() {

        stop_bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.stoptime);

        stop_bitmap = Bitmap.createScaledBitmap(stop_bitmap,my_app.game_Height/12 , my_app.game_Height/12 , false);


        setOnTouchListener(this);
      //  setOnClickListener(this);
        btn_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint.setColor(getResources().getColor(R.color.blow_3));
        btn_Paint.setColor(getResources().getColor(R.color.gray_light));
        btn_Paint.setStyle(Paint.Style.FILL_AND_STROKE);
        btn_Paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint2.setColor(getResources().getColor(R.color.blow_4));

        btn_Paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);




        btn_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_text.setColor(getResources().getColor(android.R.color.holo_red_dark));
        btn_text.setTextSize(my_app.game_width/17);






        animation11 =  ValueAnimator.ofInt(my_app.game_width/10, 0);
        animation11.setDuration(200);
        animation11.setInterpolator(new DecelerateInterpolator());
        animation11.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                 hidOffest = (int) valueAnimator.getAnimatedValue();

            }
        });



            animation =  ValueAnimator.ofFloat(0, my_app.game_width/12);
        animation.setDuration(200);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float valueToDraw = (float) valueAnimator.getAnimatedValue();
                rid =valueToDraw;

               // btn_text.setAlpha(100 - (int) valueToDraw);
                invalidate();
            }
        });

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                animation11.start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                rid2 = rid;
                animation2.start();
               // btn_Paint2.setAlpha(150);
               // mOnmovtionEndListener.OnmovtionEndListener();
                 Log.i("anim","end");

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });



        animation2 =  ValueAnimator.ofFloat(0, my_app.game_width/30);
            animation2.setDuration(200);
            animation2.setRepeatCount(20);
            animation2.setRepeatMode(ValueAnimator.REVERSE);
            animation2.setInterpolator(new DecelerateInterpolator());
            animation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                 rid2 = (float) valueAnimator.getAnimatedValue();

                // btn_text.setAlpha(100 - (int) valueToDraw);
                invalidate();
            }
        });

final int endhightoffset = my_app.game_Height /180;
        animation_end =  ValueAnimator.ofInt(200, 0);
        animation_end.setDuration(300);
        animation_end.setInterpolator(new DecelerateInterpolator());
        animation_end.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int valueToDraw = (int) valueAnimator.getAnimatedValue();
               // rid2 =valueToDraw;
              //  rid=valueToDraw;
                btn_Paint.setAlpha(valueToDraw);
                btn_Paint2.setAlpha(valueToDraw);
                btn_text.setAlpha(valueToDraw);
                cntr_height += endhightoffset;
                invalidate();

            }
        });



    }

    public  void resume() {
     //   btn_text.setAlpha(255);
        rid = init_rid;
    }



    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);


        init_rid = 0.4f * getWidth();
      //  rid = init_rid;
        rid = 0; rid2=0;
        btn_Paint.setAlpha(0);
        btn_Paint2.setAlpha(0);
        btn_text.setAlpha(0);
        cntr_width = getWidth()/2 ;
        cntr_height = getHeight()/2 ;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int cntr_width_dt  =    cntr_width- hidOffest;
        //  canvas.drawPaint(btn_Paint2);
        canvas.drawCircle(cntr_width_dt,cntr_height,rid  + rid2, btn_Paint2);

        canvas.drawCircle(cntr_width_dt,cntr_height,rid, btn_Paint);

        canvas.drawBitmap(stop_bitmap,cntr_width_dt - stop_bitmap.getWidth()/2,cntr_height - stop_bitmap.getWidth()/2,btn_Paint);

        canvas.drawText(String.valueOf(my_app.game_eng.getStop_time_points()),cntr_width_dt,cntr_height -  stop_bitmap.getWidth()/2, btn_text);
    }


    public void start() {

        if(my_app.game_eng.getStop_time_points() > 0) {



            rid = 0;
            rid2 = 0;
            clickable = true;
            cntr_width = getWidth() / 2;
            cntr_height = getHeight() / 2;

            btn_Paint.setAlpha(200);
            btn_Paint2.setAlpha(150);
            btn_text.setAlpha(230);
/*
        btn_Paint2.setShader(new RadialGradient(
                getWidth()/2,
                getHeight()/2,
                getHeight()/3,
                my_app.game_eng.current_level.getColor2(),
                my_app.game_eng.current_level.getColor1(),
                Shader.TileMode.CLAMP
        ));
*/
            btn_Paint2.setColor(my_app.game_eng.current_level.getColor2());

            btn_Paint.setColor(getResources().getColor(android.R.color.white));

            animation.start();
        }

    }

    public void hide( ) {
        rid = 0;rid2 = 0;
        animation2.cancel();

        btn_Paint.setAlpha(0);
        btn_Paint2.setAlpha(0);
        btn_text.setAlpha(0);
        invalidate();
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN) {


            if(  clickable ){
                clickable = false;
            stopTimeListener.OnStopTimeTouchListener();
           // rid2 = 50;
            animation_end.start();
                my_app.game_eng.sub_Stop_time_points();
            //  mPaint.setColor(my_app.game_eng.current_level.getColor1());

                btn_Paint.setColor(my_app.game_eng.current_level.getColor2());
                btn_Paint2.setColor(my_app.game_eng.current_level.getColor2());

             /*
                btn_Paint2.setShader(new RadialGradient(
                    getWidth()/2,
                    getHeight()/2,
                    getHeight()/3,
                    my_app.game_eng.current_level.getColor1(),
                    my_app.game_eng.current_level.getColor2(),
                    Shader.TileMode.CLAMP
            ));

            btn_Paint.setShader(new RadialGradient(
                    getWidth()/2,
                    getHeight()/2,
                    getHeight()/2,
                    my_app.game_eng.current_level.getColor1(),
                    my_app.game_eng.current_level.getColor2(),
                    Shader.TileMode.CLAMP
            ));
                    */
                invalidate();
            return false;
        }}
        return false;
    }

    public interface StopTimeListener {
        void OnStopTimeTouchListener();
    }


}
