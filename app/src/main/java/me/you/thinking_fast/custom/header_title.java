package me.you.thinking_fast.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import me.you.thinking_fast.R;
import me.you.thinking_fast.engine.my_app;


/**
 * Created by younes_hp on 2/24/2017.
 */

public class header_title extends LinearLayout {
    Paint pai;
    private Path path;

    public header_title(Context context) {
        super(context);
        init();
    }



    public header_title(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public header_title(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    init();
    }


    private void init() {
        setWillNotDraw(false);
        pai = new Paint(Paint.ANTI_ALIAS_FLAG);
        pai.setColor(getResources().getColor(R.color.white));
        pai.setPathEffect(new CornerPathEffect(10));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

      //  canvas.drawColor(getResources().getColor(R.color.blow_light2));
       // canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/2,pai);




        canvas.drawPath(path, pai);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);



         path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(0, -100);

        path.lineTo(getWidth(),-100);
        path.lineTo(getWidth(),getHeight()/2);

        path.lineTo(getWidth()/2,getHeight());
        path.lineTo(0,getHeight()/2);


        path.close();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
