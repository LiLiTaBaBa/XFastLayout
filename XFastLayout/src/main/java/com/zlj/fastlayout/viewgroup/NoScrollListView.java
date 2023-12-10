package com.zlj.fastlayout.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zlj on 2018/p1/12 0012.
 * @email：282384507@qq.com
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 不复用ItemView的列表View
 */

public class NoScrollListView extends ListView{
    public NoScrollListView(Context context) {
        super(context);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
