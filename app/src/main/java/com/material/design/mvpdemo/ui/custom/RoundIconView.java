package com.material.design.mvpdemo.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.material.design.mvpdemo.R;

/**
 * Created by chan on 12/14/17.
 */

public class RoundIconView extends ImageView {

    private Paint paint;

    public RoundIconView(Context context) {
        super(context);
    }

    public RoundIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        setWillNotDraw(false);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        paint.setStyle(Paint.Style.FILL);
        setImageResource(R.drawable.ic_close_white_24dp);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int radius = getWidth()/2;
        canvas.drawCircle(radius,radius,radius,paint);
        super.onDraw(canvas);
    }
}
