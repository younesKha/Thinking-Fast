package me.you.thinking_fast.canvas;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.you.thinking_fast.about;
import me.you.thinking_fast.R;
import me.you.thinking_fast.custom.circuler_rate_view;
import me.you.thinking_fast.custom.complish_info;
import me.you.thinking_fast.custom.front_btn;
import me.you.thinking_fast.custom.setting_options;
import me.you.thinking_fast.custom.start_btn;
import me.you.thinking_fast.custom.start_clicked_area;
import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.level_entity;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.engine.player;
import me.you.thinking_fast.levels;
import me.you.thinking_fast.setting;
import me.you.thinking_fast.start;

/**
 * Created by younes_hp on 3/10/2017.
 */

public class startCanvas extends ViewGroup {


    private Paint frag1, frag10;
    public start_btn mstartbrn;

    public start_clicked_area start_btn_clicled;

    private Paint background_paint;
    private Paint title_text,img_paint;
    private CheckBox cb;
    private LinearLayout ll;

    private front_btn msetting,level,info,share;
private setting_options setopt;
     boolean setadd = false;
    private int change_ang = 0;
    private int cntr_width;
    private int cntr_Height;
    private ValueAnimator anim_loop;
    Bitmap logo_img,man_img;
    private circuler_rate_view cir_rate_view;
    private complish_info comp_info;
    private float outscaleFactor =1;
    private ValueAnimator startGameAnim;
    private float down_move = 1;
    private int btnWidth;
    private float btn_y_pos;
    private Paint text_p;
    private File imagePath;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public startCanvas(Context context) {
        super(context);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public startCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public startCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
       // super.onSizeChanged(w, h, oldw, oldh);

        cntr_width =  getWidth() /2;
        cntr_Height =  getHeight() /2;

        mstartbrn.layout(0,0,getWidth(),getHeight());
        start_btn_clicled.layout(getWidth()/2 - 100,getHeight()/2 - 100,getWidth()/2 + 100,getHeight()/2 + 100);

        int margin =100;

         btnWidth = getWidth() / 7;;

         btn_y_pos = (getHeight() /4.3f);
        level.layout(getWidth() - btnWidth, (int) btn_y_pos,  getWidth() , (int) btn_y_pos + btnWidth);
        msetting.layout(getWidth() - btnWidth *3, (int)btn_y_pos,  getWidth() - btnWidth*2, (int) btn_y_pos + btnWidth);
        info.layout(getWidth() - btnWidth *5, (int) btn_y_pos,  getWidth() - btnWidth*4, (int) btn_y_pos + btnWidth);
        share.layout(getWidth() - btnWidth *7, (int) btn_y_pos,  getWidth() - btnWidth*6, (int) btn_y_pos + btnWidth);



        setopt.layout(0 , 0,getWidth() ,getHeight());
        cir_rate_view.layout(0 , (int) (getHeight()/2.6f),getWidth() , (int) (getHeight()/1.4f));
        //setLayerType(View.LAYER_TYPE_HARDWARE, null);

        comp_info.layout(0 , (int) (getHeight()/1.5f),getWidth() , (int) (getHeight()/1.2f));



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {

        setWillNotDraw(false);
        logo_img = BitmapFactory.decodeResource(getResources(), R.mipmap.think_fast_logo);
        logo_img=  Bitmap.createScaledBitmap(logo_img, (int) (my_app.game_width /2.1f),  (int) (my_app.game_width /2.1f), true);

        if(my_app.get_game_sett_hardlevel() == 0) {

        //first time
        }
        else if(my_app.get_game_sett_hardlevel() == 1) {
            man_img = BitmapFactory.decodeResource(getResources(), R.mipmap.man);
            man_img=  Bitmap.createScaledBitmap(man_img, (int) (my_app.game_width /2.7f),  (int) (my_app.game_width /2.7f), true);

        } else {
            man_img = BitmapFactory.decodeResource(getResources(), R.mipmap.child);
            man_img=  Bitmap.createScaledBitmap(man_img, (int) (my_app.game_width /2.7f),  (int) (my_app.game_width /2.7f), true);

        }

        background_paint =new Paint(0);


         msetting = new front_btn(getContext(),R.mipmap.settings);
        level = new front_btn(getContext(),R.mipmap.levels);
        info = new front_btn(getContext(),R.mipmap.info);
        share = new front_btn(getContext(),R.mipmap.share);
       player Plr = my_app.mdb.get_player();

        float pers = 50;
        if(Plr == null )pers = 50;
        else {
          if( my_app.get_game_sett_hardlevel() == 1)
            pers = Plr.getMan_pers();
          else
              pers = Plr.getChild_pers();
        }
        cir_rate_view = new circuler_rate_view(getContext(),3,10,"سرعة تفكيرك",pers);
        comp_info = new complish_info(getContext(),Plr);
        mstartbrn = new start_btn(getContext());
        start_btn_clicled= new start_clicked_area(getContext());

        start_btn_clicled.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               // anim_angl.start();
                mstartbrn.start();


            }
        });

        addView(start_btn_clicled);




        addView(cir_rate_view);

        addView(comp_info);
        addView(mstartbrn);

        addView(msetting);
        addView(level);
        addView(info);
        addView(share);



        background_paint.setShader(new RadialGradient(
                my_app.game_width/2 ,
                my_app.game_Height/2 ,
                my_app.game_Height ,
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(R.color.blow_3),
                Shader.TileMode.CLAMP));

        mstartbrn.setOnStartGameClickedListener(new start_btn.Start_Clicked_listiner() {
            @Override
            public void onStart_clicked_listiner() {
               // startGameAnim.start();
            }

            public void on_down() {
                // startGameAnim.start();



                background_paint.setShader(new RadialGradient(
                        my_app.game_width/2 ,
                        my_app.game_Height/2 ,
                        my_app.game_Height/2 ,
                        getResources().getColor(android.R.color.holo_blue_bright),
                        getResources().getColor(R.color.colorAccent),
                        Shader.TileMode.CLAMP));

            }
            public void on_up() {
                // startGameAnim.start();
                background_paint.setShader(new RadialGradient(
                        my_app.game_width/2 ,
                        my_app.game_Height/2 ,
                        my_app.game_Height/2 ,
                        getResources().getColor(android.R.color.holo_blue_bright),
                        getResources().getColor(R.color.blow_3),
                        Shader.TileMode.CLAMP));
            }
        });

        setopt = new setting_options(getContext(),null);

        msetting.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {


          //      Pair[] pair = new Pair[1];
          //      pair[0] = new Pair<View,String>( view,"sett_fram");


          //      ActivityOptions ops = ActivityOptions.makeSceneTransitionAnimation(start.handler,pair);

                Intent intent = new Intent(start.handler, setting.class);
               // intent.putExtra("itemid", id);
                start.handler.startActivity(intent);
            my_app.current_activity = core.SETT_ACT;
                start.handler.overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_center);


            }
        });

        level.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {


            //    Pair[] pair = new Pair[1];
            //    pair[0] = new Pair<View,String>( view,"sett_fram");

                //overridePendingTransition(android.R.anim.slide_in_left,R.anim.scale_out);
           //     ActivityOptions ops = ActivityOptions.makeSceneTransitionAnimation(start.handler,pair);

                Intent intent = new Intent(start.handler, levels.class);
                // intent.putExtra("itemid", id);
                start.handler.startActivity(intent);
                my_app.current_activity = core.LEVELS_ACT;

                start.handler.overridePendingTransition(R.anim.in_rout_left,R.anim.out_rout_left);

            }
        });
        info.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(start.handler, about.class);
                start.handler.startActivity(intent);
                my_app.current_activity = core.INFO_ACT;
                start.handler.overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_center);

            }
        });

        share.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {


                     Bitmap  bb  = takeScreenshot();
                        saveBitmap(bb);
                        shareIt();
            }
        });



//Creating textview .
        TextView SampleTextView1 = new TextView(getContext());
        TextView SampleTextView2 = new TextView(getContext());

        //Adding text to TextView.
        SampleTextView1.setText("First TextView Text");
        SampleTextView2.setText("Second TextView Text");

        //Setting TextView text Size
        SampleTextView1.setTextSize(25);
        SampleTextView2.setTextSize(25);

      //   title_text = new Paint(Paint.ANTI_ALIAS_FLAG);
       //  title_text.setColor(getResources().getColor(R.color.green_1));
       //  title_text.setTextSize(my_app.game_width/9);
       //  title_text.setTextAlign(Paint.Align.CENTER);
       // title_text.setShadowLayer(20.0f, 4.0f, 4.0f, 0xFF340000);


        img_paint= new Paint(Paint.ANTI_ALIAS_FLAG);
      //  ColorFilter emphasize = new LightingColorFilter(getResources().getColor(android.R.color.holo_blue_light),getResources().getColor(android.R.color.holo_blue_light) );
        //img_paint.setColorFilter(emphasize);
       // img_paint.setColor(getResources().getColor(R.color.blow_light2));


        text_p = new Paint(Paint.ANTI_ALIAS_FLAG);
        text_p.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        text_p.setTextSize(my_app.game_width/22);
        text_p.setTextAlign(Paint.Align.CENTER);
        text_p.setTypeface(Typeface.create(my_app.mainFont, Typeface.NORMAL));
        frag1 = new Paint(0);
        frag1.setColor(getResources().getColor(android.R.color.holo_blue_bright));




        frag10 = new Paint(0);
        frag10.setColor(getResources().getColor(R.color.blow_dark2));
        frag10.setAlpha(100);



        startGameAnim =  ValueAnimator.ofFloat(1, 0.2f);
        startGameAnim.setDuration(400);
        startGameAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        startGameAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                outscaleFactor = (float) valueAnimator.getAnimatedValue();

            //    down_move = down_move +0.2f;
                invalidate();
            }
        });

        startGameAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

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






        anim_loop =  ValueAnimator.ofInt(my_app.game_Height, my_app.game_Height/5);
        anim_loop.setDuration(1500);
        anim_loop.setStartDelay(500);
        anim_loop.setRepeatMode(ValueAnimator.REVERSE);
        anim_loop.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_loop.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_loop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int valueToDraw = (int) valueAnimator.getAnimatedValue();

                background_paint.setShader(new RadialGradient(
                        my_app.game_width/2 ,
                        my_app.game_Height/2 ,
                        valueToDraw ,
                        getResources().getColor(android.R.color.holo_blue_bright),
                        getResources().getColor(R.color.blow_3),
                        Shader.TileMode.CLAMP));

                invalidate();
            }
        });

       anim_loop.start();




    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        //canvas.drawPaint(background_paint);

        canvas.scale(outscaleFactor, outscaleFactor,cntr_width,cntr_Height * down_move  );
       // canvas.drawRoundRect(new RectF(-getWidth(),0,getWidth(),getHeight()),0,0,background_paint);
        canvas.drawPaint(background_paint);


        canvas.drawBitmap(logo_img, my_app.game_width/1.9f - logo_img.getWidth()/2 , 0, img_paint);
        if(man_img != null)
        canvas.drawBitmap(man_img, my_app.game_width/5 - man_img.getWidth()/2 , my_app.game_Height/30, img_paint);
        canvas.drawText( "مستوي الاسئلة : " + (((my_app.get_game_sett_hardlevel() == 1)) ? ("بالغ") : ("طفل")), my_app.game_width/1.9f  ,  logo_img.getHeight()/1.3f, text_p);

        canvas.drawRect(0,btn_y_pos  + btnWidth/5 ,my_app.game_width,btn_y_pos + btnWidth   - btnWidth/5 ,frag10);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    public void resume() {

        mstartbrn.resume();
if(my_app.change_man_img) {


   if(my_app.change_man_img) {
       if (my_app.get_game_sett_hardlevel() == 1) {
           man_img = BitmapFactory.decodeResource(getResources(), R.mipmap.man);
       } else {
           man_img = BitmapFactory.decodeResource(getResources(), R.mipmap.child);

       }
   }
    man_img = Bitmap.createScaledBitmap(man_img, (int) (my_app.game_width / 2.7f), (int) (my_app.game_width / 2.7f), true);

    player Plr = my_app.mdb.get_player();

    float pers = 50;
    if(Plr == null )pers = 50;
    else {
        if( my_app.get_game_sett_hardlevel() == 1)
            pers = Plr.getMan_pers();
        else
            pers = Plr.getChild_pers();
    }

        if(cir_rate_view != null)
    cir_rate_view.set_rate(pers);


}
        Log.i("","resume called");

        my_app.change_man_img = false;
    }

    public void onPressBack() {
        if (setadd) {
         //   removeView(setopt);
            //setadd = false;
        }
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(R.id.startCanves).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
         imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "In Tweecher, My highest score with screen shot";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Thinkfast score");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

       start.handler.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public void startgame() {

    }
}
