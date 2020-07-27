package me.you.thinking_fast.canvas;

import android.animation.Animator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Random;

import me.you.thinking_fast.R;
import me.you.thinking_fast.custom.answer_area;
import me.you.thinking_fast.custom.option_background;
import me.you.thinking_fast.custom.mainwheel;
import me.you.thinking_fast.custom.riddle_view;
import me.you.thinking_fast.custom.score_points_view;
import me.you.thinking_fast.custom.stop_time_view;
import me.you.thinking_fast.custom.wiave_view;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.engine.riddle;
import me.you.thinking_fast.End_dialogfrag;
import me.you.thinking_fast.game;

import static me.you.thinking_fast.engine.core.ANSWER_1;
import static me.you.thinking_fast.engine.core.ANSWER_2;
import static me.you.thinking_fast.engine.core.END_LEVEL_SUSSES;
import static me.you.thinking_fast.engine.core.END_NO_POINT;
import static me.you.thinking_fast.engine.core.END_NO_EXTRA;
import static me.you.thinking_fast.engine.core.RS_FALSE_ANSWERED;
import static me.you.thinking_fast.engine.core.RS_TRUE_ANSWER;


/**
 *
 * Created by younes_hp on 3/11/2017.
 */


public class gameCanvas extends ViewGroup implements View.OnClickListener{
    private Paint background_paint;
    private Paint mwPaint;
    private mainwheel mainwhee;
    private answer_area answer1;
    private answer_area answer2;

    private riddle_view riddView;
    private riddle current_riddle;
   private Random rand ;

    private  answer_area.onAnswer_motionEnd Answer_motionEnd;
    private score_points_view scores;
    private wiave_view waivwView;
    private option_background end_opt;
    private stop_time_view stopv;
    private Animation anim_answer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public gameCanvas(Context context) {
        super(context);
        init();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public gameCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public gameCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        end_opt.layout(0,0,getWidth(),getHeight());

        stopv.layout(0,getHeight()/2,getWidth()/4,getHeight()/2 + getHeight()/4);
            int w = getWidth();
                    int h = getHeight();

        mainwhee.layout(0,0,w,h);
       // waivwView.layout(0,0,w,h);
        answer1.layout(0, h - (h/4),(w/2), h);
        answer2.layout((w/2), h - (h/4),w, h);
        riddView.layout(0,0,w,h);
        scores.layout(0,0,w,h);

        background_paint.setShader(new RadialGradient(
                getWidth()/2,
                getHeight()/2,
                getHeight()/2,
               my_app.game_eng.current_level.getColor2(),
                my_app.game_eng.current_level.getColor1(),
                Shader.TileMode.CLAMP
        ));


    }

    ////////////////////////INTERFACE//////////////////////////////////////////////
    public void display_riddle(riddle r,boolean isfirst){
        if(r != null) {

            current_riddle = r;

            int randomNum =   rand.nextInt((2 - 1) + 1) + 1;


            if (randomNum == 2)
                r.swap_answer();


            answer1.setAnswer_text(r.getAnswer1(),r.is_yes_no_qus());
            answer2.setAnswer_text(r.getAnswer2(),r.is_yes_no_qus());

            riddView.display_riddle_view(r);


            if(!isfirst) {
                mainwhee.reset(my_app.game_eng.current_level.getTime_ms());
            }
        }

    }
    public void setAnswer_motionEnd(answer_area.onAnswer_motionEnd answer_motionEnd) {
        Answer_motionEnd = answer_motionEnd;
    }
 //////////////////////////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        setWillNotDraw(false);
       // setOnClickListener(this);
        rand = new Random();
        anim_answer= AnimationUtils.loadAnimation(game.gameActivity, R.anim.shaking3);
        anim_answer.setDuration(200);





        background_paint =new Paint(0);
        mwPaint =new Paint(0);
        mwPaint.setColor(getResources().getColor(R.color.blow_light));



        mainwhee= new mainwheel(getContext(),50,50,75,3,my_app.game_eng.current_level.getColor2(),my_app.game_eng.current_level.getColor2());
        addView(mainwhee);


        answer1= new answer_area(getContext(),ANSWER_1);
        addView(answer1);

        answer2= new answer_area(getContext(),ANSWER_2);
        addView(answer2);

        riddView = new riddle_view(getContext());
        addView(riddView);
      //  waivwView = new wiave_view(getContext());
      //  addView(waivwView);
        stopv = new stop_time_view(getContext());
        addView(stopv);
        stopv.setStopTimeListener(new stop_time_view.StopTimeListener() {
            @Override
            public void OnStopTimeTouchListener() {
                mainwhee.stop_time_anim();
                my_app.game_eng.play_stoped();
                current_riddle.setStop_time(true);
            }
        });


        scores = new score_points_view(getContext(),my_app.game_eng.current_level.getNeed_points_count()
                ,my_app.game_eng.current_level.getExtra_fail_points(),
                my_app.game_eng.current_level.getTextColor()
                ,my_app.game_eng.current_level.getDrop_count());
        addView(scores);




         end_opt = new option_background(getContext());


        mainwhee.set_wheel_listener(new mainwheel.wheel_listener() {
            @Override
            public void onWheelEndWithNoAswer(Animator animator) {
                my_app.stop_answering = true;
                stopv.hide();
                answer1.make_false(0,0,true);
                answer2.make_false(0,0,false);
                mainwhee.make_red();
                current_riddle.change_state(RS_FALSE_ANSWERED,mainwhee.get_spend_time());

                my_app.game_eng.play_incorrect();


                if (scores.drop_point() == 0) {
                    mainwhee.setwheel_zero();
                }



            }

            @Override
            public void onStopTimeArea() {
                stopv.start();
            }

            @Override
            public void tickWheel(float val) {
                riddView.tickWheel(val);
            }
        });

        answer1.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();
                Log.i("Touch","x:"+x+"||y:"+y);

                stopv.hide();


                if(my_app.stop_answering)
                    return false;

                answer1.setAnimation(anim_answer);

                my_app.stop_answering = true;


                if(current_riddle.getTrueAnswer() == ANSWER_1) {
                    my_app.game_eng.play_correct();

                    answer1.make_true(x,y);
                    scores.add_point();

                    //  waivwView.do_waive(answer1.getWorld_pos_x(),answer1.getWorld_pos_y(),android.R.color.holo_green_light);

                    current_riddle.change_state(RS_TRUE_ANSWER,mainwhee.get_spend_time());
                }else
                {
                    //   if(scores.)
                    answer1.make_false(x,y,true);

                    if(scores.drop_point()==0) {
                        my_app.game_eng.play_incorrect();

                        //   waivwView.do_waive(answer1.getWorld_pos_x(), answer1.getWorld_pos_y(), android.R.color.holo_red_light);
                        current_riddle.change_state(RS_FALSE_ANSWERED,mainwhee.get_spend_time());
                    }

                }

                mainwhee.setwheel_zero();
                return false;
            }
        });


answer2.setOnTouchListener(new OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        int y = (int)motionEvent.getY();
        Log.i("Touch","x:"+x+"||y:"+y);
        stopv.hide();


        Log.i("Needpoints","-"+my_app.game_eng.current_level.getNeed_points_count());
        if(my_app.stop_answering)
            return false;


        answer2.setAnimation(anim_answer);
        my_app.stop_answering = true;


        if(current_riddle.getTrueAnswer() == ANSWER_2) {
            my_app.game_eng.play_correct();

            answer2.make_true(x,y);
            scores.add_point();

            //   waivwView.do_waive(answer2.getWorld_pos_x(),answer2.getWorld_pos_y(),android.R.color.holo_green_light);
            current_riddle.change_state(RS_TRUE_ANSWER,mainwhee.get_spend_time());
        }else
        {

            current_riddle.change_state(RS_FALSE_ANSWERED,mainwhee.get_spend_time());
            answer2.make_false(x,y,true);

            if(scores.drop_point()==0) {
                my_app.game_eng.play_incorrect();
                //      waivwView.do_waive(answer2.getWorld_pos_x(), answer2.getWorld_pos_y(), android.R.color.holo_red_light);
            }

        }
        //waivwView.start_motin();

        mainwhee.setwheel_zero();

        return false;
    }
});

        answer1.set_on_answer_motion_end(new answer_area.onAnswer_motionEnd() {
            @Override
            public void motion_end() {onAnswer_montionEnnd();}
        });
        answer2.set_on_answer_motion_end(new answer_area.onAnswer_motionEnd() {
            @Override
            public void motion_end() {
                onAnswer_montionEnnd();
            }
        });
    }


    private void onAnswer_montionEnnd() {
        //-----DEACTIVE_CODE
       int state = scores.get_End_state();
       //int state = END_LEVEL_SUSSES;

         if(state == END_NO_POINT){

             if(findViewWithTag("OPTION_PACK") == null)
             {
                 addView(end_opt);
             }

             end_opt.show(END_NO_POINT);
             my_app.game_eng.play_lose();

             FragmentManager fm = game.gameActivity.getFragmentManager();
             FragmentTransaction ft = fm.beginTransaction();
             Fragment prev = fm.findFragmentByTag("dialog");
             if (prev != null) {
                 ft.remove(prev);
             }
             ft.addToBackStack(null);

             // Create and show the dialog.
             End_dialogfrag newFragment = End_dialogfrag.newInstance(END_NO_POINT);
             newFragment.show(ft, "dialog");




        }else if(state == END_NO_EXTRA){

             if(findViewWithTag("OPTION_PACK") == null)
                 addView(end_opt);

             end_opt.show(END_NO_EXTRA);
             my_app.game_eng.play_lose();

             FragmentManager fm = game.gameActivity.getFragmentManager();
             FragmentTransaction ft = fm.beginTransaction();
             Fragment prev = fm.findFragmentByTag("dialog");
             if (prev != null) {
                 ft.remove(prev);
             }
             ft.addToBackStack(null);

             // Create and show the dialog.
             End_dialogfrag newFragment = End_dialogfrag.newInstance(END_NO_EXTRA);
             newFragment.show(ft, "dialog");


         }else if(state == END_LEVEL_SUSSES) {

             if(findViewWithTag("OPTION_PACK") == null)
             addView(end_opt);
             removeView(scores);
             addView(scores);

             end_opt.show(END_LEVEL_SUSSES);
             my_app.game_eng.play_win();

             FragmentManager fm = game.gameActivity.getFragmentManager();
             FragmentTransaction ft = fm.beginTransaction();
             Fragment prev = fm.findFragmentByTag("dialog");
             if (prev != null) {
                 ft.remove(prev);
             }
             ft.addToBackStack(null);

             // Create and show the dialog.
             End_dialogfrag newFragment = End_dialogfrag.newInstance(END_LEVEL_SUSSES);
             newFragment.show(ft, "dialog");

             scores.play_win();
        }else{
            Answer_motionEnd.motion_end();
        }


    }

    void disaple_answer_Btn(){
       answer1.disaple();
       answer2.disaple();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            canvas.drawPaint(background_paint);
        //    canvas.drawCircle(getWidth()/2,  getHeight()/2 ,getHeight()/2.5f,mwPaint);

    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
      //  super.onSizeChanged(w, h, oldw, oldh);

    }


    @Override
    public void onClick(View view) {
      //  mainwhee.reset();
    }

    public void startWheel(){
     //   mainwhee.reset();

    }


    public void onBackPressed() {
        mainwhee.stopanimation();



    }
}
