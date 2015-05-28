package com.scrapp.manuel.tuquelees.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Manuel on 10/05/2015.
 */
public class RectangleImageView extends ImageView {

    public RectangleImageView(Context context) {
        super(context);
    }

    public RectangleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectangleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        Double asdf =  width*1.5;
        int heigth=asdf.intValue();
        setMeasuredDimension(width, heigth);
    }
}
