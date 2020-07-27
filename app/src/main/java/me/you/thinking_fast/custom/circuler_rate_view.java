package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.engine.player;

import static me.you.thinking_fast.engine.core.RTYPE_TEXT;
import static me.you.thinking_fast.engine.core.RTYPE_TEXTANDIMAGE;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class circuler_rate_view extends View {


    private Paint btn_Paint, btn_text, ratetext;
    //  Handler handler = new Handler(Looper.getMainLooper());
    private ValueAnimator animation;


    private float rate_val = 10;
    private float rid = 100;


    private float border, _border;
    private float speed = 0;

    private String text = "Hello";
    private float rate = 0;
    private boolean is_widget = false;

    private Paint btn_Paint2, btn_Paint4;

    private int rid_loop;
    private int cntr_width;
    private int cntr_Height;
    private float rate_anim;
    private Paint btn_Paint3;
    private float rate_100_anim;
    private Bitmap img;
    private float rid_full;
    private Paint text_progress;


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public circuler_rate_view(Context context, AttributeSet attrs) {

        // setTransitionName("start_game");
        super(context, attrs);
        is_widget = true;
        this.border = 4;

        this.text = "";
        this.rate = 80;
        init();


    }


    public void set_rate(float rate) {
        this.rate = rate;


        if (rate == -1) {
            rate_anim = 0;
            invalidate();
            return;
        }

        animation = ValueAnimator.ofFloat(0, this.rate);
        animation.setStartDelay(0);
        animation.setDuration(400);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rate_100_anim = (float) valueAnimator.getAnimatedValue();
                // rid +=valueToDraw;

                rate_anim = rate_100_anim / 100 * 360;
                invalidate();
            }
        });

        // do stuff then
        // can call h again after work!
        animation.start();
        //  h.postDelayed(this, 2000);


        if(!is_widget) {
            if (my_app.get_game_sett_hardlevel() == 1) {
                my_app.progress = my_app.mdb.get_player().getMan_curr_lvl();
            } else {
                my_app.progress = my_app.mdb.get_player().getChild_curr_lvl();
            }
        }

    }

    public circuler_rate_view(Context context, float border, float speed, String text, float rate) {
        super(context);
        // setTransitionName("start_game");
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        this.border = border;
        this.speed = speed;
        this.text = text;
        this.rate = rate;
        init();


        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;

            @Override
            public void run() {
                // do stuff then
                // can call h again after work!
                animation.start();
                //  h.postDelayed(this, 2000);
            }
        }, 1000); // 1 second delay (takes millis)


    }


    private void init() {

        btn_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint.setColor(getResources().getColor(R.color.blow_3));


        img = BitmapFactory.decodeResource(getResources(), R.drawable.lock);


        btn_Paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

        btn_Paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        // btn_Paint2.setColor(getResources().getColor(R.color.blow_4));
        btn_Paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint2.setShader(new RadialGradient(
                my_app.game_width / 2,
                my_app.game_Height / 2,
                my_app.game_Height / 2,
                getResources().getColor(R.color.yallow_1),
                getResources().getColor(R.color.yallow_2),
                Shader.TileMode.CLAMP));


        btn_Paint4.setShader(new RadialGradient(
                my_app.game_width / 2,
                my_app.game_Height / 2,
                my_app.game_Height / 2,
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_red_light),
                Shader.TileMode.CLAMP));

        btn_Paint3.setShader(new RadialGradient(
                my_app.game_width / 2,
                my_app.game_Height / 2,
                my_app.game_Height / 2,
                getResources().getColor(R.color.blow_light2),
                getResources().getColor(R.color.blow_light),
                Shader.TileMode.CLAMP));
        btn_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_text.setColor(getResources().getColor(android.R.color.white));
        btn_text.setTextSize(my_app.game_width / 19);
        btn_text.setTextAlign(Paint.Align.CENTER);
        btn_text.setTypeface(Typeface.create(my_app.font3, Typeface.NORMAL));


        text_progress = new Paint(Paint.ANTI_ALIAS_FLAG);
        text_progress.setColor(getResources().getColor(R.color.white));
        text_progress.setTextSize(my_app.game_width / 30);
        text_progress.setTextAlign(Paint.Align.CENTER);
        text_progress.setTypeface(Typeface.create(my_app.font3, Typeface.NORMAL));

        ratetext = new Paint(Paint.ANTI_ALIAS_FLAG);
        ratetext.setColor(getResources().getColor(android.R.color.white));


        ratetext.setTextAlign(Paint.Align.CENTER);
        ratetext.setTypeface(Typeface.create(my_app.font3, Typeface.NORMAL));


        animation = ValueAnimator.ofFloat(0, rate);

        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rate_100_anim = (float) valueAnimator.getAnimatedValue();
                // rid +=valueToDraw;
                rate_anim = rate_100_anim / 100 * 360;
                invalidate();
            }
        });




        if(!is_widget) {
            if (my_app.get_game_sett_hardlevel() == 1) {
                my_app.progress = my_app.mdb.get_player().getMan_curr_lvl();
            } else {
                my_app.progress = my_app.mdb.get_player().getChild_curr_lvl();
            }
        }
    }

    private void setTextSizeForWidth(int req_width, String stext, Paint textPaint) {

        final float testTextSize = 20f;

        // Get the bounds of the text, using our testTextSize.

        //  textPaint.setTypeface(Typeface.create(my_app.mainFont, Typeface.NORMAL));

        textPaint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        textPaint.getTextBounds(stext, 0, stext.length(), bounds);

        float desiredTextSize = testTextSize * req_width / (bounds.width());

        textPaint.setTextSize(desiredTextSize);


        // Set the paint for that size.

    }


    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);


        cntr_width = getWidth() / 2;
        cntr_Height = getHeight() / 2;
        rid_full = Math.min(cntr_Height, cntr_width);


        _border = (border / 100) * rid_full;
        rid = rid_full - _border * 3;

        setTextSizeForWidth((int) ((int) rid / 1.3f), text, btn_text);
        if (is_widget) {
            rid = rid_full;
            setTextSizeForWidth((int) rid, "100%", ratetext);
        } else
        {
            rid = rid_full - _border * 2;
            setTextSizeForWidth((int)  rid,"100%",ratetext);
        }
        img= Bitmap.createScaledBitmap(img, (int) rid  ,(int) rid , false);







    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        canvas.drawCircle(cntr_width,cntr_Height,rid_full  , btn_Paint);

        canvas.drawCircle(cntr_width,cntr_Height,rid  , btn_Paint3);

        canvas.drawArc(new RectF(getWidth()/2 - rid,getHeight()/2 -rid,getWidth()/2 +rid,getHeight()/2 +rid  ),0,rate_anim,true,btn_Paint2);


        // btn_Paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        canvas.drawArc(new RectF(getWidth()/2 - rid ,getHeight()/2 -rid,getWidth()/2 +rid,getHeight()/2 +rid ),0,Math.max(0,rate_anim -365),true,btn_Paint4);
        // canvas.drawCircle(cntr_width,cntr_Height,rid - _border - _border, btn_Paint);
        canvas.drawCircle(cntr_width,cntr_Height,rid - _border, btn_Paint);


        canvas.drawText(text,cntr_width,cntr_Height + cntr_Height/5, btn_text);

        if(is_widget) {
            canvas.drawText(String.format(java.util.Locale.US, "%.1f", rate_100_anim) + "%", cntr_width, cntr_Height + (cntr_Height / 4), ratetext);
        }else {
            canvas.drawText(String.format(java.util.Locale.US, "%.1f", rate_100_anim) + "%", cntr_width, cntr_Height, ratetext);



            canvas.drawText("التقدم : " +    my_app.progress,cntr_width,cntr_Height + cntr_Height/2, text_progress);

        }
        //draw locked
        if(rate == -1)
        canvas.drawBitmap(img,cntr_width - img.getWidth()/2,cntr_Height - img.getHeight()/2, ratetext);
    }


    public void start( ) {
        animation.start();

    }

    public void setTextColor(int textColor) {

        btn_text.setColor(textColor);
        ratetext.setColor(textColor);

    }


    public void setCenterCircleColor(int Color) {
        btn_Paint.setColor(Color);


    }


}
