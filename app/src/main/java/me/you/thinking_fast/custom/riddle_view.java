package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.engine.riddle;

import static me.you.thinking_fast.engine.core.RTYPE_IMAGE;
import static me.you.thinking_fast.engine.core.RTYPE_SHAP;
import static me.you.thinking_fast.engine.core.RTYPE_TEXT;
import static me.you.thinking_fast.engine.core.RTYPE_TEXTANDIMAGE;
import static me.you.thinking_fast.engine.core.SHAP_CIRCLE;
import static me.you.thinking_fast.engine.core.SHAP_RECT;
import static me.you.thinking_fast.engine.core.SHAP_TRINGLE;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class riddle_view extends View  {


    private Paint mPaint = null;
    private Paint textPaint = null;
    private Paint shapPaint = null;
    private Paint backPaint = null;


    private float inscaleFactor = 2.f;



    private Bitmap curr_ridd_bitmap;
    private Bitmap old_ridd_bitmap;

    private String Riddle_text;
    private riddle current_riddle;
    private riddle old_riddle;
    private  ValueAnimator animation;
    private int pos_x,pos_y;
    private Paint curr_bitmap_paint;
    private float outscaleFactor;


    public riddle_view(Context context) {
        super(context);


        pos_x = my_app.game_width/2 ;
        pos_y = my_app.game_Height/2;



        animation =  ValueAnimator.ofFloat(2, 1);
        animation.setDuration(300);
        animation.setStartDelay(20);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
              float val= (float) valueAnimator.getAnimatedValue();

                inscaleFactor = val;
                outscaleFactor =   val - 1;

                invalidate();
            }
        });

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                my_app.game_eng.play_whoo();
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


        init();
    }

    public void setRiddle_text(String riddle_text) {
        this.Riddle_text = riddle_text;
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(android.R.color.white));



        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(my_app.game_eng.current_level.getTextColor());
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize((my_app.game_width/12) );

        curr_bitmap_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        curr_bitmap_paint.setFilterBitmap(true);


        shapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shapPaint.setColor(getResources().getColor(android.R.color.holo_red_light));


        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(getResources().getColor(android.R.color.white));




        // mPiePaint.setAlpha(100);
        //   movePlayer0Runnable.run();

        // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }




    private  void setTextSizeForWidth() {


         if (current_riddle.getType() == RTYPE_TEXT || current_riddle.getType() == RTYPE_TEXTANDIMAGE) {


        final float testTextSize = 20f;

        // Get the bounds of the text, using our testTextSize.

        textPaint.setTypeface(Typeface.create(my_app.secandFont, Typeface.NORMAL));

        textPaint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        textPaint.getTextBounds(current_riddle.getRiddleText(), 0, current_riddle.getRiddleText().length(), bounds);

            // 1 =full , 2 = half
            float fullscreen = 1.1f;

          if( current_riddle.getRiddleText().length() > 15)
              fullscreen = 1.1f;
            else
              fullscreen = 1.5f;

             float desiredTextSize = testTextSize * (my_app.game_width/fullscreen) / (bounds.width());

        textPaint.setTextSize(desiredTextSize);



        // Set the paint for that size.
         }
    }

    public void do_invalidate(){
        invalidate();
    }

    private Bitmap get_riddle_bitmap(String bitName) {
try {
    Bitmap bbb = my_app.mdb.get_image(bitName);
    return Bitmap.createScaledBitmap(bbb, my_app.game_Height / 2, my_app.game_Height / 2, false);
}catch (Exception e){
    Log.i("get_image","imageName"+ bitName +"||sqlmsg:" + e.getMessage());

}
        return null;

    }
    public void set_riddle_bitmap(String name) {
        curr_ridd_bitmap = get_riddle_bitmap(name);
    }
    public void set_riddle_bitmap_null() {
        curr_ridd_bitmap = null;
    }
    public riddle_view(Context context, AttributeSet attrs) {
        super(context, attrs);



    }

    public riddle_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
        protected void onDraw(Canvas canvas) {
        //  Log.i("onDraw","onDraw round");
        super.onDraw(canvas);




        canvas.save();
        canvas.scale(outscaleFactor, outscaleFactor,pos_x,pos_y);
     //   canvas.translate(0, 0);

        if(old_riddle != null ) {

            switch (old_riddle.getType()) {

                case RTYPE_TEXT:
                    canvas.drawText(old_riddle.getRiddleText(), pos_x, pos_y, textPaint);
                    break;
                case RTYPE_IMAGE:
                    if (old_ridd_bitmap != null)
                        canvas.drawBitmap(old_ridd_bitmap, pos_x - old_ridd_bitmap.getWidth() / 2, pos_y - old_ridd_bitmap.getHeight() / 2, textPaint);

                    break;

                case RTYPE_SHAP:
                    switch (old_riddle.getShap()) {
                        case SHAP_RECT:
                            canvas.drawRect(new Rect((int) (pos_x - getHeight() / 7), (int) (pos_y - getHeight() / 7), (int) (pos_x + getHeight() / 7), (int) (pos_y + getHeight() / 7)), shapPaint);
                            break;
                        case SHAP_CIRCLE:
                            canvas.drawCircle(pos_x, pos_y, getWidth() / 4, shapPaint);
                            break;
                        case SHAP_TRINGLE:
                            Path path = new Path();
                            path.setFillType(Path.FillType.EVEN_ODD);
                            path.moveTo(pos_x - getHeight() / 7, pos_y);
                            path.lineTo(pos_x + getHeight() / 7, pos_y);
                            path.lineTo(pos_x , pos_y -getHeight() / 7);
                            path.lineTo(pos_x -getHeight() / 7, pos_y);
                            path.close();

                            canvas.drawPath(path, shapPaint);
                            //  canvas.drawtr
                            break;
                    }
                    break;

                case RTYPE_TEXTANDIMAGE:
                    if (old_ridd_bitmap != null)
                        canvas.drawBitmap(old_ridd_bitmap, pos_x - old_ridd_bitmap.getWidth() / 2, pos_y - old_ridd_bitmap.getHeight() / 2, textPaint);
                        canvas.drawText(old_riddle.getRiddleText(), pos_x, pos_y - (getHeight()/5), textPaint);
                    break;


                default:

                    break;

            }
        }

        canvas.restore();


        canvas.save();
        canvas.scale(inscaleFactor, inscaleFactor,pos_x,pos_y);
        // canvas.translate(0,0);
        if(current_riddle != null ) {

            switch (current_riddle.getType()) {

                case RTYPE_TEXT:
                    canvas.drawText(current_riddle.getRiddleText(), pos_x, pos_y, textPaint);
                    break;
                case RTYPE_IMAGE:
                    if (curr_ridd_bitmap != null)
                        canvas.drawBitmap(curr_ridd_bitmap, pos_x - curr_ridd_bitmap.getWidth() / 2, pos_y - curr_ridd_bitmap.getHeight() / 2, curr_bitmap_paint);

                    break;

                case RTYPE_SHAP:
                    shapPaint.setColor(current_riddle.getColor());
                    switch (current_riddle.getShap()) {
                        case SHAP_RECT:
                            canvas.drawRect(new Rect((int) (pos_x - getHeight() / 7), (int) (pos_y - getHeight() / 7), (int) (pos_x + getHeight() / 7), (int) (pos_y + getHeight() / 7)), shapPaint);
                            break;
                        case SHAP_CIRCLE:
                            canvas.drawCircle(pos_x, pos_y, getWidth() / 4, shapPaint);
                            break;
                        case SHAP_TRINGLE:
                            Path path = new Path();
                            path.setFillType(Path.FillType.EVEN_ODD);
                            path.moveTo(pos_x - getHeight() / 7, pos_y);
                            path.lineTo(pos_x + getHeight() / 7, pos_y);
                            path.lineTo(pos_x , pos_y -getHeight() / 7);
                            path.lineTo(pos_x -getHeight() / 7, pos_y);
                            path.close();

                            canvas.drawPath(path, shapPaint);
                            //  canvas.drawtr
                            break;

                    }
                    break;

                case RTYPE_TEXTANDIMAGE:


                    if (curr_ridd_bitmap != null)
                        canvas.drawBitmap(curr_ridd_bitmap, pos_x - curr_ridd_bitmap.getWidth() / 2, pos_y - curr_ridd_bitmap.getHeight() / 2, textPaint);
                    canvas.drawText(current_riddle.getRiddleText(), pos_x, pos_y - (getHeight()/5), textPaint);


                    break;

                default:

                    break;

            }
        }

        canvas.restore();

}


    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);


    }


    public void display_riddle_view(riddle r) {
        outscaleFactor = inscaleFactor;
        inscaleFactor = 2;

        old_riddle = current_riddle;
        current_riddle = r;
        old_ridd_bitmap = curr_ridd_bitmap;

        if(current_riddle.getType() == RTYPE_IMAGE || current_riddle.getType() == RTYPE_TEXTANDIMAGE )
            set_riddle_bitmap(r.getImg());

            setTextSizeForWidth();

        animation.start();
    }

    public void tickWheel(float val) {

        inscaleFactor = inscaleFactor - (val * 0.000005f);

        invalidate();

    }




}
