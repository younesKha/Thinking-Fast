package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class answer_area extends View{


    private Paint mPaint = null;
    private Paint waive_Paint = null;
    private Paint textPaint = null;
    String answer_text;
    private Paint borderPaint = null;
    private byte answer_num;
    private float pos_x,pos_y,rid=0;
    private float rid_wave = 0;
    private ValueAnimator anim;
    private boolean hide = false;
    private ValueAnimator anim_true,anim_false;
    private onAnswer_motionEnd m_answer_end;
    public float pos_y_dy =0;

    private on_showAnswers_displayed m_answer_displayed;



    private float world_pos_x;
    private float world_pos_y;
    private int borderTith;
    private boolean call_end_motion_fn;
    private Bitmap sel_bitmap,true_bitmap,false_bitmap;
    private boolean is_yes_no_qus;
    private Paint TrueFalsePaint;
    private float carvNum = 140;
    private int carvNumPlus;
    private float carvNum_dt;
    private float offsetdy ;
    private int touch_x,touch_y;
    private int normal_font_size;
    private int mat_ss;


    public float getWorld_pos_x() {
        return world_pos_x;
    }

    public float getWorld_pos_y() {
        return world_pos_y;
    }

    public void set_on_answer_motion_end(onAnswer_motionEnd m_answer_end) {
        this.m_answer_end = m_answer_end;
    }

    public void setM_answer_displayed(on_showAnswers_displayed m_answer_displayed) {
        this.m_answer_displayed = m_answer_displayed;
    }


    public answer_area(Context context ,byte answer_num) {
        super(context);

    this.answer_num = answer_num;

       init();
    }


    public void set_hidden(boolean b){
        hide = b ;
    }
    public void make_true(int x,int y){
        touch_x = x;
        touch_y= y;
        anim_true.start();

        call_end_motion_fn = true;

    }
    public void make_false(int x,int y ,boolean call_end_motion_fn){
        touch_x = x;
        touch_y= y;
        anim_false.start();
       this.call_end_motion_fn= call_end_motion_fn;
    }


    private void init() {

        true_bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.true_);
        false_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.false_);

        true_bitmap = Bitmap.createScaledBitmap(true_bitmap,my_app.game_Height/10 , my_app.game_Height/10 , false);
        false_bitmap= Bitmap.createScaledBitmap(false_bitmap,my_app.game_Height/10  , my_app.game_Height/10 , false);

        this.answer_num= answer_num;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(my_app.game_eng.current_level.getColor2());


        waive_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        waive_Paint.setColor(getResources().getColor(android.R.color.holo_green_light));
        waive_Paint.setAlpha(60);

        normal_font_size =  my_app.game_width / 11;
        mat_ss =  20 * (my_app.game_width / 2) ;



        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(my_app.game_eng.current_level.getTextColor());
        textPaint.setTextSize(my_app.game_width/12);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textPaint.setTypeface(Typeface.create(my_app.mainFont, Typeface.NORMAL));

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(my_app.game_eng.current_level.getColor1());

        TrueFalsePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TrueFalsePaint.setColor(my_app.game_eng.current_level.getColor1());

        borderTith = my_app.game_width/250;

        answer_text  = " ";
        // mPiePaint.setAlpha(100);
        carvNum  = my_app.game_width/10;
        carvNumPlus  = my_app.game_width/60;
        offsetdy   = my_app.game_width/190;
        carvNum_dt  = carvNum;
        ColorFilter emphasize = new LightingColorFilter(my_app.game_eng.current_level.getTextColor(),my_app.game_eng.current_level.getTextColor() );
        TrueFalsePaint.setColorFilter(emphasize);

      //  borderPaint.setShadowLayer(10.0f, 0.0f, 5.0f, my_app.game_eng.current_level.getColor2());

     //   setLayerType(LAYER_TYPE_SOFTWARE, borderPaint);
      //  textPaint.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000);

        //movePlayer0Runnable.run();
        // setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        anim =  ValueAnimator.ofFloat(  100,1);
        anim.setDuration(201);
        if(answer_num != 1)
        anim.setStartDelay(100);

        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rid=(float) valueAnimator.getAnimatedValue();
               // carvNum = carvNum_dt - rid;
               // pos_y_dy += offsetdy;

                invalidate();
            }

        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mPaint.setShader(null);
                mPaint.setColor(my_app.game_eng.current_level.getColor2());

              //  pos_y_dy = 0;
            //    pos_y = pos_y_dy;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(answer_num != 1)
                my_app.stop_answering = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });



        anim_true =  ValueAnimator.ofFloat(1, my_app.game_width);
        anim_true.setDuration(501);
        anim_true.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_true.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val=(float) valueAnimator.getAnimatedValue();
                mPaint.setShader(new RadialGradient(
                        touch_x,
                        touch_y,
                        val,
                        getResources().getColor(android.R.color.holo_green_dark),
                        my_app.game_eng.current_level.getColor2(),

                        Shader.TileMode.CLAMP
                ));
              //  mPaint.setColor(getResources().getColor(android.R.color.holo_green_dark));
                invalidate();
            }

        });

        anim_true.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
               if (call_end_motion_fn)
                m_answer_end.motion_end();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });





        anim_false =  ValueAnimator.ofFloat(1, my_app.game_width);
        anim_false.setDuration(501);
        anim_false.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_false.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val=(float) valueAnimator.getAnimatedValue();


                mPaint.setShader(new RadialGradient(
                        touch_x,
                        touch_y,
                       val,
                        getResources().getColor(android.R.color.holo_red_light),
                        my_app.game_eng.current_level.getColor2(),

                        Shader.TileMode.CLAMP
                ));

             //   mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
                invalidate();
            }

        });

        anim_false.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (call_end_motion_fn)
                m_answer_end.motion_end();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }




    public answer_area(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public answer_area(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public  void setAnswer_text(String t, boolean yes_no_qus){
        answer_text = t;


        is_yes_no_qus = yes_no_qus;
        if(yes_no_qus && t.equals("1"))
            sel_bitmap = true_bitmap;
        else if(yes_no_qus && t.equals("0"))
            sel_bitmap = false_bitmap;



        if(!yes_no_qus) {
            textPaint.setTextSize(20f);
            Rect bounds = new Rect();
            textPaint.getTextBounds(t, 0, t.length(), bounds);


            if (bounds.width() > my_app.center_width) {
                float desiredTextSize = mat_ss / bounds.width();
                textPaint.setTextSize(desiredTextSize);
            } else
                textPaint.setTextSize(normal_font_size);
        }


        anim.start();
       // invalidate();
    }

    @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

        if(!hide) {

        //    canvas.drawRoundRect(new RectF(0 + borderTith + rid               ,0 + borderTith + rid +20               , getWidth() - borderTith - rid           ,getHeight()- borderTith - rid ),carvNum,carvNum,borderPaint);
            canvas.drawRoundRect(new RectF(0 + borderTith + borderTith +  rid , 0 + borderTith + borderTith + rid + 20,getWidth()- borderTith - borderTith - rid,getHeight() - borderTith - borderTith - rid ),carvNum + carvNumPlus,carvNum + carvNumPlus,mPaint);

            if(is_yes_no_qus)
                canvas.drawBitmap(sel_bitmap,pos_x - sel_bitmap.getWidth() / 2,pos_y+pos_y_dy -  sel_bitmap.getHeight() / 2, TrueFalsePaint);

            else
                        canvas.drawText(answer_text, pos_x, pos_y+pos_y_dy , textPaint);


        }
        //canvas.drawRect(new RectF(0,0,rid,rid),mPaint);
    }


    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b) ;

        pos_x = getWidth()/2 ;
        pos_y = getHeight()/2 ;
     //  rid =getWidth()/2.6f ;
      //  pos_y_dy = pos_y;
         world_pos_x = r - pos_x;
         world_pos_y = b - pos_y;
    }




    public void disaple() {
        setOnClickListener(null);
        setOnTouchListener(null);
    }


    public interface  onAnswer_motionEnd{
        public void motion_end();
    }


    public interface  on_showAnswers_displayed{
        public void answered_displayed();
    }


}
