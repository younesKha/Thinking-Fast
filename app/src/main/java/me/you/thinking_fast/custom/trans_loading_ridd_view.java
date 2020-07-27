package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class trans_loading_ridd_view extends RelativeLayout {


    private Paint color1_Paint, color2_Paint,text_paint;
    Handler handler = new Handler(Looper.getMainLooper());
    private  ValueAnimator animation;

    private float rid = 100;
    private float dx = 6;
    private float init_rid;
    private Paint btn_Paint2;
    OnProgressEndListener monProgressEndListener= null;
    private Paint backPi;
    private boolean CalledprogressFished = false;
    private int cntrWidth;
    private int cntrhight;
    private int border;
    private Paint lvl_circl_Paint;
    private Paint text_paint1;
    private Paint text_paint2;
    private float outscaleFactor = 10;
    private ValueAnimator animation2;
    private float y_offset;

    public trans_loading_ridd_view(Context context) {
        super(context);
        init();
    }

    public trans_loading_ridd_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public trans_loading_ridd_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }




    //init();
    public void setProgressEndListener(OnProgressEndListener monProgressEndListener) {
        this.monProgressEndListener = monProgressEndListener;
    }





    public void set_color(int color1,int color2,int colortext){
        backPi.setColor(color1);
        color1_Paint.setColor(color2);
        color2_Paint.setColor(colortext);
        text_paint.setColor(colortext);
        text_paint1.setColor(colortext);
        text_paint2.setColor(colortext);
        //text_paint2.setAlpha(150);

        lvl_circl_Paint.setColor(color2);
        lvl_circl_Paint.setShadowLayer(30.0f, 0.0f, 0.0f, 0xFF340000);


        backPi.setShader(new RadialGradient(
                my_app.game_width,
                my_app.game_Height/2,
                my_app.game_width,
                color2,color1,
                Shader.TileMode.CLAMP));

        lvl_circl_Paint.setShader(new RadialGradient(
                my_app.game_width,
                my_app.game_Height/2,
                my_app.game_width,
                color1,color2,
                Shader.TileMode.CLAMP));
    }
    private void init() {
         setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        rid = my_app.game_Height;

        color1_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
       // color1_Paint.setColor(getResources().getColor(R.color.blow_3));
lvl_circl_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        color2_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
      //  color2_Paint.setColor(getResources().getColor(R.color.blow_4));



        text_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        text_paint.setTextSize(my_app.game_width/16);
        text_paint.setTextAlign(Paint.Align.CENTER);
        text_paint.setTypeface(Typeface.create(my_app.mainFont, Typeface.NORMAL));



        text_paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        text_paint1.setTextSize(my_app.game_width/10);
        text_paint1.setTextAlign(Paint.Align.CENTER);
        text_paint1.setTypeface(Typeface.create(my_app.mainFont, Typeface.NORMAL));


        text_paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        text_paint2.setTextSize(my_app.game_width/20);
        text_paint2.setTextAlign(Paint.Align.CENTER);
        text_paint2.setAlpha(200);

        text_paint2.setTypeface(Typeface.create(my_app.secandFont, Typeface.NORMAL));


        backPi = new Paint(Paint.ANTI_ALIAS_FLAG);
       // backPi.setColor(getResources().getColor(R.color.green_1));


        animation2 =  ValueAnimator.ofFloat(10, 1);
        animation2.setDuration(1000);
        animation2.setStartDelay(500);
        animation2.setInterpolator(new AccelerateDecelerateInterpolator());
        animation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val= (float) valueAnimator.getAnimatedValue();


                outscaleFactor = val;

                invalidate();
            }
        });

        animation2.start();

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


        cntrWidth = getWidth()/2;
        cntrhight = getHeight()/2;
        border = cntrWidth/17;

        y_offset = cntrhight/1.4f;


    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(backPi);
     //  canvas.drawCircle(cntrWidth,cntrhight,rid + border, color2_Paint);

        canvas.drawCircle(cntrWidth,cntrhight - y_offset,rid, color1_Paint);


        canvas.save();
        canvas.scale(outscaleFactor, outscaleFactor,cntrWidth,cntrhight);

        canvas.drawCircle(cntrWidth,cntrhight - y_offset,getWidth()/5, lvl_circl_Paint);
        canvas.drawText("الجولة",cntrWidth,cntrhight - cntrhight/1.6f, text_paint);
        canvas.drawText(my_app.game_eng.current_level.getNum() + "",cntrWidth,cntrhight - cntrhight/1.4f, text_paint1);

        canvas.restore();

        canvas.drawText("النقاط المطلوبـة : " + my_app.game_eng.current_level.getNeed_points_count(),cntrWidth *outscaleFactor,cntrhight - cntrhight/4, text_paint2);
        canvas.drawText("الأخطاء المسموحة : " + + my_app.game_eng.current_level.getExtra_fail_points(),cntrWidth * outscaleFactor,cntrhight - cntrhight/8, text_paint2);



        //   canvas.drawText(" انتظر لحظات",canvas.getWidth()/2,canvas.getHeight()/2, btn_text);
    }


    public  void resume() {
        //color2_text.setAlpha(255);
        rid = init_rid;
    }


    public interface OnProgressEndListener {
        void  onProgressEndListener();
    }

    public void set_progress_persent(float pers ) {




         if (pers < 3){

            if(!CalledprogressFished) {
                monProgressEndListener.onProgressEndListener();
                color1_Paint.setAlpha(0);
                CalledprogressFished= true;
                invalidate();
            }
            return;
        }
        float new_rid = pers / 100 * my_app.game_Height;

        if(animation != null)
        animation.cancel();
        animation =  ValueAnimator.ofFloat(rid, new_rid);
        animation.setDuration(2000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                 rid = (float) valueAnimator.getAnimatedValue();

                // color2_text.setAlpha(100 - (int) valueToDraw);
                invalidate();
            }
        });

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {


                Log.i("anim","end");

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animation.start();
    }

    public void start( ) {
        animation.start();

    }
}
