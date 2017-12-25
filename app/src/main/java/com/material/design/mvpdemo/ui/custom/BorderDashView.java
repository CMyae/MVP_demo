package com.material.design.mvpdemo.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chan on 12/14/17.
 */

public class BorderDashView extends View {

    private Paint paint;

    public BorderDashView(Context context) {
        super(context);
    }

    public BorderDashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BorderDashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1f);
        paint.setPathEffect(new DashPathEffect(new float[]{10.0f, 5.0f},10));
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(10,10,getWidth()-10,getHeight()-10,paint);
    }
}
