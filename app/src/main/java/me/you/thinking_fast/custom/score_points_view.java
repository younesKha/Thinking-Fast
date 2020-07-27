package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.my_app;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class score_points_view extends View  {


    private  int pointsColor;
    private Paint mPaint = null;
    private Paint waive_Paint = null;
    private Paint textPaint = null;
    String answer_text;
    private Paint borderPaint = null;
    private float pos_x,pos_y,rid;
    private float rid_wave = 0;
    private ValueAnimator anim_add,anim_drop;
    private ValueAnimator anim2;
    private boolean hide = false;

    private float mangle;

   List<point> points = new ArrayList<point>();
    private int not_stable_point,ii;
    private double main_angle_degree;
    private int Need_correct_Ques = 0;

    private int drop_point = 0;

    private int extra_fail_points = 0;

    private int end_state =0;
    private double rid_point;
    private int centerHight,centerWidth;
    private Paint mPaint2;
    private int palpha =0;
    private ValueAnimator winAnim;

    public score_points_view(Context context,int Need_correct_Ques,int extra_fail_points,int pointsColor,int drop_point) {
        super(context);
       this.Need_correct_Ques= Need_correct_Ques;
        this.extra_fail_points =extra_fail_points;
        this.pointsColor  =pointsColor;
        this.drop_point =drop_point;
       init();
    }


    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(pointsColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      //  mPaint.setShadowLayer(10.0f, 0.0f, -5.0f, 0xFF000000);
      //  setLayerType(LAYER_TYPE_SOFTWARE, mPaint);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(pointsColor);
        //  mPaint2.setColor(getResources().getColor(R.color.white));

       mPaint2.setAlpha(150);
        rid = my_app.game_width/43;


    }

    private void set_points_color(int color ){
        mPaint.setColor(color);

    }

        public void add_point(){
            points.add(new point());
            if(extra_fail_points <= 0)
                not_stable_point = 1000;
            else
            not_stable_point = points.size()-1 - drop_point;

            ii   = points.size()-1;

            anim_add.start();
            palpha= 0;
            if(points.size() == Need_correct_Ques){
                end_state = core.END_LEVEL_SUSSES;
            }
        }
    public boolean is_score_perfect(){
        if(points.size() == Need_correct_Ques){
            return true;
        }
        else return false;
    }


    public int drop_point(){
       // points.remove(points.size()-1);
      //  ii = points.size()-1;
        extra_fail_points --;

        if(points.size() >= drop_point) {

            if(extra_fail_points<0){
                end_state = core.END_NO_EXTRA;
                return core.END_NO_EXTRA;
            }

            anim_drop.start();
            end_state = 0;
            return  0;
        }
        else
        {
            end_state = core.END_NO_POINT;
            return  core.END_NO_POINT;
        }
    }

    public int get_End_state() {
        //   return core.END_LEVEL_SUSSES;
        return end_state;
    }



    public score_points_view(Context context, AttributeSet attrs) {
        super(context, attrs);



    }

    public score_points_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
        protected void onDraw(Canvas canvas) {
          //  Log.i("onDraw","onDraw round");
            super.onDraw(canvas);
        //canvas.drawPaint(mPiePaint);
       //  canvas.drawCircle(pos_x,pos_y,rid,borderPaint);

        for(int i=0;i<points.size();i++) {
            if (i > not_stable_point) {
              // canvas.drawCircle(points.get(i).xx, points.get(i).yy, rid + (i *4), mPaint2);

                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.moveTo(points.get(i).xx, points.get(i).yy - rid);
                path.lineTo(points.get(i).xx + (rid/2), points.get(i).yy - (rid/2));
                path.lineTo(points.get(i).xx +rid, points.get(i).yy);
                path.lineTo(points.get(i).xx + (rid/2), points.get(i).yy + (rid/2));
                path.lineTo(points.get(i).xx, points.get(i).yy + rid);
                path.lineTo(points.get(i).xx - (rid/2), points.get(i).yy + (rid/2));
                path.lineTo(points.get(i).xx - rid, points.get(i).yy);
                path.lineTo(points.get(i).xx - (rid/2), points.get(i).yy - (rid/2));

                path.close();

                canvas.drawPath(path, mPaint2);

            }else {
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.moveTo(points.get(i).xx, points.get(i).yy - rid);
                path.lineTo(points.get(i).xx + (rid / 3), points.get(i).yy - (rid / 3));
                path.lineTo(points.get(i).xx + rid, points.get(i).yy);
                path.lineTo(points.get(i).xx + (rid / 3), points.get(i).yy + (rid / 3));
                path.lineTo(points.get(i).xx, points.get(i).yy + rid);
                path.lineTo(points.get(i).xx - (rid / 3), points.get(i).yy + (rid /3));
                path.lineTo(points.get(i).xx - rid, points.get(i).yy);
                path.lineTo(points.get(i).xx - (rid / 3), points.get(i).yy - (rid / 3));

                path.close();
                canvas.drawPath(path, mPaint);
            }
        }
    }


    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);

        main_angle_degree =   Math.toDegrees(Math.acos((Math.pow(my_app.wheel_rid/2,2)  + (Math.pow(my_app.wheel_rid/2,2) - Math.pow(my_app.game_width,2)))/((2 * Math.pow(my_app.wheel_rid/2,2)))));

        Log.i("main_ ang", "main_angle_degree:" + main_angle_degree + " pow:" + main_angle_degree + " wheel_rid:" + my_app.wheel_rid + " width:" + my_app.game_width );

        anim_add =  ValueAnimator.ofFloat(190 + ((float)main_angle_degree/2),190 - ((float)main_angle_degree/2));
        anim_add.setDuration(501);

        rid_point = my_app.game_Height/2.6;
        centerWidth  = my_app.game_width/2;
        centerHight  = my_app.game_Height/2;

        anim_add.setInterpolator(new AccelerateDecelerateInterpolator());

        anim_add.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float anglmove=(float) valueAnimator.getAnimatedValue();


                mangle = (float) (anglmove + ii * (main_angle_degree /(Need_correct_Ques )));

                double ddpos_x = Math.sin(Math.toRadians(mangle)) * rid_point;
                double ddpos_y = Math.cos(Math.toRadians(mangle)) * rid_point;

                points.get(ii).xx = (float) (ddpos_x + centerWidth);
                points.get(ii).yy = (float) (ddpos_y + centerHight);
              //  mPaint2.setAlpha(150);
                invalidate();
            }

        });


        anim_drop =  ValueAnimator.ofFloat(0,my_app.game_Height/100);
        anim_drop.setDuration(201);
        anim_drop.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_drop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float anglmove=(float) valueAnimator.getAnimatedValue();

                for(int i=0;i<drop_point;i++)
                points.get(ii - i).yy +=anglmove;


                invalidate();
            }

        });

        anim_drop.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                for(int i=0;i<drop_point;i++)
                points.remove(points.size() -1);



                if(extra_fail_points <= 0)
                    not_stable_point = 1000;
                else
                    not_stable_point = points.size()-1 - drop_point;


                ii   = points.size()-1;



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

    public void play_win() {

        not_stable_point = 1000;
        invalidate();

    }


    class point{
       public float xx,yy;
        public point(){
            xx= 0;
            yy=0;
        }
    }


}
