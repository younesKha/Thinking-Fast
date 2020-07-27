package me.you.thinking_fast.custom;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.start;

public class MyCustomView extends LinearLayout
{

    private final my_radio cb;
    private final my_radio cb2;

    public MyCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
       setGravity(Gravity.TOP);
        setPadding(10,10,10,10);
        ViewGroup.LayoutParams LayoutParamsview = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

       // setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));


        cb = new my_radio(start.handler);
        cb.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        cb.setText("مبتدأ طفل  ");
 // cb.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
        cb.setTextColor(getResources().getColor(android.R.color.white));
        cb.setTextSize(20);


        //cb.setGravity(Gravity.CENTER_VERTICAL);


        cb2 = new my_radio(start.handler);
        cb2.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        cb2.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
        cb.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        cb2.setText(" محترف ");
        cb2.setLayoutParams(LayoutParamsview);

        cb2.setTextColor(getResources().getColor(android.R.color.white));
        cb2.setTextSize(20);
        cb2.setTag(22);
        cb2.setGravity(Gravity.CENTER);
        cb2.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        cb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cb.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                cb2.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                cb2.setChecked(false); cb.setChecked(true);

            }
        });

        cb2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cb.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                cb2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                cb.setChecked(false); cb2.setChecked(true);
            }
        });

        addView(cb);
        addView(cb2);

    }



    //  measureChild
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

           int hh =  my_app.game_Height/15;
           cb.layout(hh/3 ,1,  getWidth() -  getWidth()/4,hh);

           cb2.layout(hh/3,hh + hh/7, getWidth() - getWidth()/4,  hh + hh + hh/7);

    }
}