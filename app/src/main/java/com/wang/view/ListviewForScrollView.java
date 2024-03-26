package com.wang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListviewForScrollView extends ListView {
    public ListviewForScrollView(Context context) {
        super(context);
    }

    public ListviewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListviewForScrollView(Context context, AttributeSet attrs,
        int defStyle) {
        super(context, attrs, defStyle);
    }
        
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}