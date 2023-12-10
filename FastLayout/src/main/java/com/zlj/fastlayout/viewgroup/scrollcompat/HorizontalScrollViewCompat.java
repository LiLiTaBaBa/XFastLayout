package com.zlj.fastlayout.viewgroup.scrollcompat;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;


/**
 * Created by zlj on 2018/5/18 0018.
 * @email：282384507@qq.com
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 兼容滚动监听方法的横向滚动View
 */
public class HorizontalScrollViewCompat extends HorizontalScrollView {

    public HorizontalScrollViewCompat(Context context) {
        super(context);
    }

    public HorizontalScrollViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewCompat(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void setOnScrollChangedListenerCompat(final HorizontalScrollViewCompat.OnScrollChangedListenerCompat onScrollChangedListenerCompat){
        //如果是M以上的版本直接使用系统的监听方法
       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
           setOnScrollChangeListener(new OnScrollChangeListener() {
               @Override
               public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                   if(onScrollChangedListenerCompat!=null) {
                       onScrollChangedListenerCompat.onScrollChanged(scrollX,scrollY);
                   }
               }
           });
       }else{
           this.onScrollChangedListenerCompat=onScrollChangedListenerCompat;
       }
    }

    private HorizontalScrollViewCompat.OnScrollChangedListenerCompat onScrollChangedListenerCompat;
    public interface OnScrollChangedListenerCompat{
        void onScrollChanged(int dx, int dy);
    }
}