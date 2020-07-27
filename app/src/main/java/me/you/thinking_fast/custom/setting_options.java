package me.you.thinking_fast.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.start;

public class setting_options extends LinearLayout
{

    private final Button cb;
    private final Button cb2;
    private final Paint backpaint;
    private final TextView textmsg;
    private final ValueAnimator anim;
    public int ddt;

    public setting_options(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setWillNotDraw(false);
        //setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
       setGravity(Gravity.CENTER);
        setPadding(30,30,30,30);
        ViewGroup.LayoutParams LayoutParamsview = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

       // setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));


        backpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backpaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        backpaint.setAlpha(230);
        cb = new Button(start.handler);
        cb.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        cb.setText(" اعادة المحاولة  ");
        cb.setBackgroundResource(R.drawable.btn_style);
 // cb.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
        cb.setTextColor(getResources().getColor(android.R.color.white));
        cb.setTextSize(20);
        cb.setGravity(Gravity.CENTER);

        //cb.setGravity(Gravity.CENTER_VERTICAL);


        textmsg = new TextView(start.handler);
        textmsg.setText("  الإعدادت");
        textmsg.setTextSize(22);
        textmsg.setTextColor(getResources().getColor(android.R.color.white));
        textmsg.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        cb2 = new Button(start.handler);
        cb2.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        cb2.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        cb2.setBackgroundResource(R.drawable.btn_style);

        cb2.setText("  خروج ");
        cb2.setLayoutParams(LayoutParamsview);

        cb2.setTextColor(getResources().getColor(android.R.color.white));
        cb2.setTextSize(20);
        cb2.setTag(22);
        cb2.setGravity(Gravity.CENTER);

        addView(textmsg);
        addView(cb);
        addView(cb2);



        anim =  ValueAnimator.ofInt( 0, 100);
        anim.setDuration(401);

        anim.setInterpolator(new DecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                 ddt = (int) valueAnimator.getAnimatedValue();


                invalidate();
            }

        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       canvas.drawRoundRect(new RectF(0,0,getWidth()* ddt/100,getHeight() * ddt/100),60,60,backpaint);

      //  canvas.drawCircle(100,100,440,backpaint);
    }


    //  measureChild
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

     //   super.onLayout(changed, l, t, r, b);

           int hh =  my_app.game_Height/10;

        textmsg.layout(1,1,   getWidth(),hh);


        cb.layout(1,hh,   getWidth(),hh + hh);
           cb2.layout(1,hh+hh + (hh/2),getWidth(),  hh + hh + hh + (hh/2));
    }

    public  void show() {
        anim.start();
    }
}