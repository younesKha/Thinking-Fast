package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;

import static android.R.attr.x;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class start_btn extends View implements View.OnTouchListener,View.OnClickListener{


    private Paint btn_Paint,btn_text;
    Handler handler = new Handler(Looper.getMainLooper());
    private  ValueAnimator animation,startGameAnim;

    private float rid = 100;
    private float dx = 6;
    private float init_rid;
    private Paint btn_Paint2;
    private int cntr_width;
    private int cntr_Height;
    private int water_pos_y;
    private int water_pos_x;
    Start_Clicked_listiner startClicked= null;
    onAnimation_end_listiner monAnimation_end_listiner=null;
    private String start_game;
    private Bitmap start_bm;
    private float click_rid = 0;
    private Paint btn_Paint4;
    private Path path,path2;

    public void set_onAnimation_end_listiner(onAnimation_end_listiner monAnimation_end_listiner) {
        this.monAnimation_end_listiner = monAnimation_end_listiner;
    }




    public void setOnStartGameClickedListener(Start_Clicked_listiner startClicked) {
        this.startClicked = startClicked;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public start_btn(Context context) {
        super(context);
       // setTransitionName("start_game");
        init();
    }



    private void init() {


        setOnTouchListener(this);
            setOnClickListener(this);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        start_bm = BitmapFactory.decodeResource(getResources(), R.drawable.play);

        btn_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint.setColor(getResources().getColor(R.color.white));
        btn_Paint.setAlpha(200);

        btn_Paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_Paint4.setColor(getResources().getColor(R.color.blow_2));

        btn_Paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
       // btn_Paint2.setColor(getResources().getColor(R.color.blow_4));

        start_game= getResources().getString(R.string.start_game);

        btn_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        btn_text.setColor(getResources().getColor(android.R.color.white));
        btn_text.setTextSize(my_app.game_width/16);
        btn_text.setTextAlign(Paint.Align.CENTER);

        btn_text.setTypeface(Typeface.create(my_app.mainFont, Typeface.NORMAL));




        startGameAnim =  ValueAnimator.ofInt(0, my_app.game_Height);
        startGameAnim.setDuration(500);
        startGameAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        startGameAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                click_rid= (int) valueAnimator.getAnimatedValue();
                btn_Paint2.setAlpha((int) Math.min(250,rid));
                btn_text.setColor(getResources().getColor(android.R.color.background_light));
                invalidate();
            }
        });

        startGameAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {


                btn_Paint2.setShader(new RadialGradient(
                        my_app.game_width/2,
                        my_app.game_Height/2,
                        my_app.game_Height/2,
                        getResources().getColor(R.color.blow_4),
                        getResources().getColor(R.color.blow_dark),
                        Shader.TileMode.CLAMP));
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                monAnimation_end_listiner.onAnimation_end_listiner();
                Log.i("anim","end");

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });



        animation =  ValueAnimator.ofFloat(0, my_app.game_width/2);
        animation.setDuration(2000);
        animation.setRepeatCount(100);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float valueToDraw = (float) valueAnimator.getAnimatedValue();
                rid = valueToDraw;
                btn_Paint.setAlpha(Math.max(0,255 - (int) valueToDraw));

                invalidate();
            }
        });

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                btn_text.setColor(getResources().getColor(android.R.color.white));
                btn_Paint2.setShader(new RadialGradient(
                        my_app.game_width/2,
                        my_app.game_Height/2,
                        my_app.game_Height/2,
                        getResources().getColor(R.color.yallow_1),
                        getResources().getColor(R.color.yallow_2),
                        Shader.TileMode.CLAMP));
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

    public  void resume() {
        animation.start();
     //   btn_text.setAlpha(255);
      //  rid = init_rid;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        water_pos_x = (int)motionEvent.getX();
        water_pos_y = (int)motionEvent.getY();

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (water_pos_x > (cntr_width - (start_bm.getWidth() )) && water_pos_x < (cntr_width + (start_bm.getWidth()))) {
                if (water_pos_y > (cntr_Height - (start_bm.getHeight())) && water_pos_y < (cntr_Height + (start_bm.getHeight()))) {
                    Log.i("clicked ", "clicked ");
                    btn_text.setColor(getResources().getColor(R.color.yallow_2));
                    startClicked.on_down();
                    invalidate();
                }
            }
        } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            btn_text.setColor(getResources().getColor(R.color.white));
            startClicked.on_up();
            invalidate();

        }


     //  animation.start();
        return false;
    }

    @Override
    public void onClick(View view) {
      //  Log.i("Clicled x y" ,water_pos_x + " - " + water_pos_y );




            if  (water_pos_x > (cntr_width - (start_bm.getWidth())) && water_pos_x < (cntr_width + (start_bm.getWidth())) ){
                if  (water_pos_y > (cntr_Height - (start_bm.getHeight())) && water_pos_y < (cntr_Height + (start_bm.getHeight())) ) {
                    Log.i("clicked ", "clicked ");
                    animation.cancel();
                    monAnimation_end_listiner.onAnimation_end_listiner();
                    //  startGameAnim.start();
                        startClicked.onStart_clicked_listiner();
                }
            }


    }




    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);


        init_rid = 0.14f * my_app.game_width;
        rid= 0;
        cntr_width =  getWidth() /2 ;
        cntr_Height =  getHeight() /2 + getHeight() /3;

        start_bm=  Bitmap.createScaledBitmap(start_bm,my_app.game_width/6 , my_app.game_width/6, false);



         path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(getWidth()/3, getHeight());

        path.lineTo(cntr_width,cntr_Height);
        path.lineTo(getWidth() - getWidth()/3,getHeight());
        path.close();




         path2 = new Path();
        path2.setFillType(Path.FillType.EVEN_ODD);
        path2.moveTo(0, getHeight()/2);

        path2.lineTo(cntr_width,cntr_Height);
        path2.lineTo(getWidth(),getHeight()/2);
        path2.close();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(cntr_width,cntr_Height + start_bm.getHeight()/4,rid, btn_Paint);

        canvas.drawCircle(cntr_width,cntr_Height + start_bm.getHeight()/4,rid - rid/20 , btn_Paint4);



        canvas.drawPath(path, btn_Paint4);
        canvas.drawPath(path2, btn_Paint4);



        btn_Paint4.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

//canvas.restore();
        //canvas.drawCircle(cntr_width,cntr_Height + start_bm.getHeight()/4,rid , btn_text);
        canvas.drawCircle(cntr_width,cntr_Height + start_bm.getHeight()/4, start_bm.getWidth()/1.8f, btn_text);
        canvas.drawBitmap(start_bm,cntr_width - start_bm.getWidth()/2,cntr_Height - start_bm.getHeight()/4,btn_Paint2);
        btn_Paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }


    public void start( ) {
       // animation.start();

    }


    public interface Start_Clicked_listiner {
        void onStart_clicked_listiner();
        void on_down();
        void on_up();


    }
    public interface onAnimation_end_listiner {
        void onAnimation_end_listiner();
    }
}
