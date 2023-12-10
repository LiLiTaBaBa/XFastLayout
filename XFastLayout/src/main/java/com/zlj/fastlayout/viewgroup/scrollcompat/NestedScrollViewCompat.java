package com.zlj.fastlayout.viewgroup.scrollcompat;

import android.content.Context;
import android.os.Build;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by zlj on 2018/5/18 0018.
 * @email：282384507@qq.com
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 兼容滚动监听方法的ScrollView
 */
public class NestedScrollViewCompat extends NestedScrollView {

    public NestedScrollViewCompat(Context context) {
        super(context);
    }

    public NestedScrollViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollViewCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //滚动回调
        if(this.onScrollChangedListenerCompat!=null){
            onScrollChangedListenerCompat.onScrollChanged(l,t);
        }
    }

    /**
     * 设置滚动监听 兼容版本
     * @param onScrollChangedListenerCompat
     */
    public void setOnScrollChangedListenerCompat(final OnScrollChangedListenerCompat onScrollChangedListenerCompat){
        //如果是M以上的版本直接使用系统的监听方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setOnScrollChangeListener(new OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView nestedScrollView, int dx, int dy, int oldx, int oldy) {
                    if(onScrollChangedListenerCompat!=null){
                        onScrollChangedListenerCompat.onScrollChanged(dx,dy);
                    }
                }
            });
        }else{
            this.onScrollChangedListenerCompat=onScrollChangedListenerCompat;
        }
    }

    private NestedScrollViewCompat.OnScrollChangedListenerCompat onScrollChangedListenerCompat;
    public interface OnScrollChangedListenerCompat{
        void onScrollChanged(int dx, int dy);
    }
}