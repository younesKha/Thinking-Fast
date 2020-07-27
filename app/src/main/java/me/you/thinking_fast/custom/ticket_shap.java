package me.you.thinking_fast.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import me.you.thinking_fast.R;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class ticket_shap extends LinearLayout {
    Paint pai;

    public ticket_shap(Context context) {
        super(context);
        init();
    }



    public ticket_shap(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ticket_shap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    init();
    }


    private void init() {
        setWillNotDraw(false);
        pai = new Paint(Paint.ANTI_ALIAS_FLAG);
        pai.setColor(getResources().getColor(R.color.white));
        pai.setPathEffect(new CornerPathEffect(13));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(0, 0);
        path.lineTo(getWidth()/2,0);
        path.lineTo(getWidth()/2 + getWidth()/14 ,getHeight()/2);

        path.lineTo(getWidth()/2,getHeight());
        path.lineTo(0,getHeight());


        path.close();

        canvas.drawPath(path, pai);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setcolor(int color) {
        pai.setColor(color);
        invalidate();
    }
}
