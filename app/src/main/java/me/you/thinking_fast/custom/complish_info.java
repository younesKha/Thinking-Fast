package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.engine.player;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class complish_info extends View  {


    private final player plr;
    private Paint btn_Paint,btn_text,ratetext;
  //  Handler handler = new Handler(Looper.getMainLooper());
    private  ValueAnimator animation;





    private Paint btn_Paint2;

    private ValueAnimator anim_loop;
    private int rid_loop;
    private int cntr_width;
    private int cntr_Height;
    private float rate_anim;
    private Paint btn_Paint3;
    private float rate_100_anim;
    private Rect rec;
    private Rect rec1;
    private String prog;
    private String queslevl;


    public complish_info(Context context, player plr) {
        super(context);
       // setTransitionName("start_game");
        this.plr = plr;
        init();


        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            private long time = 0;

            @Override
            public void run()
            {
                // do stuff then
                // can call h again after work!
               animation.start();
              //  h.postDelayed(this, 2000);
            }
        }, 500); // 1 second delay (takes millis)


    }



    private void init() {

        btn_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint.setColor(getResources().getColor(R.color.blow_5));
        rec = new Rect();
        rec1= new Rect();

        btn_Paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
       // btn_Paint2.setColor(getResources().getColor(R.color.blow_4));
        btn_Paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint2.setShader(new RadialGradient(
                my_app.game_width/2,
                my_app.game_Height/2,
                my_app.game_Height/2,
                getResources().getColor(R.color.yallow_1),
                getResources().getColor(R.color.yallow_2),
                Shader.TileMode.CLAMP));
        btn_Paint3.setShader(new RadialGradient(
                my_app.game_width/2,
                my_app.game_Height/2,
                my_app.game_Height/2,
                getResources().getColor(R.color.blow_light2),
                getResources().getColor(R.color.blow_light),
                Shader.TileMode.CLAMP));
        btn_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_text.setColor(getResources().getColor(R.color.yallow_2));
        btn_text.setTextSize(my_app.game_width/30);
        btn_text.setTextAlign(Paint.Align.RIGHT);
        btn_text.setTypeface(Typeface.create(my_app.mainFont, Typeface.NORMAL));

        ratetext= new Paint(Paint.ANTI_ALIAS_FLAG);
        ratetext.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        ratetext.setTextSize(my_app.game_width/14);
        ratetext.setTextAlign(Paint.Align.CENTER);

        rate_100_anim= -1000;
        animation =  ValueAnimator.ofFloat(-1000, my_app.game_width/20);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                   rate_100_anim= (float) valueAnimator.getAnimatedValue();
               // rid +=valueToDraw;


                invalidate();
            }
        });






       // anim_loop.start();
    }

    public  void resume() {
        btn_text.setAlpha(255);

    }




    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);




      //  cntr_width =  getWidth() /2;
     //   cntr_Height =  getHeight() /2;
       // rid = Math.min(cntr_Height,cntr_width);
      //  _border = (border/100) * rid ;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


      //  rid = Math.min(cntr_Height,cntr_width);
     //   canvas.drawCircle(cntr_width,cntr_Height,rid  , btn_Paint2);
       // btn_Paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

       // canvas.drawArc(new RectF(getWidth()/2 - rid,getHeight()/2 -rid,getWidth()/2 +rid,getHeight()/2 +rid ),0,rate_anim,true,btn_Paint3);


      //  canvas.drawCircle(cntr_width,cntr_Height,rid - _border, btn_Paint);

    //    canvas.drawText(text,cntr_width,cntr_Height, btn_text);
    //    canvas.drawPaint(btn_Paint3);

        queslevl = "مستوى الاسئلة : " + (((my_app.get_game_sett_hardlevel() == 1)) ? ("كبير") : ("طفل"));
        btn_text.getTextBounds(queslevl,0,queslevl.length(),rec);
     //   canvas.drawText( queslevl ,getWidth()- rate_100_anim,rec.height() , btn_text);


        prog =  "التقدم : 12/" +  plr.getMan_curr_lvl() ;
        btn_text.getTextBounds(prog,0,prog.length(),rec1);
      //  canvas.drawText(prog,rec1.width()+rate_100_anim,rec1.height() , btn_text);


        //   canvas.drawArc(new RectF(_xp ,_yp ,_xp + _rid,_yp + _rid),0,x,true,_ringPaint2);

      //  _ringPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
       // canvas.drawArc(new RectF(_xp + _lineWight ,_yp +_lineWight ,_xp +_rid - _lineWight ,_yp +_rid - _lineWight ),0,360,true,_ringPaint);
    }


    public void start( ) {
     //   animation.start();

    }
}
