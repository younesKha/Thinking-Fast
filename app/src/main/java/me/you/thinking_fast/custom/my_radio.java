package me.you.thinking_fast.custom;

import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;

/**
 * Created by younes_hp on 4/8/2017.
 */

public class my_radio extends RadioButton {
    public my_radio(Context context) {
        super(context);
        Log.i("getMeasuredHeight",getMeasuredHeight() + " ");

    }
/*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);


        setMeasuredDimension(widthSize, heightSize);
        Log.i("heightSize",heightSize + " ");

    }
*/
}
